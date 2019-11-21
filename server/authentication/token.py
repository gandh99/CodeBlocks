from datetime import timedelta
from django.utils import timezone
from rest_framework.authtoken.models import Token


TOKEN_EXPIRED_AFTER_SECONDS = 86400


def expires_in(token):
    time_elapsed = timezone.now() - token.created
    remaining_time = timedelta(seconds=TOKEN_EXPIRED_AFTER_SECONDS) - time_elapsed
    return remaining_time


def is_token_expired(token):
    return expires_in(token) < timedelta(seconds=0)


def expired_token_handler(token):
    is_expired = is_token_expired(token)
    if is_expired:
        token.delete()
        token = Token.objects.create(user=token.user)
    return is_expired, token