from rest_framework.generics import CreateAPIView
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.status import HTTP_200_OK

from authentication.models import Invitation, ProjectGroupMember


class InvitationResponseView(CreateAPIView):
    permission_classes = (IsAuthenticated,)

    def create(self, request, *args, **kwargs):
        # Get header info
        invitee = request.META.get('HTTP_USERNAME')

        # Get response to invitation
        invitation_response = request.data.get("invitationResponse")

        # Extract the relevant invitation
        invitation_id = request.data.get("invitationID")
        invitation = Invitation.objects.get(pk=invitation_id)

        # Process response
        if invitation_response == "accept":
            project_group = invitation.project_group
            invitee = invitation.invitee
            rank = invitation.invitee_rank
            new_member = ProjectGroupMember(project_group=project_group, user_profile=invitee, rank=rank)
            new_member.save()
            project_group.user_profile.add(invitee)

        elif invitation_response == "decline":
            pass

        # Remove the invitation
        invitation.delete()

        response = {"Response": "Success"}
        return Response(response, status=HTTP_200_OK)