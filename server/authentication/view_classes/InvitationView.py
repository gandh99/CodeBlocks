from rest_framework.generics import ListAPIView, DestroyAPIView, CreateAPIView
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.status import HTTP_200_OK, HTTP_400_BAD_REQUEST

from authentication.JSONEncoder import JSONEncoder
from authentication.models import Invitation, UserProfile, ProjectGroup
from authentication.serializers import InviteSerializer


class InvitationView(ListAPIView, CreateAPIView, JSONEncoder):
    serializer_class = InviteSerializer
    permission_classes = (IsAuthenticated,)

    def list(self, request, *args, **kwargs):
        # Get header info
        invitee = request.META.get('HTTP_USERNAME')
        invitee_profile = UserProfile.objects.get(username=invitee)

        # Get list of invitations for the above invitee
        invitations = list(Invitation.objects.filter(invitee=invitee_profile))
        json_data = list([self.encode(invitation) for invitation in invitations])

        return Response(json_data, status=HTTP_200_OK)

    def create(self, request, *args, **kwargs):
        # Get header info
        inviter = request.META.get('HTTP_USERNAME')
        inviter_profile = UserProfile.objects.get(username=inviter)
        project_id = request.META.get('HTTP_PROJECTID')
        project_group = ProjectGroup.objects.get(id=project_id)

        # Get invitee info
        invitee = request.data.get("invitee")
        invitee_profile = UserProfile.objects.get(username=invitee)
        invitee_rank = request.data.get("inviteeRank")

        # Return an error if the invitee is already in the project
        if invitee_profile in list(project_group.user_profile.all()):
            response = {"Response": "User is already in the Project"}
            return Response(response, status=HTTP_400_BAD_REQUEST)

        # Create a new invite
        invite = Invitation(project_group=project_group, inviter=inviter_profile, invitee=invitee_profile,
                            invitee_rank=invitee_rank)
        invite.save()

        response = {"Response": "Success"}
        return Response(response, status=HTTP_200_OK)

    def encode(self, o):
        d = {'id': o.pk, 'projectTitle': o.project_group.title, 'inviter': o.inviter.username,
             'invitee': o.invitee.username}
        return d
