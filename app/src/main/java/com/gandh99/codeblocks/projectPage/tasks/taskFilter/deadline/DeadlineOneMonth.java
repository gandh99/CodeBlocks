package com.gandh99.codeblocks.projectPage.tasks.taskFilter.deadline;

import android.nfc.FormatException;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.gandh99.codeblocks.common.dateFormatting.CustomDateFormatter;
import com.gandh99.codeblocks.projectPage.tasks.api.Task;

import java.util.ArrayList;
import java.util.List;

import static com.gandh99.codeblocks.common.dateFormatting.DateUnitContainer.DateUnitWrtMinutes.DAY;
import static com.gandh99.codeblocks.common.dateFormatting.DateUnitContainer.DateUnitWrtMinutes.HOUR;
import static com.gandh99.codeblocks.common.dateFormatting.DateUnitContainer.DateUnitWrtMinutes.MINUTES;
import static com.gandh99.codeblocks.common.dateFormatting.DateUnitContainer.DateUnitWrtMinutes.WEEK;

public class DeadlineOneMonth implements DeadlineCriteria {
  @RequiresApi(api = Build.VERSION_CODES.O)
  @Override
  public List<Task> filter(List<Task> taskList) {
    List<Task> filteredTaskList = new ArrayList<>();

    for (Task task : taskList) {
      String deadline = task.getDeadline();
      try {
        String remainingTime = CustomDateFormatter.getRemainingTime(deadline);
        String remainingTimeUnits = remainingTime.split(" ")[1];

        if (remainingTimeUnits.equals(WEEK.getUnitShortForm())
          || remainingTimeUnits.equals(DAY.getUnitShortForm())
          || remainingTimeUnits.equals(HOUR.getUnitShortForm())
          || remainingTimeUnits.equals(MINUTES.getUnitShortForm())) {
          filteredTaskList.add(task);
        }
      } catch (FormatException |RuntimeException e) {
        e.printStackTrace();
      }
    }

    return filteredTaskList;
  }
}
