package com.gandh99.codeblocks.projectPage.tasks.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.authentication.InputValidator;
import com.gandh99.codeblocks.common.dateFormatting.CustomDateFormatter;
import com.gandh99.codeblocks.projectPage.TaskDataLoader;
import com.gandh99.codeblocks.projectPage.members.api.ProjectMember;
import com.gandh99.codeblocks.projectPage.members.viewModel.MemberViewModel;
import com.gandh99.codeblocks.projectPage.tasks.api.Task;
import com.gandh99.codeblocks.projectPage.tasks.api.TaskAPIService;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gandh99.codeblocks.projectPage.TaskDataLoader.CATEGORY;
import static com.gandh99.codeblocks.projectPage.TaskDataLoader.DEADLINE;
import static com.gandh99.codeblocks.projectPage.TaskDataLoader.DESCRIPTION;
import static com.gandh99.codeblocks.projectPage.TaskDataLoader.PRIORITY;
import static com.gandh99.codeblocks.projectPage.TaskDataLoader.TITLE;
import static com.gandh99.codeblocks.projectPage.tasks.activity.EditTaskCategoriesActivity.TASK_CATEGORIES_LIST_INTENT;
import static com.gandh99.codeblocks.projectPage.tasks.fragment.TasksFragment.EDIT_TASK_INTENT;

