from django.contrib.auth import authenticate
from django.views.decorators.csrf import csrf_exempt
from rest_framework.authtoken.models import Token
from rest_framework.decorators import api_view, permission_classes
from rest_framework.generics import ListAPIView, UpdateAPIView, CreateAPIView, DestroyAPIView
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


# class ProjectView(ListAPIView, CreateAPIView):
#     serializer_class = ProjectGroupSerializer
#     permission_classes = (IsAuthenticated,)
#
#     def list(self, request, *args, **kwargs):
#         # Get header info
#         username = request.META.get('HTTP_USERNAME')
#
#         # Get list of projects
#         projects = list(ProjectGroup.objects.filter(user_profile__username=username))
#         json_data = list([CustomJSONEncoder().encode(project) for project in projects])
#         return Response(json_data, status=HTTP_200_OK)
#
#     def create(self, request, *args, **kwargs):
#         # Get header info
#         username = request.META.get('HTTP_USERNAME')
#
#         # Get project info
#         user_profile = UserProfile.objects.get(username=username)
#         title = request.data.get("title")
#         description = request.data.get("description")
#
#         # Create a new project
#         project_group = ProjectGroup(title=title, description=description)
#         project_group.save()
#         project_group.user_profile.add(user_profile)
#
#         # Create the first member of this new project, and this member will be an Admin
#         member = ProjectGroupMember(project_group=project_group, user_profile=user_profile,
#                                     rank=ProjectGroupMember.ADMIN)
#         member.save()
#
#         response = {"Response": "Success"}
#         return Response(response, status=HTTP_200_OK)


class MemberView(ListAPIView):
    serializer_class = ProjectGroupMemberSerializer
    permission_classes = (IsAuthenticated,)

    def list(self, request, *args, **kwargs):
        # Get header info
        username = request.META.get('HTTP_USERNAME')
        project_id = request.META.get('HTTP_PROJECTID')
        project_group = ProjectGroup.objects.get(id=project_id)

        # Get list of members
        members = list(ProjectGroupMember.objects.filter(project_group=project_group))
        json_data = list([CustomJSONEncoder().encode(member) for member in members])

        return Response(json_data, status=HTTP_200_OK)


class TaskView(ListAPIView, CreateAPIView):
    serializer_class = TaskSerializer
    permission_classes = (IsAuthenticated,)

    def list(self, request, *args, **kwargs):
        # Get header info
        username = request.META.get('HTTP_USERNAME')
        project_id = request.META.get('HTTP_PROJECTID')

        # Get list of tasks
        tasks = list(Task.objects.filter(project_group__pk=project_id))
        json_data = list([CustomJSONEncoder().encode(task) for task in tasks])

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


class InvitationView(ListAPIView, CreateAPIView, DestroyAPIView):
    serializer_class = InviteSerializer
    permission_classes = (IsAuthenticated,)

    def list(self, request, *args, **kwargs):
        # Get header info
        invitee = request.META.get('HTTP_USERNAME')
        invitee_profile = UserProfile.objects.get(username=invitee)

        # Get list of invitations for the above invitee
        invitations = list(Invitation.objects.filter(invitee=invitee_profile))
        json_data = list([CustomJSONEncoder().encode(invitation) for invitation in invitations])

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


class InvitationResponseView(CreateAPIView):
    permission_classes = (IsAuthenticated,)

    def create(self, request, *args, **kwargs):
        # Get header info
        invitee = request.META.get('HTTP_USERNAME')

        # Get response to invitation
        invitation_response = request.data.get("invitationResponse")

        # Extract the relevant invitation
        invitation_id = request.data.get("invitationID")
        invitation = Invitation.objects.get(pk=invitation_id)

        # Process response
        if invitation_response == "accept":
            project_group = invitation.project_group
            invitee = invitation.invitee
            rank = invitation.invitee_rank
            new_member = ProjectGroupMember(project_group=project_group, user_profile=invitee, rank=rank)
            new_member.save()
            project_group.user_profile.add(invitee)

        elif invitation_response == "decline":
            pass

        # Remove the invitation
        invitation.delete()

        response = {"Response": "Success"}
        return Response(response, status=HTTP_200_OK)


class CustomJSONEncoder(json.JSONEncoder):
    def encode(self, o):
        if isinstance(o, ProjectGroup):
            d = {'pk': o.pk, 'title': o.title, 'description': o.description}
            return d
        elif isinstance(o, Task):
            d = {'id': o.pk, 'title': o.title, 'description': o.description, 'dateCreated': o.date_created,
                 'deadline': o.deadline}
            return d
        elif isinstance(o, ProjectGroupMember):
            d = {'username': o.user_profile.username, 'rank': o.rank}
            return d
        elif isinstance(o, Invitation):
            d = {'id': o.pk, 'projectTitle': o.project_group.title, 'inviter': o.inviter.username,
                 'invitee': o.invitee.username}
            return d


# register
# curl -X POST --data "username=john&password=smith" http://localhost:8000/register

# login
# curl -X POST --data "username=john&password=smith" http://localhost:8000/login
# Retrieve token (e.g. abc123)

# profile
# curl -H "Authorization: Token abc123" --header "username: john" http://localhost:8000/profile

# update
# curl -X PUT -H "Authorization: Token abc123" -H "username: john" -d "credits=100" http://localhost:8000/update
