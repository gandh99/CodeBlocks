from django.db import models


# Create your models here.
class UserProfile(models.Model):
    owner = models.OneToOneField('auth.User', related_name='UserProfile', on_delete=models.CASCADE)
    username = models.CharField(max_length=10, blank=False, default='')
    password = models.CharField(max_length=251, blank=False)
    credits = models.IntegerField(blank=False, default=100)