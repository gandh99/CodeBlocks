from rest_framework.generics import ListAPIView, CreateAPIView
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.status import HTTP_200_OK

from authentication.CustomJSONEncoder import CustomJSONEncoder
from authentication.models import ProjectGroup, UserProfile, ProjectGroupMember
from authentication.serializers import ProjectGroupSerializer


class ProjectView(ListAPIView, CreateAPIView):
    serializer_class = ProjectGroupSerializer
    permission_classes = (IsAuthenticated,)

    def list(self, request, *args, **kwargs):
        # Get header info
        username = request.META.get('HTTP_USERNAME')

        # Get list of projects
        projects = list(ProjectGroup.objects.filter(user_profile__username=username))
        json_data = list([CustomJSONEncoder().encode(project) for project in projects])
        return Response(json_data, status=HTTP_200_OK)

    def create(self, request, *args, **kwargs):
        # Get header info
        username = request.META.get('HTTP_USERNAME')

        # Get project info
        user_profile = UserProfile.objects.get(username=username)
        title = request.data.get("title")
        description = request.data.get("description")

        # Create a new project
        project_group = ProjectGroup(title=title, description=description)
        project_group.save()
        project_group.user_profile.add(user_profile)

        # Create the first member of this new project, and this member will be an Admin
        member = ProjectGroupMember(project_group=project_group, user_profile=user_profile,
                                    rank=ProjectGroupMember.ADMIN)
        member.save()

        response = {"Response": "Success"}
        return Response(response, status=HTTP_200_OK)