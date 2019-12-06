package com.gandh99.codeblocks.projectPage.tasks.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gandh99.codeblocks.projectPage.tasks.api.Task;
import com.gandh99.codeblocks.projectPage.tasks.repository.TaskRepository;

import java.util.List;

import javax.inject.Inject;

public class TaskViewModel extends ViewModel {
  private TaskRepository repository;

  @Inject
  public TaskViewModel(TaskRepository repository) {
    this.repository = repository;
  }

  public MutableLiveData<List<Task>> getTasks() {
    return repository.getTasks();
  }

  public void refreshTaskList() {
    repository.refreshTaskList();
  }

  public void updateTaskList(List<Task> tasks) {
    repository.updateTaskList(tasks);
  }
}
