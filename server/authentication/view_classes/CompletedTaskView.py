from rest_framework.generics import ListAPIView
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.status import HTTP_200_OK

from authentication.JSONEncoder import JSONEncoder
from authentication.models import Task
from authentication.serializers import TaskSerializer


class CompletedTaskView(ListAPIView, JSONEncoder):
    serializer_class = TaskSerializer
    permission_classes = (IsAuthenticated,)

    def list(self, request, *args, **kwargs):
        # Get header info
        username = request.META.get('HTTP_USERNAME')
        project_id = request.META.get('HTTP_PROJECTID')

        # Get list of tasks
        tasks = list(Task.objects.filter(project_group__pk=project_id, completed=True))
        json_data = list([self.encode(task) for task in tasks])

        return Response(json_data, status=HTTP_200_OK)

    def encode(self, o):
        assignees = self.encode_assignees(list(o.assignees.all()))
        categories = self.encode_categories(list(o.categories.all()))

        d = {'id': o.pk, 'title': o.title, 'description': o.description, 'dateCreated': o.date_created,
             'deadline': o.deadline, 'priority': o.priority, 'assignees': assignees, 'categories': categories,
             'completed': o.completed}
        return d

    def encode_assignees(self, assignees_list):
        assignees_username_list = []
        for assignee in assignees_list:
            assignees_username_list.append(assignee.username)
        return assignees_username_list

    def encode_categories(self, categories_list):
        categories_name_list = []
        for category in categories_list:
            categories_name_list.append(category.category)
        return categories_name_list