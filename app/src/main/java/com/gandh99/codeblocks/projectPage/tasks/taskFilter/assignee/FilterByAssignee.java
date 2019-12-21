package com.gandh99.codeblocks.projectPage.tasks.taskFilter.assignee;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gandh99.codeblocks.App;
import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.authentication.AuthenticationInterceptor;
import com.gandh99.codeblocks.projectPage.tasks.api.Task;
import com.gandh99.codeblocks.projectPage.tasks.taskFilter.TaskFilterCriteria;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class FilterByAssignee implements TaskFilterCriteria {
  private Map<String, AssigneeCriteria> assigneeCriteriaMap = new HashMap<>();

  @Inject
  public FilterByAssignee(AuthenticationInterceptor interceptor) {
    assigneeCriteriaMap.put(App.getAppResources().getString(R.string.filter_assignee_anyone), new AssigneeAnyone());
    assigneeCriteriaMap.put(App.getAppResources().getString(R.string.filter_assignee_me), new AssigneeMe(interceptor));
  }

  @Override
  public List<Task> filterTasks(View view, RadioGroup radioGroup, List<Task> taskList) {
    int selectedAssigneeId = radioGroup.getCheckedRadioButtonId();
    RadioButton radioButton = view.findViewById(selectedAssigneeId);

    for (Map.Entry<String, AssigneeCriteria> entry : assigneeCriteriaMap.entrySet()) {
      String criteria = entry.getKey();

      if (criteria.equals(radioButton.getText().toString())) {
        AssigneeCriteria assigneeCriteria = entry.getValue();
        return assigneeCriteria.filter(taskList);
      }
    }

    return taskList;
  }
}
