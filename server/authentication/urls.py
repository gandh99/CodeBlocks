from django.urls import path

from authentication.view_classes.TaskCategoryView import TaskCategoryView
from authentication.view_classes.CompletedTaskView import CompletedTaskView
from authentication.view_classes.UserProfileView import UserProfileView
from authentication.view_classes.InvitationResponseView import InvitationResponseView
from authentication.view_classes.InvitationView import InvitationView
from authentication.view_classes.MemberView import MemberView
from authentication.view_classes.ProjectView import ProjectView
from authentication.view_classes.TaskView import TaskView
from .views import register, login

urlpatterns = [
    path('register', register),
    path('login', login),
    path('user_profile', UserProfileView.as_view()),
    path('user/projects', ProjectView.as_view()),
    path('user/projects/tasks', TaskView.as_view()),
    path('user/projects/completed_tasks', CompletedTaskView.as_view()),
    path('user/projects/categories', TaskCategoryView.as_view()),
    path('user/projects/members', MemberView.as_view()),
    path('invite', InvitationView.as_view()),
    path('invite_response', InvitationResponseView.as_view())
]