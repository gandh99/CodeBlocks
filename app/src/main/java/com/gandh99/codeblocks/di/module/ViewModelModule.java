package com.gandh99.codeblocks.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.gandh99.codeblocks.FactoryViewModel;
import com.gandh99.codeblocks.dashboard.viewModel.DashboardViewModel;
import com.gandh99.codeblocks.di.key.ViewModelKey;

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
  abstract ViewModelProvider.Factory bindViewModelFactory(FactoryViewModel factory);
}
