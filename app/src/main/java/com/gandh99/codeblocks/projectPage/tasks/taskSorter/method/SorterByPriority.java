package com.gandh99.codeblocks.projectPage.tasks.taskSorter.method;

import com.gandh99.codeblocks.App;
import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.projectPage.tasks.api.Task;
import com.gandh99.codeblocks.projectPage.tasks.taskSorter.order.TaskOrder;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class SorterByPriority implements TaskSortMethod {
  private String[] allPriorities = App.getAppResources().getStringArray(R.array.priority);

  @Override
  public void sortTasks(List<Task> taskList, TaskOrder order) {
    setComparisonCriteria(order, task -> {
      String taskPriority = task.getPriority();
      for (int i = 0; i < allPriorities.length; i++) {
        if (taskPriority.equals(allPriorities[i])) {
          return String.valueOf(i);
        }
      }

      return "-1";
    });
    Collections.sort(taskList, order);
  }

  @Override
  public void setComparisonCriteria(TaskOrder order, Function<Task, String> comparisonCriteria) {
    order.setComparisonCriteria(comparisonCriteria);
  }
}
