package com.gandh99.codeblocks.projectPage.tasks.taskSorter.method;

import com.gandh99.codeblocks.projectPage.tasks.api.Task;
import com.gandh99.codeblocks.projectPage.tasks.taskSorter.order.TaskOrder;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class SorterByDateCreated implements TaskSortMethod {

  @Override
  public void sortTasks(List<Task> taskList, TaskOrder order) {
    setComparisonCriteria(order, Task::getDateCreated);
    Collections.sort(taskList, order);
  }

  @Override
  public void setComparisonCriteria(TaskOrder order, Function<Task, String> comparisonCriteria) {
    order.setComparisonCriteria(comparisonCriteria);
  }

}
