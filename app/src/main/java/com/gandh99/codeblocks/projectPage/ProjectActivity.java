package com.gandh99.codeblocks.projectPage;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.homePage.dashboard.fragment.DashboardFragment;
import com.gandh99.codeblocks.homePage.TabsPagerAdapter;
import com.gandh99.codeblocks.projectPage.issues.IssuesFragment;
import com.gandh99.codeblocks.projectPage.members.MembersFragment;
import com.gandh99.codeblocks.projectPage.tasks.TasksFragment;
import com.google.android.material.tabs.TabLayout;

import dagger.android.AndroidInjection;

public class ProjectActivity extends AppCompatActivity {
  private ViewPager viewPager;
  private TabLayout tabLayout;
  private TabsPagerAdapter tabsPagerAdapter;

  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_project);

    viewPager = findViewById(R.id.project_viewPager);
    tabLayout = findViewById(R.id.project_tabLayout);

    configureDagger();
    receiveProjectData();
    createTabLayout();
    createTabs();
  }

  private void configureDagger() {
    AndroidInjection.inject(this);
  }

  private void receiveProjectData() {
    Intent intent = getIntent();
    String projectTitle = intent.getStringExtra(DashboardFragment.INTENT_PROJECT_TITLE);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle(projectTitle);
  }

  private void createTabLayout() {
    // Setup adapter
    tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
    tabsPagerAdapter.addFragment(new TasksFragment());
    tabsPagerAdapter.addFragment(new IssuesFragment());
    tabsPagerAdapter.addFragment(new MembersFragment());

    viewPager.setAdapter(tabsPagerAdapter);
    tabLayout.setupWithViewPager(viewPager);
  }

  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
  private void createTabs() {
    String[] tabNames = new String[] {"Tasks", "Issues", "Members"};
    int[] tabIcons = new int[] {R.drawable.ic_task_white, R.drawable.ic_bug_white, R.drawable.ic_members_white};

    for (int i = 0; i < tabNames.length; i++) {
      TextView textViewTab = (TextView) LayoutInflater.from(this).inflate(R.layout.home_tab, null);
      textViewTab.setCompoundDrawablesRelativeWithIntrinsicBounds(
        0, tabIcons[i], 0, 0
      );
      textViewTab.setText(tabNames[i]);
      tabLayout.getTabAt(i).setCustomView(textViewTab);
    }
  }
}
