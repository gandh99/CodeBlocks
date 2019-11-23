package com.gandh99.codeblocks.dashboard.api;

import com.gandh99.codeblocks.authentication.api.SessionToken;
import com.gandh99.codeblocks.authentication.api.User;
import com.gandh99.codeblocks.dashboard.Project;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DashboardAPIService {

  @FormUrlEncoded
  @POST("projects")
  Call<List<Project>> getProjects();

}
