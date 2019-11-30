package com.gandh99.codeblocks.homePage.invitations.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InvitationAPIService {

  @GET("invite")
  Call<List<Invitation>> getInvitations();
}
