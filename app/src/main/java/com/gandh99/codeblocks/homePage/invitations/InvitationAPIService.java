package com.gandh99.codeblocks.homePage.invitations;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InvitationAPIService {

  @GET("invitations")
  Call<List<Invitation>> getInvitations();
}
