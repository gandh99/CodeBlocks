package com.gandh99.codeblocks.projectPage.tasks;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.authentication.InputValidator;
import com.gandh99.codeblocks.homePage.dashboard.viewModel.DashboardViewModel;
import com.gandh99.codeblocks.projectPage.tasks.api.TaskAPIService;
import com.gandh99.codeblocks.projectPage.tasks.viewModel.TaskViewModel;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTaskDialog extends DialogFragment {
  private EditText editTextTitle, editTextDescription, editTextDateCreated, editTextDeadline;
  private Button buttonCreateTask;
  private TaskViewModel taskViewModel;

  @Inject
  ViewModelProvider.Factory viewModelFactory;

  @Inject
  InputValidator inputValidator;

  @Inject
  TaskAPIService taskAPIService;

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

    initViewModel();
    setupCreateButton();

    return new AlertDialog.Builder(getActivity())
      .setView(view)
      .create();
  }

  private void initViewModel() {
    taskViewModel = ViewModelProviders.of(this, viewModelFactory).get(TaskViewModel.class);
  }

  private void setupCreateButton() {
    buttonCreateTask.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String dateCreated = editTextDateCreated.getText().toString();
        String deadline = editTextDeadline.getText().toString();

        if (inputValidator.isInvalidInput(title)
          || inputValidator.isInvalidInput(description)
          || inputValidator.isInvalidInput(dateCreated)
          || inputValidator.isInvalidInput(deadline)) {
          Toast.makeText(getContext(), "Please fill in all the required information",
            Toast.LENGTH_SHORT).show();
          return;
        }

        taskAPIService.createTask(title, description, dateCreated, deadline).enqueue(new Callback<ResponseBody>() {
          @Override
          public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (response.isSuccessful()) {
              Toast.makeText(getContext(), "Successfully created project", Toast.LENGTH_SHORT).show();
              dismiss();
              refreshTaskList();
            }
          }

          @Override
          public void onFailure(Call<ResponseBody> call, Throwable t) {

          }
        });
      }
    });
  }

  private void refreshTaskList() {
    taskViewModel.refreshTaskList();
  }
}
