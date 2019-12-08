package com.gandh99.codeblocks.di.module;

import com.gandh99.codeblocks.di.module.common.ViewModelModule;
import com.gandh99.codeblocks.homePage.userProfile.api.UserProfileAPIService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = ViewModelModule.class)
public class UserProfileModule {

  @Provides
  @Singleton
  UserProfileAPIService provideUserProfileAPIService(Retrofit retrofit) {
    return retrofit.create(UserProfileAPIService.class);
  }
}
