package com.gandh99.codeblocks.projectPage.completedTasks.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gandh99.codeblocks.projectPage.completedTasks.repository.CompletedTaskRepository;
import com.gandh99.codeblocks.projectPage.tasks.api.Task;
import com.gandh99.codeblocks.projectPage.tasks.repository.TaskRepository;

import java.util.List;

import javax.inject.Inject;

public class CompletedTaskViewModel extends ViewModel {
  private CompletedTaskRepository repository;

  @Inject
  public CompletedTaskViewModel(CompletedTaskRepository repository) {
    this.repository = repository;
  }

  public MutableLiveData<List<Task>> getTasks() {
    return repository.getTasks();
  }

  public void refreshTaskList() {
    repository.refreshTaskList();
  }

}
