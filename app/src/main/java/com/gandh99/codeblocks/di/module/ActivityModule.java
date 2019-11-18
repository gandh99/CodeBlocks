package com.gandh99.codeblocks.di.module;

import com.gandh99.codeblocks.homePage.activity.HomeActivity;
import com.gandh99.codeblocks.authentication.activity.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
  @ContributesAndroidInjector(modules = {MainActivityFragmentModule.class})
  abstract MainActivity contributeMainActivity();

  @ContributesAndroidInjector(modules = {HomeActivityFragmentModule.class})
  abstract HomeActivity contributeHomeActivity();
}
