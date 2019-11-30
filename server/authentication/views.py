from django.contrib.auth import authenticate
from django.views.decorators.csrf import csrf_exempt
from rest_framework.authtoken.models import Token
from rest_framework.decorators import api_view, permission_classes
from rest_framework.generics import ListAPIView, UpdateAPIView, CreateAPIView
from rest_framework.permissions import AllowAny, IsAuthenticated
from rest_framework.status import (
    HTTP_400_BAD_REQUEST,
    HTTP_404_NOT_FOUND,
    HTTP_200_OK
)
from rest_framework.response import Response
from django.contrib.auth.models import User
import json
from .token import expired_token_handler
from .serializers import UserProfileSerializer, ProjectGroupSerializer, TaskSerializer, ProjectGroupMemberSerializer, \
    InviteSerializer
from .models import UserProfile, ProjectGroup, Task, ProjectGroupMember, Invitation


@csrf_exempt
@api_view(['POST'])
def register(request):
    # Retrieve fields
    username = request.POST['username']
    password = request.POST['password']

    # Create User for authentication
    user = User.objects.create_user(username=username, email="null", password=password)

    # Create UserProfile in database
    user_profile = UserProfile(owner=user, username=username, password=password)
    user_profile.save()

    data = {'Response': 'Registration successful'}
    return Response(data, status=HTTP_200_OK)


@csrf_exempt
@api_view(["POST"])
@permission_classes((AllowAny,))
def login(request):
    # Retrieve fields
    username = request.data.get("username")
    password = request.data.get("password")

    if username is None or password is None:
        return Response({"Error": "Please enter both username and password"},
                        status=HTTP_400_BAD_REQUEST)

    # Authenticate user
    user = authenticate(username=username, password=password)
    if not user:
        return Response({"Error": "Invalid credentials"},
                        status=HTTP_404_NOT_FOUND)

    # Generate and return token
    token, _ = Token.objects.get_or_create(user=user)

    # Create new token if it is expired
    is_expired, token = expired_token_handler(token)

    return Response({"Token": token.key},
                    status=HTTP_200_OK)


class Projects(ListAPIView, CreateAPIView):
    serializer_class = ProjectGroupSerializer
    permission_classes = (IsAuthenticated,)

    def list(self, request, *args, **kwargs):
        # Get header info
        username = request.META.get('HTTP_USERNAME')

        # Get list of projects
        projects = list(ProjectGroup.objects.filter(user_profile__username=username))
        json_data = list([ComplexEncoder().encode(proj) for proj in projects])
        return Response(json_data, status=HTTP_200_OK)

    def create(self, request, *args, **kwargs):
        # Get header info
        username = request.META.get('HTTP_USERNAME')

        # Get project info
        user_profile = UserProfile.objects.get(username=username)
        title = request.data.get("title")
        leader = request.data.get("leader")
        description = request.data.get("description")

        # Create a new project
        project_group = ProjectGroup(title=title, leader=leader, description=description)
        project_group.save()
        project_group.user_profile.add(user_profile)

        # Create the first member of this new project, and this member will be an Admin
        member = ProjectGroupMember(project_group=project_group, user_profile=user_profile,
                                    rank=ProjectGroupMember.ADMIN)
        member.save()

        response = {"Response": "Success"}
        return Response(response, status=HTTP_200_OK)


class Members(ListAPIView):
    serializer_class = ProjectGroupMemberSerializer
    permission_classes = (IsAuthenticated,)

    def list(self, request, *args, **kwargs):
        # Get header info
        username = request.META.get('HTTP_USERNAME')
        project_id = request.META.get('HTTP_PROJECTID')
        project_group = ProjectGroup.objects.get(id=project_id)

        # Get list of members
        members = list(ProjectGroupMember.objects.filter(project_group=project_group))
        json_data = list([ComplexEncoder().encode(member) for member in members])

        return Response(json_data, status=HTTP_200_OK)


