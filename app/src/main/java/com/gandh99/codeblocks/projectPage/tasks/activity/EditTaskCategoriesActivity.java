package com.gandh99.codeblocks.projectPage.tasks.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.gandh99.codeblocks.R;

public class EditTaskCategoriesActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_task_categories);

    initToolbar();
  }

  private void initToolbar() {
    getSupportActionBar().setTitle("Edit Task Categories");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  @Override
  public boolean onNavigateUp() {
    finish();
    return true;
  }
}
