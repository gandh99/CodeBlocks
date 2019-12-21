package com.gandh99.codeblocks.projectPage.tasks.taskFilter.assignee;

import com.gandh99.codeblocks.projectPage.tasks.api.Task;

import java.util.List;

public interface AssigneeCriteria {
  List<Task> filter(List<Task> taskList);
}
