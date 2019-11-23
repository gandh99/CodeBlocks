package com.gandh99.codeblocks.dashboard.viewModel;

import androidx.lifecycle.ViewModel;

import com.gandh99.codeblocks.dashboard.api.Project;
import com.gandh99.codeblocks.dashboard.repository.DashboardRepository;

import java.util.List;

import javax.inject.Inject;

public class DashboardViewModel extends ViewModel {
  private DashboardRepository repository;

  @Inject
  public DashboardViewModel(DashboardRepository repository) {
    this.repository = repository;
  }

  public List<Project> getProjects() {
    return repository.getProjects();
  }
}
