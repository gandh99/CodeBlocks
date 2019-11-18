package com.gandh99.codeblocks.di.module;

import com.gandh99.codeblocks.DashboardFragment;
import com.gandh99.codeblocks.fragment.LoginFragment;
import com.gandh99.codeblocks.fragment.RegisterFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HomeActivityFragmentModule {
  @ContributesAndroidInjector
  abstract DashboardFragment contributeDashboardFragment();
}
