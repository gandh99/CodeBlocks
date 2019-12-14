package com.gandh99.codeblocks.projectPage.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.authentication.AuthenticationInterceptor;
import com.gandh99.codeblocks.common.Refreshable;
import com.gandh99.codeblocks.homePage.TabsPagerAdapter;
import com.gandh99.codeblocks.homePage.activity.HomeActivity;
import com.gandh99.codeblocks.homePage.dashboard.api.DashboardAPIService;
import com.gandh99.codeblocks.homePage.dashboard.api.Project;
import com.gandh99.codeblocks.homePage.dashboard.fragment.DashboardFragment;
import com.gandh99.codeblocks.projectPage.completedTasks.fragment.CompletedTasksFragment;
import com.gandh99.codeblocks.projectPage.members.fragment.MembersFragment;
import com.gandh99.codeblocks.projectPage.tasks.fragment.TasksFragment;
import com.google.android.material.tabs.TabLayout;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
  private ViewPager viewPager;
  private TabLayout tabLayout;
  private TabsPagerAdapter tabsPagerAdapter;
  private Project project;
  private TasksFragment tasksFragment = new TasksFragment();
  private CompletedTasksFragment completedTasksFragment = new CompletedTasksFragment();
  private MembersFragment membersFragment = new MembersFragment();

  @Inject
  AuthenticationInterceptor authenticationInterceptor;

  @Inject
  DashboardAPIService dashboardAPIService;

  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_project);

    viewPager = findViewById(R.id.project_viewPager);
    tabLayout = findViewById(R.id.project_tabLayout);

    configureDagger();
    receiveProjectData();
    initInterceptor();
    createTabLayout();
    createTabs();
  }

  private void configureDagger() {
    AndroidInjection.inject(this);
  }

  private void receiveProjectData() {
    Intent intent = getIntent();
    project = (Project) intent.getSerializableExtra(DashboardFragment.INTENT_PROJECT);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle(project.getTitle());
  }

  private void initInterceptor() {
    // This is needed to identify which tasks/issues to extract from the server
    authenticationInterceptor.setProjectID(String.valueOf(project.getId()));
  }

  private void createTabLayout() {
    // Setup adapter
    tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
    tabsPagerAdapter.addFragment(tasksFragment);
    tabsPagerAdapter.addFragment(completedTasksFragment);
    tabsPagerAdapter.addFragment(membersFragment);

    // Setup viewPager
    viewPager.addOnPageChangeListener(this);

    // Link viewPager, tabLayout and tabsPagerAdapter
    viewPager.setAdapter(tabsPagerAdapter);
    tabLayout.setupWithViewPager(viewPager);
  }

  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
  private void createTabs() {
    String[] tabNames = new String[] {"Tasks", "Completed", "Members"};
    int[] tabIcons = new int[] {R.drawable.ic_task_white, R.drawable.ic_task_completed_white_24dp,
      R.drawable.ic_members_white};

    for (int i = 0; i < tabNames.length; i++) {
      TextView textViewTab = (TextView) LayoutInflater.from(this).inflate(R.layout.home_tab, null);
      textViewTab.setCompoundDrawablesRelativeWithIntrinsicBounds(
        0, tabIcons[i], 0, 0
      );
      textViewTab.setText(tabNames[i]);
      tabLayout.getTabAt(i).setCustomView(textViewTab);
    }
  }

  @Override
  public boolean onSupportNavigateUp() {
    finish();
    return true;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater()
      .inflate(R.menu.menu_project_activity, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_leave_project:
        dashboardAPIService.leaveProject().enqueue(new Callback<ResponseBody>() {
          @Override
          public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (response.isSuccessful()) {
              /* Return back to home page and clear the back stack
              so that the user cannot return back to the project page (IMPORTANT!) */
              Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
              intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
              startActivity(intent);
            }
          }

          @Override
          public void onFailure(Call<ResponseBody> call, Throwable t) {

          }
        });

        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
  }

  @Override
  public void onPageSelected(int position) {
    Fragment fragment = tabsPagerAdapter.getItem(position);

    try {
      ((Refreshable) fragment).refresh();
    } catch (ClassCastException e) {
      // Does not matter as the fragment simply doesn't need to refresh
    }
  }

  @Override
  public void onPageScrollStateChanged(int state) {
  }
}
