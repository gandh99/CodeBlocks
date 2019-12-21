package com.gandh99.codeblocks.projectPage.tasks.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.projectPage.GenericTaskAdapter;
import com.gandh99.codeblocks.projectPage.tasks.api.Task;
import com.gandh99.codeblocks.projectPage.tasks.taskFilter.TaskFilter;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class FilterTaskDialog extends DialogFragment {
  public static final String FILTER_TASK_DIALOG_PREFERENCES = "filterDialog";
  public static final String ASSIGNEES_PREFERENCE = "assigneesPreference";
  public static final String DEADLINE_PREFERENCE = "deadlinePreference";
  private View dialogView;
  private GenericTaskAdapter adapter;
  private SharedPreferences sharedPreferences;
  private Button buttonSave, buttonCancel;
  private RadioGroup radioGroupAssignees, radioGroupDeadline;
  private int selectedAssigneesId, selectedDeadlineId;

  @Inject
  TaskFilter taskFilter;

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    AndroidSupportInjection.inject(this);

    dialogView =
      LayoutInflater
        .from(getContext())
        .inflate(R.layout.dialog_filter_task, null);

    // Get the view items
    radioGroupAssignees = dialogView.findViewById(R.id.radioGroup_filter_assignees);
    radioGroupDeadline = dialogView.findViewById(R.id.radioGroup_filter_deadline);
    buttonSave = dialogView.findViewById(R.id.button_filter_task_save);
    buttonCancel = dialogView.findViewById(R.id.button_filter_task_cancel);

    initRadioButtons();
    initSaveButton();
    initCancelButton();

    return new AlertDialog.Builder(getActivity())
      .setView(dialogView)
      .create();
  }

  private void initRadioButtons() {
    try {
      sharedPreferences = getContext().getSharedPreferences(FILTER_TASK_DIALOG_PREFERENCES, Context.MODE_PRIVATE);

      // Restore the state of the checked radio buttons in each RadioGroup
      selectedAssigneesId = sharedPreferences.getInt(ASSIGNEES_PREFERENCE, -1);
      selectedDeadlineId = sharedPreferences.getInt(DEADLINE_PREFERENCE, -1);
      radioGroupAssignees.check(selectedAssigneesId);
      radioGroupDeadline.check(selectedDeadlineId);
    } catch (NullPointerException e) {
    }
  }

  private void initSaveButton() {
    buttonSave.setOnClickListener(view -> {
      saveRadioButtons();
      filterTasks();
      dismiss();
    });
  }

  private void saveRadioButtons() {
    // Get the IDs of the selected radio buttons
    selectedAssigneesId = radioGroupAssignees.getCheckedRadioButtonId();
    selectedDeadlineId = radioGroupDeadline.getCheckedRadioButtonId();

    // Save the state of the checked radio buttons in their respective RadioGroup
    sharedPreferences = getContext().getSharedPreferences(FILTER_TASK_DIALOG_PREFERENCES, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putInt(ASSIGNEES_PREFERENCE, selectedAssigneesId);
    editor.putInt(DEADLINE_PREFERENCE, selectedDeadlineId);
    editor.apply();
  }

  private void filterTasks() {
    // Filter the task and update the adapter
    List<Task> filteredTaskList = taskFilter.filterTasks(this.getContext(), dialogView, adapter.getListOfAllTasks());
    adapter.updateDisplayTaskList(filteredTaskList);
  }

  private void initCancelButton() {
    buttonCancel.setOnClickListener(view -> dismiss());
  }

  public void setGenericTaskAdapter(GenericTaskAdapter adapter) {
    this.adapter = adapter;
  }

}
