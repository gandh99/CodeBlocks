from django.urls import path

from authentication.view_classes.InvitationResponseView import InvitationResponseView
from authentication.view_classes.InvitationView import InvitationView
from authentication.view_classes.MemberView import MemberView
from authentication.view_classes.ProjectView import ProjectView
from authentication.view_classes.TaskView import TaskView
from .views import register, login

urlpatterns = [
    path('register', register),
    path('login', login),
    path('projects', ProjectView.as_view()),
    path('tasks', TaskView.as_view()),
    path('members', MemberView.as_view()),
    path('invite', InvitationView.as_view()),
    path('invite_response', InvitationResponseView.as_view())
]