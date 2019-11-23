package com.gandh99.codeblocks.dashboard.viewModel;

import androidx.lifecycle.ViewModel;

import com.gandh99.codeblocks.dashboard.repository.DashboardRepository;

import javax.inject.Inject;

public class DashboardViewModel extends ViewModel {
  private DashboardRepository repository;

  @Inject
  public DashboardViewModel(DashboardRepository repository) {
    this.repository = repository;
  }

  public void getProjects() {
    repository.getProjects();
  }
}
