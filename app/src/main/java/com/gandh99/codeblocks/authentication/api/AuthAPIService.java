package com.gandh99.codeblocks.authentication.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthAPIService {

  @FormUrlEncoded
  @POST("register")
  Call<User> registerUser(
    @Field("username") String username,
    @Field("password") String password
  );

  @FormUrlEncoded
  @POST("login")
  Call<SessionToken> loginUser(
    @Field("username") String username,
    @Field("password") String password
  );

}
