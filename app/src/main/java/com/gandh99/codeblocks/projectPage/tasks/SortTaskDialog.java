package com.gandh99.codeblocks.projectPage.tasks;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.projectPage.tasks.api.Task;
import com.gandh99.codeblocks.projectPage.tasks.taskSorter.AscendingOrder;
import com.gandh99.codeblocks.projectPage.tasks.taskSorter.DescendingOrder;
import com.gandh99.codeblocks.projectPage.tasks.taskSorter.SorterByDateCreated;
import com.gandh99.codeblocks.projectPage.tasks.taskSorter.SorterByDeadline;
import com.gandh99.codeblocks.projectPage.tasks.taskSorter.TaskOrder;
import com.gandh99.codeblocks.projectPage.tasks.taskSorter.TaskSortMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class SortTaskDialog extends DialogFragment {
  public static final String SORT_TASK_DIALOG_PREFERENCES =
    "com.gandh99.codeblocks.projectPage.tasks.SortTaskDialog";
  public static final String SORT_BY_PREFERENCE = "sortBy";
  public static final String ORDER_PREFERENCE = "order";

  private SharedPreferences sharedPreferences;
  private Button buttonSave, buttonCancel;
  private RadioGroup radioGroupSortBy, radioGroupOrder;
  private int selectedSortById, selectedOrderId;
  private View dialogView;
  private Map<RadioButton, TaskSortMethod> radioButtonTaskSortMethodMap = new HashMap<>();
  private Map<RadioButton, TaskOrder> radioButtonTaskOrderMap = new HashMap<>();

  @Inject
  ViewModelProvider.Factory viewModelFactory;

  @Inject
  TaskAdapter taskAdapter;

  public SortTaskDialog() {
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    AndroidSupportInjection.inject(this);

    View view =
      LayoutInflater
      .from(getContext())
      .inflate(R.layout.dialog_sort_task, null);
    dialogView = view;

    // Get the view items
    radioGroupSortBy = view.findViewById(R.id.radioGroup_sort_by);
    radioGroupOrder = view.findViewById(R.id.radioGroup_order);
    buttonSave = view.findViewById(R.id.button_sort_task_save);
    buttonCancel = view.findViewById(R.id.button_sort_task_cancel);

    initRadioButtons();
    initSaveButton();
    initCancelButton();

    return new AlertDialog.Builder(getActivity())
      .setView(view)
      .create();
  }

  private void initRadioButtons() {
    try {
      sharedPreferences = getContext().getSharedPreferences(SORT_TASK_DIALOG_PREFERENCES, Context.MODE_PRIVATE);

      // Restore the state of the checked radio buttons in each RadioGroup
      selectedSortById = sharedPreferences.getInt(SORT_BY_PREFERENCE, -1);
      selectedOrderId = sharedPreferences.getInt(ORDER_PREFERENCE, -1);
      radioGroupSortBy.check(selectedSortById);
      radioGroupOrder.check(selectedOrderId);
    } catch (NullPointerException e) {
    }

    // Get the radio buttons
    RadioButton dateCreated = dialogView.findViewById(R.id.radioButton_date_created);
    RadioButton deadline = dialogView.findViewById(R.id.radioButton_deadline);
    RadioButton orderAscending = dialogView.findViewById(R.id.radioButton_ascending);
    RadioButton orderDescending = dialogView.findViewById(R.id.radioButton_descending);

    // Map the radio buttons to their sorting functionality
    radioButtonTaskSortMethodMap.put(dateCreated, new SorterByDateCreated());
    radioButtonTaskSortMethodMap.put(deadline, new SorterByDeadline());

    // Map the radio buttons to the order in which they should be sorted
    radioButtonTaskOrderMap.put(orderAscending, new AscendingOrder());
    radioButtonTaskOrderMap.put(orderDescending, new DescendingOrder());
  }

  private void initSaveButton() {
    buttonSave.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        selectedSortById = radioGroupSortBy.getCheckedRadioButtonId();
        selectedOrderId = radioGroupOrder.getCheckedRadioButtonId();
        
        saveRadioButtons();
        sortTasks();
        dismiss();
      }
    });
  }

  private void saveRadioButtons() {
    // Save the state of the checked radio buttons in their respective RadioGroup
    sharedPreferences = getContext().getSharedPreferences(SORT_TASK_DIALOG_PREFERENCES, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putInt(SORT_BY_PREFERENCE, selectedSortById);
    editor.putInt(ORDER_PREFERENCE, selectedOrderId);
    editor.apply();
  }

  private void sortTasks() {
    // Get the selected radio buttons
    RadioButton selectedSortingMethod = dialogView.findViewById(selectedSortById);
    RadioButton selectedOrder = dialogView.findViewById(selectedOrderId);

    // Sort the tasks based on the user's selection
    List<Task> tasks = taskAdapter.getTaskList();
    TaskOrder order = radioButtonTaskOrderMap.get(selectedOrder);
    TaskSortMethod taskSortMethod = radioButtonTaskSortMethodMap.get(selectedSortingMethod);
    taskSortMethod.sortTasks(tasks, order);
    taskAdapter.updateList(tasks);
  }

  private void initCancelButton() {
    buttonCancel.setOnClickListener(view -> dismiss());
  }

  @Override
  public void onResume() {
    super.onResume();
    /* The following portion is to size the window */
    Window window = getDialog().getWindow();
    Point size = new Point();

    Display display = window.getWindowManager().getDefaultDisplay();
    display.getSize(size);

    int width = size.x;
    window.setLayout((int) (width * 0.75), WindowManager.LayoutParams.WRAP_CONTENT);
  }

}
