package com.gandh99.codeblocks.homePage.userProfile.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;

public interface UserProfileAPIService {

  @GET("user_profile")
  Call<UserProfile> getUserProfile();

  @FormUrlEncoded
  @PUT("user_profile")
  Call<ResponseBody> updateUserProfile(
    @Field("location") String location,
    @Field("company") String company,
    @Field("jobTitle") String jobTitle,
    @Field("email") String email,
    @Field("website") String website,
    @Field("personalMessage") String personalMessage
  );
}
