import base64

from django.core.files.base import ContentFile
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

        # Decode the base64 string so that it can be set as the profile picture
        image_b64 = request.data.get('profilePicture')
        profile_picture = ContentFile(base64.b64decode(image_b64), name=username + ".png")

        user_profile.profile_picture = profile_picture
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
        # Encode the image file as a base64 string
        image_base64 = base64.b64encode(o.profile_picture.read())

        d = {'pk': o.pk, 'profilePicture': image_base64, 'location': o.location, 'company': o.company,
             'jobTitle': o.job_title, 'email': o.email, 'website': o.website, 'personalMessage': o.personal_message}
        return d
