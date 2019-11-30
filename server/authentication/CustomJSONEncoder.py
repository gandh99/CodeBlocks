from authentication.models import ProjectGroup, Task, ProjectGroupMember, Invitation
import json


class CustomJSONEncoder(json.JSONEncoder):
    def encode(self, o):
        if isinstance(o, ProjectGroup):
            d = {'pk': o.pk, 'title': o.title, 'description': o.description}
            return d
        elif isinstance(o, Task):
            d = {'id': o.pk, 'title': o.title, 'description': o.description, 'dateCreated': o.date_created,
                 'deadline': o.deadline}
            return d
        elif isinstance(o, ProjectGroupMember):
            d = {'username': o.user_profile.username, 'rank': o.rank}
            return d
        elif isinstance(o, Invitation):
            d = {'id': o.pk, 'projectTitle': o.project_group.title, 'inviter': o.inviter.username,
                 'invitee': o.invitee.username}
            return d