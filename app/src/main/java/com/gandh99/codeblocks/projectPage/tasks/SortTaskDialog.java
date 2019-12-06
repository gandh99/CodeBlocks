package com.gandh99.codeblocks.projectPage.tasks;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.projectPage.tasks.api.Task;
import com.gandh99.codeblocks.projectPage.tasks.viewModel.TaskViewModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class SortTaskDialog extends DialogFragment {
  public static final String SORT_TASK_DIALOG_PREFERENCES =
    "com.gandh99.codeblocks.projectPage.tasks.SortTaskDialog";
  public static final String SORT_BY_PREFERENCE = "sortBy";
  public static final String ORDER_PREFERENCE = "order";

  private SharedPreferences sharedPreferences;
  private Button buttonSave, buttonCancel;
  private TaskViewModel taskViewModel;
  private RadioGroup radioGroupSortBy, radioGroupOrder;
  private int selectedSortById, selectedOrderId;
  private View dialogView;

  @Inject
  ViewModelProvider.Factory viewModelFactory;

  @Inject
  TaskListAdapter taskListAdapter;

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

    initViewModel();
    initRadioButtons();
    initSaveButton();
    initCancelButton();

    return new AlertDialog.Builder(getActivity())
      .setView(view)
      .create();
  }

  private void initViewModel() {
    taskViewModel = ViewModelProviders.of(this, viewModelFactory).get(TaskViewModel.class);
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
    buttonSave.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        selectedSortById = radioGroupSortBy.getCheckedRadioButtonId();
        selectedOrderId = radioGroupOrder.getCheckedRadioButtonId();
        
        saveRadioButtons();
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
    
    // Sort the tasks
    sortTasks();
  }

  //TODO: Change
  private void sortTasks() {
    String sortBy = ((RadioButton) (dialogView.findViewById(selectedSortById))).getText().toString();
    String order = ((RadioButton) (dialogView.findViewById(selectedOrderId))).getText().toString();

    List<Task> tasks = taskViewModel.getTasks().getValue();

    if (sortBy.equals(getString(R.string.date_created))) {
      Collections.sort(tasks, new SortByDateCreated());
      taskViewModel.updateTaskList(tasks);
    } else if (sortBy.equals(getString(R.string.deadline))) {
      Collections.sort(tasks, new SortByDeadline());
      taskViewModel.updateTaskList(tasks);
    }
  }

  //TODO: Change
  class SortByDateCreated implements Comparator<Task> {

    @Override
    public int compare(Task t1, Task t2) {
      return t1.getDateCreated().compareTo(t2.getDateCreated());
    }
  }

  //TODO: Change
  class SortByDeadline implements Comparator<Task> {

    @Override
    public int compare(Task t1, Task t2) {
      return t1.getDeadline().compareTo(t2.getDeadline());
    }
  }

  private void initCancelButton() {
    buttonCancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        dismiss();
      }
    });
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

  private void refreshTaskList() {
    taskViewModel.refreshTaskList();
  }
}
