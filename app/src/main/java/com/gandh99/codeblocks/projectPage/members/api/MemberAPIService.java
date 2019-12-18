package com.gandh99.codeblocks.projectPage.members.api;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MemberAPIService {
  @GET("user/projects/members")
  Call<List<ProjectMember>> getProjectMembers();

  @FormUrlEncoded
  @POST("invite")
  Call<ResponseBody> inviteMember(
    @Field("invitee") String invitee,
    @Field("inviteeRank") String inviteeRank
  );
}
