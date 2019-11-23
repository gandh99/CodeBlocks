package com.gandh99.codeblocks.dashboard.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DashboardAPIService {

//  @FormUrlEncoded
  @GET("projects")
  Call<List<Project>> getProjects();

}
