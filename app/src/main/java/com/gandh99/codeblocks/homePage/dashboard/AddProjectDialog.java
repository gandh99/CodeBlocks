package com.gandh99.codeblocks.homePage.dashboard;

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
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.authentication.InputValidator;
import com.gandh99.codeblocks.homePage.dashboard.api.DashboardAPIService;
import com.gandh99.codeblocks.homePage.dashboard.viewModel.DashboardViewModel;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProjectDialog extends DialogFragment {
  private EditText editTextTitle, editTextLeader, editTextDescription;
  private Button buttonCreateProject;
  private DashboardViewModel dashboardViewModel;

  @Inject
  ViewModelProvider.Factory viewModelFactory;

  @Inject
  InputValidator inputValidator;

  @Inject
  DashboardAPIService dashboardAPIService;

  public AddProjectDialog() {
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    AndroidSupportInjection.inject(this);

    View view =
      LayoutInflater
        .from(getContext())
        .inflate(R.layout.dialog_project, null);

    editTextTitle = view.findViewById(R.id.dialog_project_title);
    editTextLeader = view.findViewById(R.id.dialog_project_leader);
    editTextDescription = view.findViewById(R.id.dialog_project_description);
    buttonCreateProject = view.findViewById(R.id.dialog_project_create);

    initViewModel();
    setupCreateButton();

    return new AlertDialog.Builder(getActivity())
      .setView(view)
      .create();
  }

  private void initViewModel() {
    dashboardViewModel = ViewModelProviders.of(this, viewModelFactory).get(DashboardViewModel.class);
  }

  private void setupCreateButton() {
    buttonCreateProject.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String title = editTextTitle.getText().toString();
        String leader = editTextLeader.getText().toString();
        String description = editTextDescription.getText().toString();

        if (inputValidator.isInvalidInput(title)
          || inputValidator.isInvalidInput(leader)
          || inputValidator.isInvalidInput(description)) {
          Toast.makeText(getContext(), "Please fill in all the required information",
            Toast.LENGTH_SHORT).show();
          return;
        }

        dashboardAPIService.createProject(title, leader, description).enqueue(new Callback<ResponseBody>() {
          @Override
          public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (response.isSuccessful()) {
              Toast.makeText(getContext(), "Successfully created project", Toast.LENGTH_SHORT).show();
              dismiss();
              refreshProjectList();
            }
          }

          @Override
          public void onFailure(Call<ResponseBody> call, Throwable t) {

          }
        });
      }
    });
  }

  private void refreshProjectList() {
    dashboardViewModel.refreshProjectList();
  }
}
