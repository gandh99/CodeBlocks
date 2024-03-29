package com.gandh99.codeblocks.projectPage.tasks.dialog;

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
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.projectPage.GenericTaskAdapter;
import com.gandh99.codeblocks.projectPage.tasks.api.Task;
import com.gandh99.codeblocks.projectPage.tasks.taskFilter.TaskFilter;
import com.gandh99.codeblocks.projectPage.tasks.taskSorter.TaskSorter;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class SortTaskDialog extends DialogFragment {
  public static final String SORT_TASK_DIALOG_PREFERENCES =
    "com.gandh99.codeblocks.projectPage.tasks.dialog.SortTaskDialog";
  public static final String SORT_BY_PREFERENCE = "sortBy";
  public static final String ORDER_PREFERENCE = "order";

  private SharedPreferences sharedPreferences;
  private Button buttonSave, buttonCancel;
  private RadioGroup radioGroupSortBy, radioGroupOrder;
  private int selectedSortById, selectedOrderId;
  private View dialogSortTaskView, dialogFilterTaskView;
  private GenericTaskAdapter adapter;

  @Inject
  ViewModelProvider.Factory viewModelFactory;

  @Inject
  TaskFilter taskFilter;

  @Inject
  TaskSorter taskSorter;

  public SortTaskDialog() {
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    AndroidSupportInjection.inject(this);

    dialogSortTaskView =
      LayoutInflater
        .from(getContext())
        .inflate(R.layout.dialog_sort_task, null);

    dialogFilterTaskView =
      LayoutInflater
        .from(getContext())
        .inflate(R.layout.dialog_filter_task, null);

    // Get the view items
    radioGroupSortBy = dialogSortTaskView.findViewById(R.id.radioGroup_sort_by);
    radioGroupOrder = dialogSortTaskView.findViewById(R.id.radioGroup_order);
    buttonSave = dialogSortTaskView.findViewById(R.id.button_filter_task_save);
    buttonCancel = dialogSortTaskView.findViewById(R.id.button_filter_task_cancel);

    initRadioButtons();
    initSaveButton();
    initCancelButton();

    return new AlertDialog.Builder(getActivity())
      .setView(dialogSortTaskView)
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
  }

  private void initSaveButton() {
    buttonSave.setOnClickListener(view -> {
      saveRadioButtons();
      sortTasks();
      dismiss();
    });
  }

  private void saveRadioButtons() {
    // Get the IDs of the selected radio buttons
    selectedSortById = radioGroupSortBy.getCheckedRadioButtonId();
    selectedOrderId = radioGroupOrder.getCheckedRadioButtonId();

    // Save the state of the checked radio buttons in their respective RadioGroup
    sharedPreferences = getContext().getSharedPreferences(SORT_TASK_DIALOG_PREFERENCES, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putInt(SORT_BY_PREFERENCE, selectedSortById);
    editor.putInt(ORDER_PREFERENCE, selectedOrderId);
    editor.apply();
  }

  private void sortTasks() {
    List<Task> displayTaskList = taskFilter.filterTasks(this.getContext(), dialogFilterTaskView, adapter.getListOfAllTasks()) ;
    displayTaskList = taskSorter.sortTasks(this.getContext(), dialogSortTaskView, displayTaskList);
    adapter.updateDisplayTaskList(displayTaskList);
  }

  public void setGenericTaskAdapter(GenericTaskAdapter adapter) {
    this.adapter = adapter;
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
