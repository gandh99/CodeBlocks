from django.contrib.auth import authenticate
from django.contrib.auth.models import User
from django.views.decorators.csrf import csrf_exempt
from rest_framework.authtoken.models import Token
from rest_framework.decorators import api_view, permission_classes
from rest_framework.permissions import AllowAny
from rest_framework.response import Response
from rest_framework.status import (
    HTTP_400_BAD_REQUEST,
    HTTP_404_NOT_FOUND,
    HTTP_200_OK
)

from .models import UserProfile
from .token import expired_token_handler


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


# register
# curl -X POST --data "username=john&password=smith" http://localhost:8000/register

# login
# curl -X POST --data "username=john&password=smith" http://localhost:8000/login
# Retrieve token (e.g. abc123)

# profile
# curl -H "Authorization: Token abc123" --header "username: john" http://localhost:8000/profile

# update
# curl -X PUT -H "Authorization: Token abc123" -H "username: john" -d "credits=100" http://localhost:8000/update
