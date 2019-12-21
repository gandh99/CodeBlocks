package com.gandh99.codeblocks.projectPage.tasks.taskFilter.deadline;

import com.gandh99.codeblocks.projectPage.tasks.api.Task;

import java.util.List;

public interface DeadlineCriteria {
  List<Task> filter(List<Task> taskList);
}
