from django.contrib import admin
from .models import UserProfile, ProjectGroup, Task, ProjectGroupMember


# Register your models here.
class UserAdmin(admin.ModelAdmin):
    list_display = ('owner', 'username', 'password', 'credits')
    # list_filter = ['pub_date']
    search_fields = ['username']
    fieldsets = [
        (None,               {'fields': ['username']}),
        ('Other information', {'fields': ['password', 'credits'], 'classes': ['collapse']}),
    ]
    # inlines = [ChoiceInline]


class ProjectGroupAdmin(admin.ModelAdmin):
    list_display = ('pk', 'title', 'leader', 'description')
    fieldsets = [
        (None,               {'fields': ['user_profile']}),
        ('Other information', {'fields': ['title', 'leader', 'description'], 'classes': ['collapse']}),
    ]


class ProjectGroupMemberAdmin(admin.ModelAdmin):
    list_display = ('project_id', 'user_profile', 'rank')
    fieldsets = [
        (None,               {'fields': ['project_id', 'user_profile']}),
        ('Other information', {'fields': ['rank'], 'classes': ['collapse']}),
    ]


class TaskAdmin(admin.ModelAdmin):
    list_display = ('project_group', 'title', 'description', 'date_created', 'deadline')
    fieldsets = [
        (None,               {'fields': ['project_group']}),
        ('Other information', {'fields': ['title', 'description', 'date_created', 'deadline'], 'classes': ['collapse']}),
    ]


admin.site.register(UserProfile, UserAdmin)
admin.site.register(ProjectGroup, ProjectGroupAdmin)
admin.site.register(ProjectGroupMember, ProjectGroupMemberAdmin)
admin.site.register(Task, TaskAdmin)