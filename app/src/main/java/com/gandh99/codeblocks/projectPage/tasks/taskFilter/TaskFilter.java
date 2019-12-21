package com.gandh99.codeblocks.projectPage.tasks.taskFilter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.RadioGroup;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.authentication.AuthenticationInterceptor;
import com.gandh99.codeblocks.projectPage.tasks.api.Task;
import com.gandh99.codeblocks.projectPage.tasks.taskFilter.assignee.FilterByAssignee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import static com.gandh99.codeblocks.projectPage.tasks.dialog.FilterTaskDialog.ASSIGNEES_PREFERENCE;
import static com.gandh99.codeblocks.projectPage.tasks.dialog.FilterTaskDialog.DEADLINE_PREFERENCE;
import static com.gandh99.codeblocks.projectPage.tasks.dialog.FilterTaskDialog.FILTER_TASK_DIALOG_PREFERENCES;

public class TaskFilter {
  private int selectedAssigneesId, selectedDeadlineId;
  private RadioGroup radioGroupAssignees, radioGroupDeadline;
  private Map<RadioGroup, TaskFilterCriteria> radioGroupTaskFilterCriteriaMap;
  private AuthenticationInterceptor interceptor;

  @Inject
  public TaskFilter(AuthenticationInterceptor interceptor) {
    this.interceptor = interceptor;
  }

  private void init(Context context, View view) {
    // Get the radio groups
    radioGroupAssignees = view.findViewById(R.id.radioGroup_filter_assignees);
    radioGroupDeadline = view.findViewById(R.id.radioGroup_filter_deadline);

    loadRadioButtonsState(context);
    initRadioGroupTaskFilterCriteriaMap();
  }

  private void loadRadioButtonsState(Context context) {
    try {
      SharedPreferences sharedPreferences =
        context.getSharedPreferences(FILTER_TASK_DIALOG_PREFERENCES, Context.MODE_PRIVATE);

      // Restore the state of the checked radio buttons in each RadioGroup
      selectedAssigneesId = sharedPreferences.getInt(ASSIGNEES_PREFERENCE, -1);
      selectedDeadlineId = sharedPreferences.getInt(DEADLINE_PREFERENCE, -1);
      radioGroupAssignees.check(selectedAssigneesId);
      radioGroupDeadline.check(selectedDeadlineId);
    } catch (NullPointerException e) {
    }
  }

  private void initRadioGroupTaskFilterCriteriaMap() {
    radioGroupTaskFilterCriteriaMap = new HashMap<>();
    radioGroupTaskFilterCriteriaMap.put(radioGroupAssignees, new FilterByAssignee(interceptor));
  }

  public List<Task> filterTasks(Context context, View dialogView, List<Task> taskList) {
    init(context, dialogView);

    for (Map.Entry<RadioGroup, TaskFilterCriteria> entry : radioGroupTaskFilterCriteriaMap.entrySet()) {
      RadioGroup radioGroup = entry.getKey();
      TaskFilterCriteria taskFilterCriteria = entry.getValue();
      taskList = taskFilterCriteria.filterTasks(dialogView, radioGroup, taskList);
    }

    return taskList;
  }
}
