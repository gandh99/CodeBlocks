package com.gandh99.codeblocks.projectPage.tasks;

import androidx.fragment.app.Fragment;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.projectPage.tasks.api.Task;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TaskSorter {

  public static List<Task> sortTasks(Fragment fragment, List<Task> taskList, String sortBy, String order) {
    if (sortBy.equals(fragment.getString(R.string.date_created))) {
      Collections.sort(taskList, new SortByDateCreated());
    } else if (sortBy.equals(fragment.getString(R.string.deadline))) {
      Collections.sort(taskList, new SortByDeadline());
    }

    return taskList;
  }

  //TODO: Change
  private static class SortByDateCreated implements Comparator<Task> {

    @Override
    public int compare(Task t1, Task t2) {
      return t1.getDateCreated().compareTo(t2.getDateCreated());
    }
  }

  //TODO: Change
  private static class SortByDeadline implements Comparator<Task> {

    @Override
    public int compare(Task t1, Task t2) {
      return t1.getDeadline().compareTo(t2.getDeadline());
    }
  }
}
