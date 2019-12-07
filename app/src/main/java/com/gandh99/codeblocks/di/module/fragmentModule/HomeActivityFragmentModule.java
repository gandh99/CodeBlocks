package com.gandh99.codeblocks.di.module.fragmentModule;

import com.gandh99.codeblocks.homePage.dashboard.AddProjectDialog;
import com.gandh99.codeblocks.homePage.dashboard.fragment.DashboardFragment;
import com.gandh99.codeblocks.homePage.invitations.fragment.InvitationsFragment;
import com.gandh99.codeblocks.homePage.userProfile.UserProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HomeActivityFragmentModule {
  @ContributesAndroidInjector
  abstract DashboardFragment contributeDashboardFragment();

  @ContributesAndroidInjector
  abstract InvitationsFragment contributeInvitationsFragment();

  @ContributesAndroidInjector
  abstract UserProfileFragment contributeUserProfileFragment();

  @ContributesAndroidInjector
  abstract AddProjectDialog contributeAddProjectDialog();
}
