from rest_framework import serializers
from .models import UserProfile, ProjectGroup, Task, ProjectGroupMember


class UserProfileSerializer(serializers.ModelSerializer):
    class Meta:
        model = UserProfile
        fields = [
            'id',
            'username',
            'password',
            'credits'
        ]


class ProjectGroupSerializer(serializers.ModelSerializer):
    class Meta:
        model = ProjectGroup
        fields = [
            'id',
            'user_profile',
            'title',
            'leader',
            'description',
            'rank'
        ]


class ProjectGroupMemberSerializer(serializers.ModelSerializer):
    class Meta:
        model = ProjectGroupMember
        fields = [
            'project_id',
            'user_profile',
            'rank'
        ]


class TaskSerializer(serializers.ModelSerializer):
    class Meta:
        model = Task
        fields = [
            'id',
            'project_group',
            'title',
            'description',
            'date_created',
            'deadline'
        ]