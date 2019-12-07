package com.gandh99.codeblocks.projectPage.tasks.taskSorter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.projectPage.tasks.api.Task;
import com.gandh99.codeblocks.projectPage.tasks.taskSorter.method.SorterByDateCreated;
import com.gandh99.codeblocks.projectPage.tasks.taskSorter.method.SorterByDeadline;
import com.gandh99.codeblocks.projectPage.tasks.taskSorter.method.TaskSortMethod;
import com.gandh99.codeblocks.projectPage.tasks.taskSorter.order.AscendingOrder;
import com.gandh99.codeblocks.projectPage.tasks.taskSorter.order.DescendingOrder;
import com.gandh99.codeblocks.projectPage.tasks.taskSorter.order.TaskOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import static com.gandh99.codeblocks.projectPage.tasks.SortTaskDialog.ORDER_PREFERENCE;
import static com.gandh99.codeblocks.projectPage.tasks.SortTaskDialog.SORT_BY_PREFERENCE;
import static com.gandh99.codeblocks.projectPage.tasks.SortTaskDialog.SORT_TASK_DIALOG_PREFERENCES;

public class TaskSorter {
  private int selectedSortById, selectedOrderId;
  private RadioGroup radioGroupSortBy, radioGroupOrder;
  private RadioButton dateCreated, deadline, orderAscending, orderDescending;
  private Map<RadioButton, TaskSortMethod> radioButtonTaskSortMethodMap = new HashMap<>();
  private Map<RadioButton, TaskOrder> radioButtonTaskOrderMap = new HashMap<>();

  @Inject
  public TaskSorter() {
  }

  private void init(Context context, View view) {
    // Get the radio groups
    radioGroupSortBy = view.findViewById(R.id.radioGroup_sort_by);
    radioGroupOrder = view.findViewById(R.id.radioGroup_order);

    try {
      SharedPreferences sharedPreferences =
        context.getSharedPreferences(SORT_TASK_DIALOG_PREFERENCES, Context.MODE_PRIVATE);

      // Restore the state of the checked radio buttons in each RadioGroup
      selectedSortById = sharedPreferences.getInt(SORT_BY_PREFERENCE, -1);
      selectedOrderId = sharedPreferences.getInt(ORDER_PREFERENCE, -1);
      radioGroupSortBy.check(selectedSortById);
      radioGroupOrder.check(selectedOrderId);
    } catch (NullPointerException e) {
    }

    // Get the radio buttons
    dateCreated = view.findViewById(R.id.radioButton_date_created);
    deadline = view.findViewById(R.id.radioButton_deadline);
    orderAscending = view.findViewById(R.id.radioButton_ascending);
    orderDescending = view.findViewById(R.id.radioButton_descending);

    // Map the radio buttons to their sorting functionality
    radioButtonTaskSortMethodMap.put(dateCreated, new SorterByDateCreated());
    radioButtonTaskSortMethodMap.put(deadline, new SorterByDeadline());

    // Map the radio buttons to the order in which they should be sorted
    radioButtonTaskOrderMap.put(orderAscending, new AscendingOrder());
    radioButtonTaskOrderMap.put(orderDescending, new DescendingOrder());
  }

  public List<Task> sortTasks(Context context, View view, List<Task> taskList) {
    init(context, view);

    // Get the selected radio buttons
    selectedSortById = radioGroupSortBy.getCheckedRadioButtonId();
    selectedOrderId = radioGroupOrder.getCheckedRadioButtonId();
    RadioButton selectedSortingMethod = view.findViewById(selectedSortById);
    RadioButton selectedOrder = view.findViewById(selectedOrderId);

    // Sort the tasks based on the user's selection
    TaskOrder order = radioButtonTaskOrderMap.get(selectedOrder);
    TaskSortMethod taskSortMethod = radioButtonTaskSortMethodMap.get(selectedSortingMethod);
    taskSortMethod.sortTasks(taskList, order);
    return taskList;
  }
}
