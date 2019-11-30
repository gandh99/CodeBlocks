from rest_framework.generics import ListAPIView, CreateAPIView
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.status import HTTP_200_OK

from authentication.CustomJSONEncoder import CustomJSONEncoder
from authentication.models import Task, ProjectGroup
from authentication.serializers import TaskSerializer


class TaskView(ListAPIView, CreateAPIView):
    serializer_class = TaskSerializer
    permission_classes = (IsAuthenticated,)

    def list(self, request, *args, **kwargs):
        # Get header info
        username = request.META.get('HTTP_USERNAME')
        project_id = request.META.get('HTTP_PROJECTID')

        # Get list of tasks
        tasks = list(Task.objects.filter(project_group__pk=project_id))
        json_data = list([CustomJSONEncoder().encode(task) for task in tasks])

        return Response(json_data, status=HTTP_200_OK)

    def create(self, request, *args, **kwargs):
        # Get header info
        username = request.META.get('HTTP_USERNAME')
        project_id = request.META.get('HTTP_PROJECTID')

        # Get task info
        project_group = ProjectGroup.objects.get(id=project_id)
        title = request.data.get("title")
        description = request.data.get("description")
        date_created = request.data.get("dateCreated")
        deadline = request.data.get("deadline")

        # Create a new task
        task = Task(project_group=project_group, title=title, description=description,
                    date_created=date_created, deadline=deadline)
        task.save()

        response = {"Response": "Success"}
        return Response(response, status=HTTP_200_OK)