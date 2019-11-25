package com.gandh99.codeblocks.homePage.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.authentication.AuthenticationInterceptor;
import com.gandh99.codeblocks.authentication.Authenticator;
import com.gandh99.codeblocks.authentication.api.SessionToken;
import com.gandh99.codeblocks.homePage.dashboard.fragment.DashboardFragment;
import com.gandh99.codeblocks.homePage.dashboard.viewModel.DashboardViewModel;
import com.gandh99.codeblocks.homePage.TabsPagerAdapter;
import com.gandh99.codeblocks.homePage.notifications.NotificationsFragment;
import com.google.android.material.tabs.TabLayout;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class HomeActivity extends AppCompatActivity {
  private ViewPager viewPager;
  private TabsPagerAdapter tabsPagerAdapter;
  private TabLayout tabLayout;
  private SessionToken sessionToken;
  private String username;
  private DashboardViewModel dashboardViewModel;

  @Inject
  AuthenticationInterceptor authenticationInterceptor;

  @Inject
  ViewModelProvider.Factory viewModelFactory;

  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    getSupportActionBar().hide();

    viewPager = findViewById(R.id.home_viewPager);
    tabLayout = findViewById(R.id.home_tabLayout);

    configureDagger();
    receiveSessionToken();
    setupAuthenticationInterceptor();
    configureViewModel();

    // Setup adapter, viewpager and tab layout
    createTabLayout();

    // Inflate the individual tabs in HomeActivity
    createTabs();
  }

  private void configureDagger() {
    AndroidInjection.inject(this);
  }

  private void receiveSessionToken() {
    Intent intent = getIntent();
    sessionToken = (SessionToken) intent.getSerializableExtra(Authenticator.INTENT_SESSION_TOKEN);
    username = intent.getStringExtra(Authenticator.INTENT_USERNAME);
  }

  private void setupAuthenticationInterceptor() {
    authenticationInterceptor.setSessionToken(sessionToken.getToken());
    authenticationInterceptor.setUsername(username);
  }

  private void configureViewModel() {
    dashboardViewModel = ViewModelProviders.of(this, viewModelFactory).get(DashboardViewModel.class);
  }

  private void createTabLayout() {
    // Setup adapter
    tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
    tabsPagerAdapter.addFragment(new DashboardFragment());
    tabsPagerAdapter.addFragment(new NotificationsFragment());

    viewPager.setAdapter(tabsPagerAdapter);
    tabLayout.setupWithViewPager(viewPager);
  }

  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
  private void createTabs() {
    String[] tabNames = new String[] {"Dashboard", "Notifications"};
    int[] tabIcons = new int[] {R.drawable.ic_dashboard_white, R.drawable.ic_notifications_white};

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
