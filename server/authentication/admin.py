from django.contrib import admin
from .models import UserProfile, ProjectGroup


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
    list_display = ('title', 'leader', 'description')
    fieldsets = [
        (None,               {'fields': ['user_profile']}),
        ('Other information', {'fields': ['title', 'leader', 'description'], 'classes': ['collapse']}),
    ]


admin.site.register(UserProfile, UserAdmin)
admin.site.register(ProjectGroup, ProjectGroupAdmin)