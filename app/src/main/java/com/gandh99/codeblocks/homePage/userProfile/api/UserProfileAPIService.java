package com.gandh99.codeblocks.homePage.userProfile.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;

public interface UserProfileAPIService {

  @GET("user_profile")
  Call<UserProfile> getUserProfile();

  @GET("user_profile")
  Call<ResponseBody> updateUserProfile(
    @Field("location") String location,
    @Field("company") String company,
    @Field("jobTitle") String jobTitle,
    @Field("email") String email,
    @Field("website") String website,
    @Field("personalMessage") String personalMessage
  );
}
