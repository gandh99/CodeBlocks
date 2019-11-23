package com.gandh99.codeblocks.di.module;

import com.gandh99.codeblocks.dashboard.DashboardListAdapter;
import com.gandh99.codeblocks.dashboard.api.DashboardAPIService;
import com.gandh99.codeblocks.dashboard.repository.DashboardRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = ViewModelModule.class)
public class DashboardModule {

  // --- NETWORK INJECTION ---

  @Provides
  @Singleton
  DashboardAPIService provideDashboardAPIService(Retrofit retrofit) {
    return retrofit.create(DashboardAPIService.class);
  }

  @Provides
  @Singleton
  DashboardRepository provideDashboardRepository(DashboardAPIService dashboardAPIService) {
    return new DashboardRepository(dashboardAPIService);
  }

  @Provides
  @Singleton
  DashboardListAdapter provideDashboardListAdapter() {
    return new DashboardListAdapter();
  }
}
