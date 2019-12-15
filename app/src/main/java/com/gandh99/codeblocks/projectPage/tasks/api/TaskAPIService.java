package com.gandh99.codeblocks.projectPage.tasks.api;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface TaskAPIService {

  @GET("user/projects/tasks")
  Call<List<Task>> getTasks();

  @FormUrlEncoded
  @POST("user/projects/tasks")
  Call<ResponseBody> createTask(
    @Field("title") String title,
    @Field("description") String description,
    @Field("dateCreated") String dateCreated,
    @Field("deadline") String deadline,
    @Field("priority") String priority,
    @Field("assignees") List<String> assignees
  );

  @FormUrlEncoded
  @PUT("user/projects/tasks")
  Call<ResponseBody> updateTask(
    @Field("id") int id,
    @Field("title") String title,
    @Field("description") String description,
    @Field("dateCreated") String dateCreated,
    @Field("deadline") String deadline,
    @Field("priority") String priority,
    @Field("completed") String completed
  );

  @GET("user/projects/categories")
  Call<List<String>> getTaskCategories();
}
