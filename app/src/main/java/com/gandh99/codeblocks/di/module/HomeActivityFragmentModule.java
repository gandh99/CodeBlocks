package com.gandh99.codeblocks.di.module;

import com.gandh99.codeblocks.dashboard.AddProjectDialog;
import com.gandh99.codeblocks.dashboard.fragment.DashboardFragment;
import com.gandh99.codeblocks.homePage.fragment.NotificationsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HomeActivityFragmentModule {
  @ContributesAndroidInjector
  abstract DashboardFragment contributeDashboardFragment();

  @ContributesAndroidInjector
  abstract NotificationsFragment contributeNotificationsFragment();

  @ContributesAndroidInjector
  abstract AddProjectDialog contributeAddProjectDialog();
}
