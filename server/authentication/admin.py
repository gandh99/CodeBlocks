from django.contrib import admin
from .models import UserProfile


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


admin.site.register(UserProfile, UserAdmin)