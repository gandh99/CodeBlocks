package com.gandh99.codeblocks.di.module;

import com.gandh99.codeblocks.projectPage.issues.IssuesFragment;
import com.gandh99.codeblocks.projectPage.members.MembersFragment;
import com.gandh99.codeblocks.projectPage.tasks.TasksFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ProjectActivityFragmentModule {

  @ContributesAndroidInjector
  abstract TasksFragment contributeTasksFragment();

  @ContributesAndroidInjector
  abstract IssuesFragment contributeIssuesFragment();

  @ContributesAndroidInjector
  abstract MembersFragment contributeMembersFragment();
}
