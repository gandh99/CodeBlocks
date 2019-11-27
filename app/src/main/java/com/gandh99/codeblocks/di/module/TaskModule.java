package com.gandh99.codeblocks.di.module;

import com.gandh99.codeblocks.di.module.common.ViewModelModule;
import com.gandh99.codeblocks.projectPage.tasks.api.TaskAPIService;
import com.gandh99.codeblocks.projectPage.tasks.TaskListAdapter;
import com.gandh99.codeblocks.projectPage.tasks.repository.TaskRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = ViewModelModule.class)
public class TaskModule {

  @Provides
  @Singleton
  TaskAPIService provideTaskAPIService(Retrofit retrofit) {
    return retrofit.create(TaskAPIService.class);
  }

  @Provides
  @Singleton
  TaskRepository provideTaskRepository(TaskAPIService taskAPIService) {
    return new TaskRepository(taskAPIService);
  }

  @Provides
  @Singleton
  TaskListAdapter provideTaskListAdapter() {
    return new TaskListAdapter();
  }
}
