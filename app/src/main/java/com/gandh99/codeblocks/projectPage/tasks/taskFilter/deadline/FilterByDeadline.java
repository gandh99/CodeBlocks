package com.gandh99.codeblocks.projectPage.tasks.taskFilter.deadline;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gandh99.codeblocks.App;
import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.projectPage.tasks.api.Task;
import com.gandh99.codeblocks.projectPage.tasks.taskFilter.TaskFilterCriteria;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterByDeadline implements TaskFilterCriteria {
  private Map<String, DeadlineCriteria> deadlineCriteriaMap = new HashMap<>();

  public FilterByDeadline() {
    deadlineCriteriaMap.put(App.getAppResources().getString(R.string.filter_deadline_all_deadlines), new DeadlineAll());
    deadlineCriteriaMap.put(App.getAppResources().getString(R.string.filter_deadline_less_than_1_day_left), new DeadlineOneDay());
    deadlineCriteriaMap.put(App.getAppResources().getString(R.string.filter_deadline_less_than_1_week_left), new DeadlineOneWeek());
    deadlineCriteriaMap.put(App.getAppResources().getString(R.string.filter_deadline_less_than_1_month_left), new DeadlineOneMonth());
  }

  @Override
  public List<Task> filterTasks(View view, RadioGroup radioGroup, List<Task> taskList) {
    // Get the selected method in which the tasks should be filtered by deadline
    int selectedDeadlineId = radioGroup.getCheckedRadioButtonId();
    RadioButton selectedRadioButton = view.findViewById(selectedDeadlineId);

    // Filter the task list according to the selected method by referring to the deadlineMap
    for (Map.Entry<String, DeadlineCriteria> entry : deadlineCriteriaMap.entrySet()) {
      String deadline = entry.getKey();

      if (deadline.equals(selectedRadioButton.getText().toString())) {
        DeadlineCriteria deadlineCriteria = entry.getValue();
        return deadlineCriteria.filter(taskList);
      }
    }

    return taskList;
  }
}
