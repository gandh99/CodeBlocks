from django.urls import path

from .views import register, login, UserProfileDetail, UserProfileUpdate

urlpatterns = [
    path('register', register),
    path('login', login),
    path('profile', UserProfileDetail.as_view()),
    path('update', UserProfileUpdate.as_view())
]