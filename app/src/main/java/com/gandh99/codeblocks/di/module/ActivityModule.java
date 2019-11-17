package com.gandh99.codeblocks.di.module;

import com.gandh99.codeblocks.activity.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
  @ContributesAndroidInjector(modules = {FragmentModule.class})
  abstract MainActivity contributeMainActivity();
}
