package com.gandh99.codeblocks.projectPage.tasks.taskSorter;

import com.gandh99.codeblocks.projectPage.tasks.api.Task;

import java.util.Comparator;
import java.util.function.Function;

public interface TaskOrder extends Comparator<Task> {
  void setComparisonCriteria(Function<Task, String> sortingCriteria);
}
