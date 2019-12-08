from rest_framework import status
from rest_framework.generics import ListAPIView, UpdateAPIView
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.status import HTTP_200_OK

from authentication.JSONEncoder import JSONEncoder
from authentication.models import UserProfile
from authentication.serializers import UserProfileSerializer


class UserProfileView(ListAPIView, UpdateAPIView, JSONEncoder):
    serializer_class = UserProfileSerializer
    permission_classes = (IsAuthenticated,)

    def list(self, request, *args, **kwargs):
        # Get header info
        username = request.META.get('HTTP_USERNAME')

        # Get the user profile
        user_profile = UserProfile.objects.get(username=username)
        json_data = self.encode(user_profile)
        return Response(json_data, status=HTTP_200_OK)

    def put(self, request, *args, **kwargs):
        # Get header info
        username = request.META.get('HTTP_USERNAME')

        # Get the user profile and update it
        user_profile = UserProfile.objects.get(username=username)
        user_profile.location = request.data.get("location")
        user_profile.company = request.data.get("company")
        user_profile.job_title = request.data.get("jobTitle")
        user_profile.email = request.data.get("email")
        user_profile.website = request.data.get("website")
        user_profile.personal_message = request.data.get("personalMessage")
        user_profile.save()

        # Return response
        response = {"Response": "Success"}
        return Response(response, status=HTTP_200_OK)

    def encode(self, o):
        d = {'pk': o.pk, 'location': o.location, 'company': o.company, 'jobTitle': o.job_title, 'email': o.email,
             'website': o.website, 'personalMessage': o.personal_message}
        return d
