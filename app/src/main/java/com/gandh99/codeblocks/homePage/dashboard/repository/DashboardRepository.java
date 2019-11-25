package com.gandh99.codeblocks.homePage.dashboard.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.gandh99.codeblocks.homePage.dashboard.api.DashboardAPIService;
import com.gandh99.codeblocks.homePage.dashboard.api.Project;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardRepository {
  private static final String TAG = "DashboardRepository";
  private DashboardAPIService dashboardAPIService;
  private MutableLiveData<List<Project>> projectList = new MutableLiveData<>();

  @Inject
  public DashboardRepository(DashboardAPIService dashboardAPIService) {
    this.dashboardAPIService = dashboardAPIService;
  }

  public MutableLiveData<List<Project>> getProjects() {
    refreshProjectList();
    return projectList;
  }

  public void refreshProjectList() {
    dashboardAPIService.getProjects().enqueue(new Callback<List<Project>>() {
      @Override
      public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
        if (response.isSuccessful()) {
          List<Project> projectList = response.body();
          DashboardRepository.this.projectList.postValue(projectList);
        }
      }

      @Override
      public void onFailure(Call<List<Project>> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
      }
    });
  }
}
