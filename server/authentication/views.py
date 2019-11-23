from django.contrib.auth import authenticate
from django.views.decorators.csrf import csrf_exempt
from rest_framework.authtoken.models import Token
from rest_framework.decorators import api_view, permission_classes
from rest_framework.generics import ListAPIView, UpdateAPIView
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
from .serializers import UserProfileSerializer, ProjectGroupSerializer
from .models import UserProfile, ProjectGroup


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


class Projects(ListAPIView):
    serializer_class = ProjectGroupSerializer
    permission_classes = (IsAuthenticated,)

    def list(self, request, *args, **kwargs):
        # Get header info
        username = request.META.get('HTTP_USERNAME')

        # Get list of projects
        projects = list(ProjectGroup.objects.filter(user_profile__username=username))
        json_data = list([ComplexEncoder().encode(proj) for proj in projects])
        data = {"Projects": json_data}
        return Response(data, status=HTTP_200_OK)


class ComplexEncoder(json.JSONEncoder):
    def default(self, o):
        if isinstance(o, ProjectGroup):
            d = {'title': o.title, 'leader': o.leader, 'description': o.description}
            return d

    def encode(self, o):
        if isinstance(o, ProjectGroup):
            d = {'title': o.title, 'leader': o.leader, 'description': o.description}
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

