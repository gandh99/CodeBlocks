from django.urls import path

from .views import register, login, UserProfileDetail, UserProfileUpdate, Projects

urlpatterns = [
    path('register', register),
    path('login', login),
    path('projects', Projects.as_view()),

    # Redundant
    path('profile', UserProfileDetail.as_view()),
    path('update', UserProfileUpdate.as_view())
]