public class NewTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
  public static final int NEW_TASK_REQUEST_CODE = 100;
  public static final int EDIT_TASK_CATEGORIES_REQUEST_CODE = 101;
  public static final int EDIT_TASK_REQUEST_CODE = 102;
  public static final String INTENT_TASK_TITLE = "taskTitle";
  public static final String INTENT_TASK_DESCRIPTION = "taskDescription";
  public static final String INTENT_TASK_DEADLINE = "taskDeadline";
  public static final String INTENT_TASK_PRIORITY = "taskPriority";

  private DatePickerDialog datePickerDialog;
  private ImageView buttonDatePicker;
  private EditText editTextTaskTitle, editTextTaskDescription, editTextTaskDeadline;
  private ChipGroup chipGroupAssignedMembers, chipGroupTaskPriority, chipGroupTaskCategories;
  private Chip chipEditTaskCategories;
  private Button buttonCreateTask;
  private MemberViewModel memberViewModel;

  /* Only for the purpose of editing a Task */
  private boolean editMode;
  private int taskID;

  @Inject
  InputValidator inputValidator;

  @Inject
  TaskAPIService taskAPIService;

  @Inject
  ViewModelProvider.Factory viewModelFactory;

  @RequiresApi(api = Build.VERSION_CODES.O)
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
    chipGroupTaskPriority = findViewById(R.id.chipgroup_task_priority);
    chipGroupAssignedMembers = findViewById(R.id.chipgroup_assign_members);
    chipGroupTaskCategories = findViewById(R.id.chipgroup_task_categories);
    chipEditTaskCategories = findViewById(R.id.chip_edit_categories);
    buttonCreateTask = findViewById(R.id.button_create_task);

    initMemberViewModel();
    initPriorityChipGroup();
    initEditTaskCategories();
    loadSavedData();
    initCreateTaskButton();
  }

  private void configureDagger() {
    AndroidInjection.inject(this);
  }

  private void initMemberViewModel() {
    memberViewModel = ViewModelProviders.of(this, viewModelFactory).get(MemberViewModel.class);
    memberViewModel.getProjectMembers().observe(this, projectMembers -> {
      chipGroupAssignedMembers.removeAllViews();
      for (ProjectMember member : projectMembers) {
        Chip chip =
          (Chip) getLayoutInflater()
            .inflate(R.layout.chip_checkable, chipGroupAssignedMembers, false);
        chip.setText(member.getUsername());
        chip.setCheckable(true);
        chip.setCheckedIconVisible(true);
        chipGroupAssignedMembers.addView(chip);
      }
    });
  }

  private void initPriorityChipGroup() {
    String[] priorities = getResources().getStringArray(R.array.priority);
    int[] priorityColours = new int[] {
      R.color.colorWhite,
      R.color.colorGreen,
      R.color.colorOrange,
      R.color.colorRed
    };

    for (int i = 0; i < priorities.length; i++) {
      Chip chip =
        (Chip) getLayoutInflater()
          .inflate(R.layout.chip_checkable, chipGroupTaskPriority, false);
      chip.setText(priorities[i]);
      chip.setCheckable(true);
      chip.setCheckedIconVisible(true);
      chip.setChipBackgroundColorResource(priorityColours[i]);
      chipGroupTaskPriority.addView(chip);
    }
  }

  private void initEditTaskCategories() {
    chipEditTaskCategories.setOnClickListener(view -> {
      Intent selectCategoriesIntent = new Intent(getApplicationContext(), EditTaskCategoriesActivity.class);
      startActivityForResult(selectCategoriesIntent, EDIT_TASK_CATEGORIES_REQUEST_CODE);
    });
  }

  /* Only works if this activity was started with the intention to edit a Task */
  private void loadSavedData() {
    Intent data = getIntent();

    try {
      Task task = (Task) data.getSerializableExtra(EDIT_TASK_INTENT);
      taskID = task.getId();
      Map<String, View> stringViewMap = new HashMap<>();
      stringViewMap.put(TITLE, editTextTaskTitle);
      stringViewMap.put(DESCRIPTION, editTextTaskDescription);
      stringViewMap.put(DEADLINE, editTextTaskDeadline);
      stringViewMap.put(PRIORITY, chipGroupTaskPriority);
      stringViewMap.put(CATEGORY, chipGroupTaskCategories);
      TaskDataLoader.loadData(task, stringViewMap, getLayoutInflater());
      editMode = true;
    } catch (NullPointerException e) {
      // Reaches this point if this activity was launched from a fab (i.e. no data to load)
      editMode = false;
    }
  }

  private String getSelectedPriority() {
    String defaultPriority = "NONE";

    for (int i = 0; i < chipGroupTaskPriority.getChildCount(); i++) {
      Chip chip = (Chip) chipGroupTaskPriority.getChildAt(i);
      if (chip.isChecked()) {
        return chip.getText().toString();
      }
    }

    return defaultPriority;
  }

  /* Get from a chip group the texts of all the chips that were selected */
  private List<String> getChipGroupCheckedSelection(ChipGroup chipGroup) {
    List<String> selection = new ArrayList<>();

    for (int i = 0; i < chipGroup.getChildCount(); i++) {
      Chip chip = (Chip) chipGroup.getChildAt(i);
      if (chip.isChecked()) {
        selection.add(chip.getText().toString());
      }
    }

    return selection;
  }

  /* Get from a chip group the texts of all the chips in it */
  private List<String> getChipGroupContents(ChipGroup chipGroup) {
    List<String> selection = new ArrayList<>();

    for (int i = 0; i < chipGroup.getChildCount(); i++) {
      Chip chip = (Chip) chipGroup.getChildAt(i);
      selection.add(chip.getText().toString());
    }

    return selection;
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

  @RequiresApi(api = Build.VERSION_CODES.O)
  private void initCreateTaskButton() {
    buttonCreateTask.setOnClickListener(view -> {
      String taskTitle = editTextTaskTitle.getText().toString();
      String taskDescription = editTextTaskDescription.getText().toString();
      String taskDateCreated = CustomDateFormatter.getCurrentDate();
      String taskDeadline = editTextTaskDeadline.getText().toString();
      String taskPriority = getSelectedPriority();
      List<String> assignedMembers = getChipGroupCheckedSelection(chipGroupAssignedMembers);
      List<String> selectedCategories = getChipGroupContents(chipGroupTaskCategories);

      // Validate user inputs
      if (inputValidator.isInvalidInput(taskTitle)
        || inputValidator.isInvalidInput(taskDescription)
        || inputValidator.isInvalidInput(taskDeadline)) {
        Toast.makeText(NewTaskActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
        return;
      }

      // Make the API call to the server to update the task
      if (editMode) {
        taskAPIService
          .updateTask(taskID, taskTitle, taskDescription, taskDateCreated, taskDeadline, taskPriority,
            assignedMembers, selectedCategories, "False")
          .enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
              if (response.isSuccessful()) {
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                NewTaskActivity.this.finish();
              }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
          });

        return;
      }

      // Make the API call to the server to create the task
      taskAPIService
        .createTask(taskTitle, taskDescription, taskDateCreated, taskDeadline, taskPriority,
          assignedMembers, selectedCategories)
        .enqueue(new Callback<ResponseBody>() {
          @Override
          public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (response.isSuccessful()) {
              Intent returnIntent = new Intent();
              setResult(RESULT_OK, returnIntent);
              NewTaskActivity.this.finish();
            }
          }

          @Override
          public void onFailure(Call<ResponseBody> call, Throwable t) {
          }
        });
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == EDIT_TASK_CATEGORIES_REQUEST_CODE && resultCode == RESULT_OK) {
      try {
        List<String> selectedCategories = data.getStringArrayListExtra(TASK_CATEGORIES_LIST_INTENT);

        for (String category : selectedCategories) {
          Chip chip =
            (Chip) getLayoutInflater()
              .inflate(R.layout.chip_closable, chipGroupTaskCategories, false);
          chip.setText(category);
          chip.setOnCloseIconClickListener(view -> chipGroupTaskCategories.removeView(view));
          chipGroupTaskCategories.addView(chip);
        }
      } catch (NullPointerException e) {
        // Empty
      }
    }
  }
}
