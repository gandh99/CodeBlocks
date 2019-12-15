package com.gandh99.codeblocks.homePage.dashboard.api;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DashboardAPIService {

  @GET("user/projects")
  Call<List<Project>> getProjects();

  @FormUrlEncoded
  @POST("user/projects")
  Call<ResponseBody> createProject(
    @Field("title") String title,
    @Field("description") String description
  );

  @DELETE("user/projects")
  Call<ResponseBody> leaveProject(
  );

}
