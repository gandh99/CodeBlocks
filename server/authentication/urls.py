from django.urls import path

from authentication.ProjectView import ProjectView
from .views import register, login, TaskView, MemberView, InvitationView, InvitationResponseView

urlpatterns = [
    path('register', register),
    path('login', login),
    path('projects', ProjectView.as_view()),
    path('tasks', TaskView.as_view()),
    path('members', MemberView.as_view()),
    path('invite', InvitationView.as_view()),
    path('invite_response', InvitationResponseView.as_view())
]