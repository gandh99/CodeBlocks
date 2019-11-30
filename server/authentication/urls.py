from django.urls import path

from .views import register, login, ProjectView, TaskView, MemberView, InvitationView, InvitationResponseView

urlpatterns = [
    path('register', register),
    path('login', login),
    path('projects', ProjectView.as_view()),
    path('tasks', TaskView.as_view()),
    path('members', MemberView.as_view()),
    path('invite', InvitationView.as_view()),
    path('invite_response', InvitationResponseView.as_view())
]