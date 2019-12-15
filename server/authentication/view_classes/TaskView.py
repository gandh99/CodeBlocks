from django.db import IntegrityError
from rest_framework.generics import ListAPIView, CreateAPIView, UpdateAPIView
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.status import HTTP_200_OK

from authentication.JSONEncoder import JSONEncoder
from authentication.models import Task, ProjectGroup, TaskCategory
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
        assignees_username_list = request.data.getlist("assignees")
        task_categories_string_list = request.data.getlist("categories")

        # Get a list of the UserProfiles based on the assignees_username
        assignees_user_profile_list = self.get_assignees_user_profile_list(assignees_username_list)

        # Get a list of TaskCategory based on the task_categories_string
        task_categories_list = self.get_task_categories_list(task_categories_string_list, project_group)

        # Create a new task
        task = Task(project_group=project_group, title=title, description=description,
                    date_created=date_created, deadline=deadline, priority=priority)
        task.save()

        # Add the assignees' user profiles to the task
        self.add_assignees_to_task(task, assignees_user_profile_list)
        task.save()

        # Add the task categories to the task
        self.add_task_categories_to_task(task, task_categories_list)
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

    def get_assignees_user_profile_list(self, assignees_username):
        profiles = []
        for username in assignees_username:
            user_profile = UserProfile.objects.get(username=username)
            profiles.append(user_profile)
        return profiles

    def add_assignees_to_task(self, task, assignees_user_profile):
        for assignee in assignees_user_profile:
            task.assignees.add(assignee)

    def get_task_categories_list(self, task_categories_string, project_group):
        task_categories_list = []
        for task_category_string in task_categories_string:
            try:
                # Try to create a new TaskCategory. Only works if it does not already exist
                task_category = TaskCategory(project_group=project_group, category=task_category_string)
                task_category.save()
            except IntegrityError:
                # If the TaskCategory already exists, then extract it
                task_category = TaskCategory.objects.get(project_group=project_group, category=task_category_string)
            task_categories_list.append(task_category)
        return task_categories_list

    def add_task_categories_to_task(self, task, task_categories_list):
        for task_category in task_categories_list:
            task.categories.add(task_category)

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