class Tasks(ListAPIView, CreateAPIView):
    serializer_class = TaskSerializer
    permission_classes = (IsAuthenticated,)

    def list(self, request, *args, **kwargs):
        # Get header info
        username = request.META.get('HTTP_USERNAME')
        project_id = request.META.get('HTTP_PROJECTID')

        # Get list of tasks
        tasks = list(Task.objects.filter(project_group__pk=project_id))
        json_data = list([ComplexEncoder().encode(task) for task in tasks])

        return Response(json_data, status=HTTP_200_OK)

    def create(self, request, *args, **kwargs):
        # Get header info
        username = request.META.get('HTTP_USERNAME')
        project_id = request.META.get('HTTP_PROJECTID')

        # Get task info
        project_group = ProjectGroup.objects.get(id=project_id)
        title = request.data.get("title")
        description = request.data.get("description")
        date_created = request.data.get("dateCreated")
        deadline = request.data.get("deadline")

        # Create a new task
        task = Task(project_group=project_group, title=title, description=description,
                    date_created=date_created, deadline=deadline)
        task.save()

        response = {"Response": "Success"}
        return Response(response, status=HTTP_200_OK)


class InviteMember(ListAPIView, CreateAPIView):
    serializer_class = InviteSerializer
    permission_classes = (IsAuthenticated,)

    def list(self, request, *args, **kwargs):
        # Get header info
        invitee = request.META.get('HTTP_USERNAME')
        invitee_profile = UserProfile.objects.get(username=invitee)

        # Get list of invitations for the above invitee
        invitations = list(Invitation.objects.filter(invitee=invitee_profile))
        json_data = list([ComplexEncoder().encode(invitation) for invitation in invitations])

        return Response(json_data, status=HTTP_200_OK)

    def create(self, request, *args, **kwargs):
        # Get header info
        inviter = request.META.get('HTTP_USERNAME')
        inviter_profile = UserProfile.objects.get(username=inviter)
        project_id = request.META.get('HTTP_PROJECTID')
        project_group = ProjectGroup.objects.get(id=project_id)

        # Get invitee info
        invitee = request.data.get("invitee")
        invitee_profile = UserProfile.objects.get(username=invitee)
        invitee_rank = request.data.get("inviteeRank")

        # Create a new invite
        invite = Invitation(project_group=project_group, inviter=inviter_profile, invitee=invitee_profile,
                            invitee_rank=invitee_rank)
        invite.save()

        response = {"Response": "Success"}
        return Response(response, status=HTTP_200_OK)


class ComplexEncoder(json.JSONEncoder):
    def default(self, o):
        if isinstance(o, ProjectGroup):
            d = {'title': o.title, 'leader': o.leader, 'description': o.description}
            return d

    def encode(self, o):
        if isinstance(o, ProjectGroup):
            d = {'pk': o.id, 'title': o.title, 'leader': o.leader, 'description': o.description}
            return d
        elif isinstance(o, Task):
            d = {'id': o.id, 'title': o.title, 'description': o.description, 'dateCreated': o.date_created,
                 'deadline': o.deadline}
            return d
        elif isinstance(o, ProjectGroupMember):
            d = {'username': o.user_profile.username, 'rank': o.rank}
            return d
        elif isinstance(o, Invitation):
            d = {'id': o.id, 'projectTitle': o.project_group.title, 'inviter': o.inviter.username, 'invitee': o.invitee.username}
            return d


class UserProfileDetail(ListAPIView):
    serializer_class = UserProfileSerializer
    permission_classes = (IsAuthenticated,)

    def list(self, request, *args, **kwargs):
        # Get header info
        username = request.META.get('HTTP_USERNAME')

        # Get UserProfile data and retrieve the credits
        user_profile = list(UserProfile.objects.filter(username=username))
        result = user_profile[0].credits
        data = {'credits': result}
        return Response(data, status=HTTP_200_OK)


class UserProfileUpdate(UpdateAPIView):
    serializer_class = UserProfileSerializer
    permission_classes = (IsAuthenticated,)

    def update(self, request, *args, **kwargs):
        # Get header info
        username = request.META.get('HTTP_USERNAME')

        # Get change in credits
        credit_change = request.data.get("credits")

        # Update UserProfile
        user_profile = list(UserProfile.objects.filter(username=username))
        user_profile[0].credits += int(credit_change)
        user_profile[0].save()

        result = user_profile[0].credits
        data = {'credits': result}
        return Response(data, status=HTTP_200_OK)


# register
# curl -X POST --data "username=john&password=smith" http://localhost:8000/register

# login
# curl -X POST --data "username=john&password=smith" http://localhost:8000/login
# Retrieve token (e.g. abc123)

# profile
# curl -H "Authorization: Token abc123" --header "username: john" http://localhost:8000/profile

# update
# curl -X PUT -H "Authorization: Token abc123" -H "username: john" -d "credits=100" http://localhost:8000/update

