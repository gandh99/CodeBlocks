from rest_framework import serializers
from .models import UserProfile, ProjectGroup, Task, ProjectGroupMember, Invitation


class UserProfileSerializer(serializers.ModelSerializer):
    class Meta:
        model = UserProfile
        fields = [
            'id',
            'username',
            'password',
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


class InviteSerializer(serializers.ModelSerializer):
    class Meta:
        model = Invitation
        fields = [
            'project_group',
            'inviter',
            'invitee',
            'invitee_rank'
        ]