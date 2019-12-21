package com.gandh99.codeblocks.projectPage.tasks.taskFilter.assignee;

import com.gandh99.codeblocks.projectPage.tasks.api.Task;

import java.util.List;

public class AssigneeAnyone implements AssigneeCriteria {
  @Override
  public List<Task> filter(List<Task> taskList) {
    return taskList;
  }
}
