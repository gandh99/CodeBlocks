package com.gandh99.codeblocks.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.gandh99.codeblocks.FactoryViewModel;
import com.gandh99.codeblocks.homePage.dashboard.viewModel.DashboardViewModel;
import com.gandh99.codeblocks.di.key.ViewModelKey;
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
  abstract ViewModelProvider.Factory bindViewModelFactory(FactoryViewModel factory);
}
