package com.gandh99.codeblocks.di.module;

import com.gandh99.codeblocks.dashboard.api.DashboardAPIService;

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
}
