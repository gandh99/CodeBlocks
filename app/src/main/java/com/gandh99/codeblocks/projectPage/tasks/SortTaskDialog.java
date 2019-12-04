package com.gandh99.codeblocks.projectPage.tasks;

import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.projectPage.tasks.viewModel.TaskViewModel;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class SortTaskDialog extends DialogFragment {
  private Button buttonSave, buttonCancel;
  private TaskViewModel taskViewModel;

  @Inject
  ViewModelProvider.Factory viewModelFactory;

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

    // Get the view items
    buttonSave = view.findViewById(R.id.button_sort_task_save);
    buttonCancel = view.findViewById(R.id.button_sort_task_cancel);

    initViewModel();

    return new AlertDialog.Builder(getActivity())
      .setView(view)
      .create();
  }

  private void initViewModel() {
    taskViewModel = ViewModelProviders.of(this, viewModelFactory).get(TaskViewModel.class);
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
