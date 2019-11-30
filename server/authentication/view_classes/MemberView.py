from rest_framework.generics import ListAPIView
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.status import HTTP_200_OK

from authentication.CustomJSONEncoder import CustomJSONEncoder
from authentication.models import ProjectGroup, ProjectGroupMember
from authentication.serializers import ProjectGroupMemberSerializer


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