package com.gandh99.codeblocks.di.module;

import com.gandh99.codeblocks.common.RandomColourGenerator;
import com.gandh99.codeblocks.di.module.common.ViewModelModule;
import com.gandh99.codeblocks.projectPage.tasks.TaskAdapter;
import com.gandh99.codeblocks.projectPage.tasks.taskSorter.TaskSorter;
import com.gandh99.codeblocks.projectPage.tasks.api.TaskAPIService;
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
  TaskAdapter provideTaskAdapter() {
    return new TaskAdapter();
  }

  @Provides
  @Singleton
  TaskSorter provideTaskSorter() { return new TaskSorter(); }
}
