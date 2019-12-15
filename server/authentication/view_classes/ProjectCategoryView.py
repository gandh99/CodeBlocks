from rest_framework.generics import ListAPIView, CreateAPIView
from rest_framework.permissions import IsAuthenticated

from authentication.JSONEncoder import JSONEncoder
from authentication.serializers import ProjectCategorySerializer


class ProjectCategoryView(ListAPIView, CreateAPIView, JSONEncoder):
    serializer_class = ProjectCategorySerializer
    permission_classes = (IsAuthenticated,)

    def list(self, request, *args, **kwargs):
        pass

    def create(self, request, *args, **kwargs):
        pass

    def encode(self, o):
        pass
