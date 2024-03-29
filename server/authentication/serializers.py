from rest_framework import serializers
from .models import UserProfile, ProjectGroup, Task, ProjectGroupMember, Invitation, TaskCategory


class UserProfileSerializer(serializers.ModelSerializer):
    class Meta:
        model = UserProfile
        fields = [
            'id',
            'username',
            'password',
            'location',
            'company',
            'job_title',
            'email',
            'website',
            'personal_message'
        ]


class ProjectGroupSerializer(serializers.ModelSerializer):
    class Meta:
        model = ProjectGroup
        fields = [
            'id',
            'user_profile',
            'title',
            'description',
            'rank'
        ]


class ProjectGroupMemberSerializer(serializers.ModelSerializer):
    class Meta:
        model = ProjectGroupMember
        fields = [
            'project_group',
            'user_profile',
            'rank'
        ]


class TaskCategorySerializer(serializers.ModelSerializer):
    class Meta:
        model = TaskCategory
        fields = [
            'project_group',
            'category'
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
            'deadline',
            'priority',
            'assignees',
            'categories',
            'completed'
        ]


class InviteSerializer(serializers.ModelSerializer):
    class Meta:
        model = Invitation
        fields = [
            'project_group',
            'inviter',
            'invitee',
            'invitee_rank'
        ]