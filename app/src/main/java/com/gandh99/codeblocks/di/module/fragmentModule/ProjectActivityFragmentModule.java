package com.gandh99.codeblocks.di.module.fragmentModule;

import com.gandh99.codeblocks.projectPage.issues.IssuesFragment;
import com.gandh99.codeblocks.projectPage.members.AddMemberDialog;
import com.gandh99.codeblocks.projectPage.members.fragment.MembersFragment;
import com.gandh99.codeblocks.projectPage.tasks.AddTaskDialog;
import com.gandh99.codeblocks.projectPage.tasks.fragment.TasksFragment;

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

  @ContributesAndroidInjector
  abstract AddTaskDialog contributeAddTaskDialog();

  @ContributesAndroidInjector
  abstract AddMemberDialog contributeAddMemberDialog();
}
