package com.gandh99.codeblocks.homePage.invitations.api;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface InvitationAPIService {

  @GET("invite")
  Call<List<Invitation>> getInvitations();

  @FormUrlEncoded
  @POST("invite_response")
  Call<ResponseBody> sendInvitationResponse(
    @Field("invitationID") int invitationID,
    @Field("invitationResponse") String response
  );

}
