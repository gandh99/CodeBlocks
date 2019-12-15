from rest_framework.generics import ListAPIView, CreateAPIView
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.status import HTTP_200_OK

from authentication.JSONEncoder import JSONEncoder
from authentication.models import ProjectGroup, ProjectCategory
from authentication.serializers import ProjectCategorySerializer


class ProjectCategoryView(ListAPIView, CreateAPIView, JSONEncoder):
    serializer_class = ProjectCategorySerializer
    permission_classes = (IsAuthenticated,)

    def list(self, request, *args, **kwargs):
        # Get header info
        username = request.META.get('HTTP_USERNAME')
        project_id = request.META.get('HTTP_PROJECTID')

        # Get the ProjectGroup
        project_group = ProjectGroup.objects.get(pk=project_id)

        # Get all the categories in that ProjectGroup
        project_categories = list(ProjectCategory.objects.filter(project_group=project_group))
        json_data = list([self.encode(project_category) for project_category in project_categories])

        # Return response
        return Response(json_data, status=HTTP_200_OK)

    def create(self, request, *args, **kwargs):
        pass

    def encode(self, o):
        # json_data will then simply be an array of strings
        return o.category
