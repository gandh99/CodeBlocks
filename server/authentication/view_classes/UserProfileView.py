from rest_framework.generics import ListAPIView


class UserProfileView(ListAPIView):
    def list(self, request, *args, **kwargs):
        pass