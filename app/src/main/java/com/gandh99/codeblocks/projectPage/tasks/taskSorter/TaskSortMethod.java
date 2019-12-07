package com.gandh99.codeblocks.projectPage.tasks.taskSorter;

import com.gandh99.codeblocks.projectPage.tasks.api.Task;

import java.util.List;
import java.util.function.Function;

public interface TaskSortMethod {
  void sortTasks(List<Task> taskList, TaskOrder order);
  void setComparisonCriteria(TaskOrder order, Function<Task, String> comparisonCriteria);
}
