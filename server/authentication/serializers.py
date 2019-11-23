from rest_framework import serializers
from .models import UserProfile, ProjectGroup


class UserProfileSerializer(serializers.ModelSerializer):
    class Meta:
        model = UserProfile
        fields = [
            'id',
            'username',
            'password',
            'credits'
        ]


class ProjectSerializer(serializers.ModelSerializer):
    class Meta:
        model = ProjectGroup
        fields = [
            'id',
            'user_profile',
            'title',
            'leader',
            'description'
        ]