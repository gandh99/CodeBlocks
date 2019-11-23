package com.gandh99.codeblocks.dashboard.repository;

import com.gandh99.codeblocks.dashboard.api.DashboardAPIService;

import javax.inject.Inject;

public class DashboardRepository {
  private DashboardAPIService dashboardAPIService;

  @Inject
  public DashboardRepository(DashboardAPIService dashboardAPIService) {
    this.dashboardAPIService = dashboardAPIService;
  }

  public void getProjects() {
    dashboardAPIService.getProjects();
  }
}
