package com.gandh99.codeblocks.projectPage.tasks.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.authentication.InputValidator;
import com.gandh99.codeblocks.projectPage.tasks.api.TaskAPIService;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTaskCategoriesActivity extends AppCompatActivity {
  private static final String TAG = "EditTaskCategoriesActiv";
  public static final String TASK_CATEGORIES_LIST_INTENT = "taskCategoriesListIntent";
  private EditText editTextTaskCategory;
  private Button buttonCreateTaskCategory, buttonDone;
  private ChipGroup chipGroupSelectedCategories;

  @Inject
  InputValidator inputValidator;

  @Inject
  TaskAPIService taskAPIService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_task_categories);

    editTextTaskCategory = findViewById(R.id.editText_task_category);
    buttonCreateTaskCategory = findViewById(R.id.button_create_task_category);
    chipGroupSelectedCategories = findViewById(R.id.chipgroup_selected_categories);
    buttonDone = findViewById(R.id.button_task_category_done);

    initDagger();
    initToolbar();
    loadTaskCategories();
    initCreateTaskCategoryButton();
    initDoneButton();
  }

  private void initDagger() {
    AndroidInjection.inject(this);
  }

  private void initToolbar() {
    getSupportActionBar().setTitle("Edit Task Categories");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  private void loadTaskCategories() {
    taskAPIService.getTaskCategories().enqueue(new Callback<List<String>>() {
      @Override
      public void onResponse(Call<List<String>> call, Response<List<String>> response) {
        if (response.isSuccessful()) {
          List<String> categoryList = response.body();

          try {
            for (String category : categoryList) {
              addToChipGroup(category, false);
            }
          } catch (NullPointerException e) {
            // In case the project has 0 categories
          }
        }
      }

      @Override
      public void onFailure(Call<List<String>> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
      }
    });
  }

  private void initCreateTaskCategoryButton() {
    buttonCreateTaskCategory.setOnClickListener(view -> {
      String category = editTextTaskCategory.getText().toString();

      if (inputValidator.isInvalidInput(category)) {
        Toast.makeText(this, "Category must not be empty", Toast.LENGTH_SHORT).show();
        return;
      }

      addToChipGroup(category, true);
      editTextTaskCategory.setText("");
    });
  }

  private void initDoneButton() {
    ArrayList<String> selectedCategories = new ArrayList<>();

    buttonDone.setOnClickListener(view -> {
      // Retrieve all the selected categories in the ChipGroup
      for (int i = 0; i < chipGroupSelectedCategories.getChildCount(); i++) {
        Chip chip = (Chip) chipGroupSelectedCategories.getChildAt(i);
        if (chip.isChecked()) {
          selectedCategories.add(chip.getText().toString());
        }
      }

      // Return to the original activity
      Intent returnIntent = new Intent();
      returnIntent.putStringArrayListExtra(TASK_CATEGORIES_LIST_INTENT, selectedCategories);
      setResult(RESULT_OK, returnIntent);
      finish();
    });
  }

  private void addToChipGroup(String category, boolean isChecked) {
    Chip chip =
      (Chip) getLayoutInflater()
        .inflate(R.layout.chip_checkable, chipGroupSelectedCategories, false);
    chip.setText(category);
    chip.setCheckable(true);
    chip.setCheckedIconVisible(true);
    chip.setChecked(isChecked);
    chipGroupSelectedCategories.addView(chip);
  }

  @Override
  public boolean onNavigateUp() {
    finish();
    return true;
  }
}
