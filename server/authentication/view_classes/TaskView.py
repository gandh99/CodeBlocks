from rest_framework.generics import ListAPIView, CreateAPIView, UpdateAPIView
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.status import HTTP_200_OK

from authentication.JSONEncoder import JSONEncoder
from authentication.models import Task, ProjectGroup
from authentication.serializers import TaskSerializer
from authentication.models import UserProfile


class TaskView(ListAPIView, CreateAPIView, UpdateAPIView, JSONEncoder):
    serializer_class = TaskSerializer
    permission_classes = (IsAuthenticated,)

    def list(self, request, *args, **kwargs):
        # Get header info
        username = request.META.get('HTTP_USERNAME')
        project_id = request.META.get('HTTP_PROJECTID')

        # Get list of tasks
        tasks = list(Task.objects.filter(project_group__pk=project_id, completed=False))
        json_data = list([self.encode(task) for task in tasks])

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
        priority = request.data.get("priority")
        assignees_username = request.data.getlist("assignees")

        # Get the UserProfile of the assignees_username
        assignees_user_profile = self.get_assignees_profile(assignees_username)

        # Create a new task
        task = Task(project_group=project_group, title=title, description=description,
                    date_created=date_created, deadline=deadline, priority=priority)
        task.save()

        # Add the assignees' user profiles to the task
        self.add_assignees_to_task(task, assignees_user_profile)
        task.save()

        # Return response
        response = {"Response": "Success"}
        return Response(response, status=HTTP_200_OK)

    def put(self, request, *args, **kwargs):
        # Retrieve the task to be updated
        task_id = request.data.get("id")
        task = Task.objects.get(pk=task_id)

        # Update and save the task
        task.title = request.data.get("title")
        task.description = request.data.get("description")
        task.date_created = request.data.get("dateCreated")
        task.deadline = request.data.get("deadline")
        task.priority = request.data.get("priority")
        task.completed = request.data.get("completed")
        task.save()

        # Return response
        response = {"Response": "Success"}
        return Response(response, status=HTTP_200_OK)

    def encode(self, o):
        d = {'id': o.pk, 'title': o.title, 'description': o.description, 'dateCreated': o.date_created,
             'deadline': o.deadline, 'priority': o.priority, 'completed': o.completed}
        return d

    def get_assignees_profile(self, assignees_username):
        profiles = []
        for username in assignees_username:
            user_profile = UserProfile.objects.get(username=username)
            profiles.append(user_profile)
        return profiles

    def add_assignees_to_task(self, task, assignees_user_profile):
        for assignee in assignees_user_profile:
            task.assignees.add(assignee)
