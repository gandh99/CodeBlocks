from rest_framework.generics import ListAPIView
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.status import HTTP_200_OK

from authentication.JSONEncoder import JSONEncoder
from authentication.models import ProjectGroup, ProjectGroupMember
from authentication.serializers import ProjectGroupMemberSerializer


class MemberView(ListAPIView, JSONEncoder):
    serializer_class = ProjectGroupMemberSerializer
    permission_classes = (IsAuthenticated,)

    def list(self, request, *args, **kwargs):
        # Get header info
        username = request.META.get('HTTP_USERNAME')
        project_id = request.META.get('HTTP_PROJECTID')
        project_group = ProjectGroup.objects.get(id=project_id)

        # Get list of members
        members = list(ProjectGroupMember.objects.filter(project_group=project_group))
        json_data = list([self.encode(member) for member in members])

        return Response(json_data, status=HTTP_200_OK)

    def encode(self, o):
        d = {'username': o.user_profile.username, 'rank': o.rank}
        return d