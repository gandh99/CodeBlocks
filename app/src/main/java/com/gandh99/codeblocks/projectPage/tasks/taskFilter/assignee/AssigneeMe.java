package com.gandh99.codeblocks.projectPage.tasks.taskFilter.assignee;

import com.gandh99.codeblocks.authentication.AuthenticationInterceptor;
import com.gandh99.codeblocks.projectPage.tasks.api.Task;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AssigneeMe implements AssigneeCriteria {
  private String ownUsername;

  @Inject
  public AssigneeMe(AuthenticationInterceptor interceptor) {
    ownUsername = interceptor.getUsername();
  }

  @Override
  public List<Task> filter(List<Task> taskList) {
    List<Task> filteredTaskList = new ArrayList<>();

    // Add the task to the filteredTaskList if the user's own name is within the array of assignees in that task
    for (Task task : taskList) {
      String[] assignees = task.getAssignees();

      for (String assignee : assignees) {
        if (assignee.equals(ownUsername)) {
          filteredTaskList.add(task);
          break;
        }
      }
    }

    return filteredTaskList;
  }

}
