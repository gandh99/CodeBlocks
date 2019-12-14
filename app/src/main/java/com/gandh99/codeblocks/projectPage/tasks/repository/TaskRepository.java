package com.gandh99.codeblocks.projectPage.tasks.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.gandh99.codeblocks.projectPage.tasks.api.Task;
import com.gandh99.codeblocks.projectPage.tasks.api.TaskAPIService;

import java.util.List;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskRepository {
  private static final String TAG = "TaskRepository";
  private MutableLiveData<List<Task>> taskList = new MutableLiveData<>();
  private TaskAPIService taskAPIService;

  @Inject
  public TaskRepository(TaskAPIService taskAPIService) {
    this.taskAPIService = taskAPIService;
  }

  public MutableLiveData<List<Task>> getTasks() {
    refreshTaskList();
    return taskList;
  }

  public void refreshTaskList() {
    taskAPIService.getTasks().enqueue(new Callback<List<Task>>() {
      @Override
      public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
        if (response.isSuccessful()) {
          List<Task> taskList = response.body();
          TaskRepository.this.taskList.postValue(taskList);
        }
      }

      @Override
      public void onFailure(Call<List<Task>> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
      }
    });
  }

  public void completeTask(Task task) {
    taskAPIService
      .updateTask(
        task.getId(),
        task.getTitle(),
        task.getDescription(),
        task.getDateCreated(),
        task.getDeadline(),
        task.getPriority(),
        "True"  // Must be in this format because Django only accepts "True"/"False"
        )
      .enqueue(new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
          if (response.isSuccessful()) {
            refreshTaskList();
          }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
          Log.d(TAG, "onFailure: " + t.getMessage());
        }
      });
  }
}
