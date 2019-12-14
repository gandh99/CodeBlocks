package com.gandh99.codeblocks.projectPage.tasks;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.authentication.InputValidator;
import com.gandh99.codeblocks.projectPage.members.api.ProjectMember;
import com.gandh99.codeblocks.projectPage.members.viewModel.MemberViewModel;
import com.gandh99.codeblocks.projectPage.tasks.prioritySpinner.PrioritySpinnerAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class NewTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
  public static final int NEW_TASK_REQUEST_CODE = 100;
  public static final String INTENT_TASK_TITLE = "taskTitle";
  public static final String INTENT_TASK_DESCRIPTION = "taskDescription";
  public static final String INTENT_TASK_DEADLINE = "taskDeadline";
  public static final String INTENT_TASK_PRIORITY = "taskPriority";

  private DatePickerDialog datePickerDialog;
  private ImageView buttonDatePicker;
  private EditText editTextTaskTitle, editTextTaskDescription, editTextTaskDeadline;
  private String selectedPriority;
  private Spinner prioritySpinner;
  private PrioritySpinnerAdapter prioritySpinnerAdapter;
  private ChipGroup chipGroupMembers;
  private Button buttonCreateTask;
  private MemberViewModel memberViewModel;
  private List<ProjectMember> projectMemberList;

  @Inject
  InputValidator inputValidator;

  @Inject
  ViewModelProvider.Factory viewModelFactory;

  @RequiresApi(api = Build.VERSION_CODES.N)
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
    buttonDatePicker.setOnClickListener(view -> {
      datePickerDialog = new DatePickerDialog(NewTaskActivity.this);
      datePickerDialog.setOnDateSetListener(NewTaskActivity.this);
      datePickerDialog.show();
    });
    prioritySpinner = findViewById(R.id.spinner_priority);
    chipGroupMembers = findViewById(R.id.chipgroup_assign_members);
    buttonCreateTask = findViewById(R.id.button_create_task);

    initMemberViewModel();
    initPrioritySpinner();
    initCreateTaskButton();
  }

  private void configureDagger() {
    AndroidInjection.inject(this);
  }

  private void initMemberViewModel() {
    memberViewModel = ViewModelProviders.of(this, viewModelFactory).get(MemberViewModel.class);
    memberViewModel.getProjectMembers().observe(this, projectMembers -> {
      for (ProjectMember member : projectMembers) {
        Chip chip =
          (Chip) getLayoutInflater()
            .inflate(R.layout.chip_project_member, chipGroupMembers, false);
        chip.setText(member.getUsername());
        chip.setCheckable(true);
        chip.setCheckedIconVisible(true);
        chipGroupMembers.addView(chip);
      }
    });
  }

  private void initPrioritySpinner() {
    prioritySpinnerAdapter = new PrioritySpinnerAdapter(getApplicationContext());
    prioritySpinner.setAdapter(prioritySpinnerAdapter);

    String[] priority = prioritySpinnerAdapter.getPriorities();
    prioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedPriority = priority[i];
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });
  }

  @Override
  public boolean onSupportNavigateUp() {
    finish();
    return true;
  }

  @Override
  public void onDateSet(DatePicker datePicker, int year, int month, int day) {
    String date = year + "-" + (month + 1) + "-" + day;
    editTextTaskDeadline.setText(date);
  }

  private void initCreateTaskButton() {
    buttonCreateTask.setOnClickListener(view -> {
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
      returnIntent.putExtra(INTENT_TASK_PRIORITY, selectedPriority);
      setResult(RESULT_OK, returnIntent);
      finish();
    });

  }
}
