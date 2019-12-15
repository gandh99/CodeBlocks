from rest_framework.generics import ListAPIView, CreateAPIView, DestroyAPIView
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.status import HTTP_200_OK

from authentication.JSONEncoder import JSONEncoder
from authentication.models import ProjectGroup, UserProfile, ProjectGroupMember, Task
from authentication.serializers import ProjectGroupSerializer


class ProjectView(ListAPIView, CreateAPIView, DestroyAPIView, JSONEncoder):
    serializer_class = ProjectGroupSerializer
    permission_classes = (IsAuthenticated,)

    def list(self, request, *args, **kwargs):
        # Get header info
        username = request.META.get('HTTP_USERNAME')

        # Get list of projects
        projects = list(ProjectGroup.objects.filter(user_profile__username=username))
        json_data = list([self.encode(project) for project in projects])
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

    def delete(self, request, *args, **kwargs):
        # Get header info
        username = request.META.get('HTTP_USERNAME')
        project_id = request.META.get("HTTP_PROJECTID")

        # Get UserProfile and ProjectGroup
        user_profile = UserProfile.objects.get(username=username)
        project_group = ProjectGroup.objects.get(pk=project_id)

        # Remove the user from the project
        project_group.user_profile.remove(user_profile)

        # Delete the related ProjectGroupMember
        project_group_member = ProjectGroupMember.objects.get(project_group=project_group, user_profile=user_profile)
        project_group_member.delete()

        # If there are 0 members in the ProjectGroup, remove that ProjectGroup and all its associated Tasks
        self.delete_tasks_in_project_group(project_group)
        self.delete_project_group(project_group)

        # Return response
        response = {"Response": "Success"}
        return Response(response, status=HTTP_200_OK)

    def delete_tasks_in_project_group(self, project_group):
        task_list = list(Task.objects.filter(project_group=project_group))
        for task in task_list:
            task.delete()

    def delete_project_group(self, project_group):
        project_group.delete()

    def encode(self, o):
        d = {'pk': o.pk, 'title': o.title, 'description': o.description}
        return d