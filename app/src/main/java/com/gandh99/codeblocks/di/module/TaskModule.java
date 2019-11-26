package com.gandh99.codeblocks.di.module;

import com.gandh99.codeblocks.projectPage.tasks.api.TaskAPIService;
import com.gandh99.codeblocks.projectPage.tasks.TaskListAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class TaskModule {

  @Provides
  @Singleton
  TaskAPIService provideTaskAPIService(Retrofit retrofit) {
    return retrofit.create(TaskAPIService.class);
  }

  @Provides
  @Singleton
  TaskListAdapter provideTaskListAdapter() {
    return new TaskListAdapter();
  }
}
