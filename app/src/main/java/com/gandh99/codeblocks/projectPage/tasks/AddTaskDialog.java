package com.gandh99.codeblocks.projectPage.tasks;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.gandh99.codeblocks.R;

import dagger.android.support.AndroidSupportInjection;

public class AddTaskDialog extends DialogFragment {
  private EditText editTextTitle, editTextDescription, editTextDateCreated, editTextDeadline;
  private Button buttonCreateTask;

  public AddTaskDialog() {
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    AndroidSupportInjection.inject(this);

    View view =
      LayoutInflater
        .from(getContext())
        .inflate(R.layout.dialog_task, null);

    editTextTitle = view.findViewById(R.id.dialog_task_title);
    editTextDescription = view.findViewById(R.id.dialog_task_description);
    editTextDateCreated = view.findViewById(R.id.dialog_task_date_created);
    editTextDeadline = view.findViewById(R.id.dialog_task_deadline);
    buttonCreateTask = view.findViewById(R.id.dialog_task_create);

    setupCreateButton();

    return new AlertDialog.Builder(getActivity())
      .setView(view)
      .create();
  }

  private void setupCreateButton() {
    buttonCreateTask.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

      }
    });
  }

  private void refreshTaskList() {

  }
}
