from rest_framework.generics import ListAPIView
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.status import HTTP_200_OK

from authentication.JSONEncoder import JSONEncoder
from authentication.models import UserProfile
from authentication.serializers import UserProfileSerializer


class UserProfileView(ListAPIView, JSONEncoder):
    serializer_class = UserProfileSerializer
    permission_classes = (IsAuthenticated,)

    def list(self, request, *args, **kwargs):
        # Get header info
        username = request.META.get('HTTP_USERNAME')

        # Get list of projects
        user_profile = UserProfile.objects.get(username=username)
        json_data = self.encode(user_profile)
        return Response(json_data, status=HTTP_200_OK)

    def encode(self, o):
        d = {'pk': o.pk, 'location': o.location, 'company': o.company, 'jobTitle': o.job_title, 'email': o.email,
             'website': o.website, 'personalMessage': o.personal_message}
        return d
