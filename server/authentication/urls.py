from django.urls import path

from .views import register, login, Projects, Tasks, Members, InviteMember

urlpatterns = [
    path('register', register),
    path('login', login),
    path('projects', Projects.as_view()),
    path('tasks', Tasks.as_view()),
    path('members', Members.as_view()),
    path('invite', InviteMember.as_view())
]