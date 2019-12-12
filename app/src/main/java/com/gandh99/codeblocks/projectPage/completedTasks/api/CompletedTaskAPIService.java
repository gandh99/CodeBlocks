package com.gandh99.codeblocks.projectPage.completedTasks.api;

import com.gandh99.codeblocks.projectPage.tasks.api.Task;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CompletedTaskAPIService {

  @GET("completed_tasks")
  Call<List<Task>> getTasks();

}
