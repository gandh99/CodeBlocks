package com.gandh99.codeblocks.projectPage.tasks.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.authentication.InputValidator;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class EditTaskCategoriesActivity extends AppCompatActivity {
  private EditText editTextTaskCategory;
  private Button buttonCreateTaskCategory;
  private ChipGroup chipGroupTaskCategories;

  @Inject
  InputValidator inputValidator;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_task_categories);

    editTextTaskCategory = findViewById(R.id.editText_task_category);
    buttonCreateTaskCategory = findViewById(R.id.button_create_task_category);
    chipGroupTaskCategories = findViewById(R.id.chipgroup_selected_categories);

    initDagger();
    initToolbar();
    initCreateTaskCategoryButton();
  }

  private void initDagger() {
    AndroidInjection.inject(this);
  }

  private void initToolbar() {
    getSupportActionBar().setTitle("Edit Task Categories");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  private void initCreateTaskCategoryButton() {
    buttonCreateTaskCategory.setOnClickListener(view -> {
      String category = editTextTaskCategory.getText().toString();

      if (inputValidator.isInvalidInput(category)) {
        Toast.makeText(this, "Category must not be empty", Toast.LENGTH_SHORT).show();
        return;
      }

      addToChipGroup(category);
      editTextTaskCategory.setText("");
    });
  }

  private void addToChipGroup(String category) {
    Chip chip =
      (Chip) getLayoutInflater()
        .inflate(R.layout.chip_checkable, chipGroupTaskCategories, false);
    chip.setText(category);
    chip.setCheckable(true);
    chip.setCheckedIconVisible(true);
    chipGroupTaskCategories.addView(chip);
  }

  @Override
  public boolean onNavigateUp() {
    finish();
    return true;
  }
}
