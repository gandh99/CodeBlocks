package com.gandh99.codeblocks.di.module.common;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.gandh99.codeblocks.common.FactoryViewModel;
import com.gandh99.codeblocks.homePage.dashboard.viewModel.DashboardViewModel;
import com.gandh99.codeblocks.di.key.ViewModelKey;
import com.gandh99.codeblocks.homePage.invitations.viewModel.InvitationsViewModel;
import com.gandh99.codeblocks.projectPage.completedTasks.viewModel.CompletedTaskViewModel;
import com.gandh99.codeblocks.projectPage.members.viewModel.MemberViewModel;
import com.gandh99.codeblocks.projectPage.tasks.viewModel.TaskViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
  @Binds
  @IntoMap
  @ViewModelKey(DashboardViewModel.class)
  abstract ViewModel bindDashboardViewModel(DashboardViewModel dashboardViewModel);

  @Binds
  @IntoMap
  @ViewModelKey(TaskViewModel.class)
  abstract ViewModel bindTaskViewModel(TaskViewModel taskViewModel);

  @Binds
  @IntoMap
  @ViewModelKey(CompletedTaskViewModel.class)
  abstract ViewModel bindCompletedTaskViewModel(CompletedTaskViewModel completedTaskViewModel);

  @Binds
  @IntoMap
  @ViewModelKey(MemberViewModel.class)
  abstract ViewModel bindMemberViewModel(MemberViewModel memberViewModel);

  @Binds
  @IntoMap
  @ViewModelKey(InvitationsViewModel.class)
  abstract ViewModel bindInvitationsViewModel(InvitationsViewModel invitationsViewModel);

  @Binds
  abstract ViewModelProvider.Factory bindViewModelFactory(FactoryViewModel factory);
}
