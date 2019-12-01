package com.gandh99.codeblocks.projectPage.tasks;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.authentication.InputValidator;
import com.gandh99.codeblocks.projectPage.tasks.api.TaskAPIService;
import com.gandh99.codeblocks.projectPage.tasks.viewModel.TaskViewModel;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTaskDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
  private EditText editTextTitle, editTextDescription;
  private TextView textViewSelectedDate;
  private Button buttonCreateTask;
  private ImageView buttonDatePicker;
  private TaskViewModel taskViewModel;
  private DatePickerDialog datePickerDialog;

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
    textViewSelectedDate = view.findViewById(R.id.dialog_task_selected_date);
    buttonCreateTask = view.findViewById(R.id.dialog_task_create);
    buttonDatePicker = view.findViewById(R.id.dialog_task_datePicker);

    buttonDatePicker.setOnClickListener(new View.OnClickListener() {
      @RequiresApi(api = Build.VERSION_CODES.N)
      @Override
      public void onClick(View view) {
        datePickerDialog = new DatePickerDialog(getContext());
        datePickerDialog.setOnDateSetListener(AddTaskDialog.this);
        datePickerDialog.show();
      }
    });

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
      @RequiresApi(api = Build.VERSION_CODES.O)
      @Override
      public void onClick(View view) {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String dateCreated = getCurrentDate();
        String deadline = textViewSelectedDate.getText().toString();

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
              Toast.makeText(getContext(), "Successfully created task", Toast.LENGTH_SHORT).show();
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

  @RequiresApi(api = Build.VERSION_CODES.O)
  private String getCurrentDate() {
    LocalDate localDate = LocalDate.now();
    return localDate.getYear() + "-" + localDate.getMonth().getValue() + "-" + localDate.getDayOfMonth();
  }

  @Override
  public void onDateSet(DatePicker datePicker, int year, int month, int day) {
    String date = year + "-" + (month + 1) + "-" + day;
    textViewSelectedDate.setText(date);
  }
}
