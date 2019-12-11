package com.gandh99.codeblocks.di.module.fragmentModule;

import com.gandh99.codeblocks.projectPage.completedTasks.CompletedTasksFragment;
import com.gandh99.codeblocks.projectPage.members.AddMemberDialog;
import com.gandh99.codeblocks.projectPage.members.fragment.MembersFragment;
import com.gandh99.codeblocks.projectPage.tasks.SortTaskDialog;
import com.gandh99.codeblocks.projectPage.tasks.fragment.TasksFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ProjectActivityFragmentModule {

  @ContributesAndroidInjector
  abstract TasksFragment contributeTasksFragment();

  @ContributesAndroidInjector
  abstract CompletedTasksFragment contributeIssuesFragment();

  @ContributesAndroidInjector
  abstract MembersFragment contributeMembersFragment();

  @ContributesAndroidInjector
  abstract AddMemberDialog contributeAddMemberDialog();

  @ContributesAndroidInjector
  abstract SortTaskDialog contributeSortFilterTaskDialog();
}
