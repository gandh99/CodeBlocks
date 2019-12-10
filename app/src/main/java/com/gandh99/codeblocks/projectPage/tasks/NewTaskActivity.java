package com.gandh99.codeblocks.projectPage.tasks;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.authentication.InputValidator;
import com.gandh99.codeblocks.projectPage.tasks.api.TaskAPIService;
import com.gandh99.codeblocks.projectPage.tasks.prioritySpinner.PrioritySpinnerAdapter;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class NewTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
  public static final int NEW_TASK_REQUEST_CODE = 100;
  public static final String INTENT_TASK_TITLE = "taskTitle";
  public static final String INTENT_TASK_DESCRIPTION = "taskDescription";
  public static final String INTENT_TASK_DEADLINE = "taskDeadline";

  private DatePickerDialog datePickerDialog;
  private ImageView buttonDatePicker;
  private EditText editTextTaskTitle, editTextTaskDescription, editTextTaskDeadline;
  private Spinner prioritySpinner;
  private PrioritySpinnerAdapter prioritySpinnerAdapter;
  private Button buttonCreateTask;

  @Inject
  InputValidator inputValidator;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_task);
    getSupportActionBar().setTitle("Create New Task");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    configureDagger();

    editTextTaskTitle = findViewById(R.id.editText_task_title);
    editTextTaskDescription = findViewById(R.id.editText_task_description);
    editTextTaskDeadline = findViewById(R.id.editText_task_deadline);
    buttonDatePicker = findViewById(R.id.button_date_picker);
    buttonDatePicker.setOnClickListener(new View.OnClickListener() {
      @RequiresApi(api = Build.VERSION_CODES.N)
      @Override
      public void onClick(View view) {
        datePickerDialog = new DatePickerDialog(NewTaskActivity.this);
        datePickerDialog.setOnDateSetListener(NewTaskActivity.this);
        datePickerDialog.show();
      }
    });
    prioritySpinner = findViewById(R.id.spinner_priority);
    buttonCreateTask = findViewById(R.id.button_create_task);

    initPrioritySpinner();
    initCreateTaskButton();
  }

  private void initPrioritySpinner() {
    prioritySpinnerAdapter = new PrioritySpinnerAdapter(getApplicationContext());
    prioritySpinner.setAdapter(prioritySpinnerAdapter);
  }

  @Override
  public boolean onSupportNavigateUp() {
    finish();
    return true;
  }

  private void configureDagger() {
    AndroidInjection.inject(this);
  }

  @Override
  public void onDateSet(DatePicker datePicker, int year, int month, int day) {
    String date = year + "-" + (month + 1) + "-" + day;
    editTextTaskDeadline.setText(date);
  }

  private void initCreateTaskButton() {
    buttonCreateTask.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String taskTitle = editTextTaskTitle.getText().toString();
        String taskDescription = editTextTaskDescription.getText().toString();
        String taskDeadline = editTextTaskDeadline.getText().toString();

        if (inputValidator.isInvalidInput(taskTitle)
          || inputValidator.isInvalidInput(taskDescription)
          || inputValidator.isInvalidInput(taskDeadline)) {
          Toast.makeText(NewTaskActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
          return;
        }

        // If input is valid, return the values
        Intent returnIntent = new Intent();
        returnIntent.putExtra(INTENT_TASK_TITLE, taskTitle);
        returnIntent.putExtra(INTENT_TASK_DESCRIPTION, taskDescription);
        returnIntent.putExtra(INTENT_TASK_DEADLINE, taskDeadline);
        setResult(RESULT_OK, returnIntent);
        finish();
      }
    });

  }
}
