package com.gandh99.codeblocks.dashboard.repository;

import android.util.Log;
import android.widget.Toast;

import com.gandh99.codeblocks.dashboard.api.DashboardAPIService;
import com.gandh99.codeblocks.dashboard.api.Project;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardRepository {
  private static final String TAG = "DashboardRepository";
  private DashboardAPIService dashboardAPIService;
  private List<Project> projectList;

  @Inject
  public DashboardRepository(DashboardAPIService dashboardAPIService) {
    this.dashboardAPIService = dashboardAPIService;
  }

  public List<Project> getProjects() {
    refreshProjectList();
    return projectList;
  }

  private void refreshProjectList() {
    dashboardAPIService.getProjects().enqueue(new Callback<List<Project>>() {
      @Override
      public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
        if (response.isSuccessful()) {
          List<Project> projectList = response.body();
          DashboardRepository.this.projectList = projectList;
        }
      }

      @Override
      public void onFailure(Call<List<Project>> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
      }
    });
  }
}
