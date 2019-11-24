package com.gandh99.codeblocks.projectPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.dashboard.fragment.DashboardFragment;

public class ProjectActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_project);

    Intent intent = getIntent();
    String projectTitle = intent.getStringExtra(DashboardFragment.INTENT_PROJECT_TITLE);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle(projectTitle);

  }
}
