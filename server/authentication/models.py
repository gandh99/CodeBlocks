from django.db import models


class UserProfile(models.Model):
    owner = models.OneToOneField('auth.User', related_name='UserProfile', on_delete=models.CASCADE)
    username = models.CharField(max_length=10, blank=False, default='')
    password = models.CharField(max_length=251, blank=False)
    profile_picture = models.ImageField(upload_to='profile_pictures/', blank=True, null=True)
    location = models.CharField(max_length=50, default='')
    company = models.CharField(max_length=50, default='')
    job_title = models.CharField(max_length=50, default='')
    email = models.CharField(max_length=50, default='')
    website = models.CharField(max_length=50, default='')
    personal_message = models.CharField(max_length=50, default='')

    def __str__(self):
        return self.username


class ProjectGroup(models.Model):
    user_profile = models.ManyToManyField(UserProfile)
    title = models.CharField(max_length=20)
    description = models.CharField(max_length=150)

    def __str__(self):
        return self.title


class ProjectGroupMember(models.Model):
    project_group = models.ForeignKey(ProjectGroup, on_delete=models.CASCADE)
    user_profile = models.ForeignKey(UserProfile, on_delete=models.CASCADE)

    ADMIN = 'ADMIN'
    MEMBER = 'MEMBER'
    RANK_CHOICES = [
        (ADMIN, 'ADMIN'),
        (MEMBER, 'MEMBER')
    ]

    rank = models.CharField(max_length=10, choices=RANK_CHOICES, default=MEMBER)


class Task(models.Model):
    project_group = models.ForeignKey(ProjectGroup, on_delete=models.CASCADE)
    title = models.CharField(max_length=20)
    description = models.CharField(max_length=20)
    date_created = models.DateField(auto_now=False, auto_created=True)
    deadline = models.DateField(auto_now=False, auto_created=True)

    def __str__(self):
        return self.title


class Invitation(models.Model):
    project_group = models.ForeignKey(ProjectGroup, on_delete=models.CASCADE)
    inviter = models.ForeignKey(UserProfile, related_name="inviter", on_delete=models.CASCADE)
    invitee = models.ForeignKey(UserProfile, related_name="invitee", on_delete=models.CASCADE)
    invitee_rank = models.CharField(max_length=10, choices=ProjectGroupMember.RANK_CHOICES,
                                    default=ProjectGroupMember.MEMBER)
