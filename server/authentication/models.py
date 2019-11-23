from django.db import models


# Create your models here.
class UserProfile(models.Model):
    owner = models.OneToOneField('auth.User', related_name='UserProfile', on_delete=models.CASCADE)
    username = models.CharField(max_length=10, blank=False, default='')
    password = models.CharField(max_length=251, blank=False)
    credits = models.IntegerField(blank=False, default=100)

    def __str__(self):
        return self.username


class ProjectGroup(models.Model):
    user_profile = models.ManyToManyField(UserProfile)
    title = models.CharField(max_length=20)
    leader = models.CharField(max_length=20)
    description = models.CharField(max_length=150)

    def __str__(self):
        return self.title