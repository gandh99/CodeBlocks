package com.gandh99.codeblocks.di.module.common;

import com.gandh99.codeblocks.di.module.fragmentModule.HomeActivityFragmentModule;
import com.gandh99.codeblocks.di.module.fragmentModule.MainActivityFragmentModule;
import com.gandh99.codeblocks.di.module.fragmentModule.ProjectActivityFragmentModule;
import com.gandh99.codeblocks.homePage.activity.HomeActivity;
import com.gandh99.codeblocks.authentication.activity.MainActivity;
import com.gandh99.codeblocks.homePage.userProfile.activity.EditUserProfileActivity;
import com.gandh99.codeblocks.projectPage.activity.ProjectActivity;
import com.gandh99.codeblocks.projectPage.tasks.activity.EditTaskCategoriesActivity;
import com.gandh99.codeblocks.projectPage.tasks.activity.NewTaskActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
  @ContributesAndroidInjector(modules = {MainActivityFragmentModule.class})
  abstract MainActivity contributeMainActivity();

  @ContributesAndroidInjector(modules = {HomeActivityFragmentModule.class})
  abstract HomeActivity contributeHomeActivity();

  @ContributesAndroidInjector(modules = {ProjectActivityFragmentModule.class})
  abstract ProjectActivity contributeProjectActivity();

  @ContributesAndroidInjector
  abstract NewTaskActivity contributeNewTaskActivity();

  @ContributesAndroidInjector
  abstract EditTaskCategoriesActivity contributeEditTaskCategoriesActivity();

  @ContributesAndroidInjector
  abstract EditUserProfileActivity contributeEditProfileActivity();
}
