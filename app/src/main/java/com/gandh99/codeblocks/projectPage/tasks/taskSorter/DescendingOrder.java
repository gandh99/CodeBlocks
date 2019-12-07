package com.gandh99.codeblocks.projectPage.tasks.taskSorter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.gandh99.codeblocks.projectPage.tasks.api.Task;

import java.util.function.Function;

public class DescendingOrder implements TaskOrder {
  private Function<Task, String> sortingCriteria;

  @Override
  public void setComparisonCriteria(Function<Task, String> sortingCriteria) {
    this.sortingCriteria = sortingCriteria;
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  @Override
  public int compare(Task firstTask, Task secondTask) {
    String firstCriteria = sortingCriteria.apply(firstTask);
    String secondCriteria = sortingCriteria.apply(secondTask);

    return secondCriteria.compareTo(firstCriteria);
  }
}
