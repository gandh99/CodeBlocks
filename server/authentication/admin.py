from django.contrib import admin
from .models import UserProfile, ProjectGroup, Task, ProjectGroupMember, Invitation


# Register your models here.
class UserProfileAdmin(admin.ModelAdmin):
    list_display = ('owner', 'username', 'password', 'location', 'company', 'job_title', 'email', 'website',
                    'personal_message')
    # list_filter = ['pub_date']
    search_fields = ['username']
    fieldsets = [
        (None,               {'fields': ['username']}),
        ('Other information', {'fields': ['password', 'location', 'company', 'job_title', 'email', 'website',
                                          'personal_message'], 'classes': ['collapse']}),
    ]
    # inlines = [ChoiceInline]


class ProjectGroupAdmin(admin.ModelAdmin):
    list_display = ('id', 'title', 'description')
    fieldsets = [
        (None,               {'fields': ['user_profile']}),
        ('Other information', {'fields': ['title', 'description'], 'classes': ['collapse']}),
    ]


class ProjectGroupMemberAdmin(admin.ModelAdmin):
    list_display = ('project_group', 'user_profile', 'rank')
    fieldsets = [
        (None,               {'fields': ['project_group', 'user_profile']}),
        ('Other information', {'fields': ['rank'], 'classes': ['collapse']}),
    ]


class TaskAdmin(admin.ModelAdmin):
    list_display = ('project_group', 'title', 'description', 'date_created', 'deadline', 'priority')
    fieldsets = [
        (None,               {'fields': ['project_group']}),
        ('Other information', {'fields': ['title', 'description', 'date_created', 'deadline', 'priority', 'assignees',
                                          'completed'],
                               'classes': ['collapse']}),
    ]


class InvitationAdmin(admin.ModelAdmin):
    list_display = ('project_group', 'inviter', 'invitee', 'invitee_rank')
    fieldsets = [
        (None,               {'fields': ['project_group']}),
        ('Other information', {'fields': ['inviter', 'invitee', 'invitee_rank'], 'classes': ['collapse']}),
    ]


admin.site.register(UserProfile, UserProfileAdmin)
admin.site.register(ProjectGroup, ProjectGroupAdmin)
admin.site.register(ProjectGroupMember, ProjectGroupMemberAdmin)
admin.site.register(Task, TaskAdmin)
admin.site.register(Invitation, InvitationAdmin)