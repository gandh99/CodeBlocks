package com.gandh99.codeblocks.homePage.dashboard.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gandh99.codeblocks.homePage.dashboard.api.Project;
import com.gandh99.codeblocks.homePage.dashboard.repository.DashboardRepository;

import java.util.List;

import javax.inject.Inject;

public class DashboardViewModel extends ViewModel {
  private DashboardRepository repository;

  @Inject
  public DashboardViewModel(DashboardRepository repository) {
    this.repository = repository;
  }

  public MutableLiveData<List<Project>> getProjects() {
    return repository.getProjects();
  }

  public void refreshProjectList() {
    repository.refreshProjectList();
  }
}
