package com.gandh99.codeblocks.di.module;

import com.gandh99.codeblocks.di.module.common.ViewModelModule;
import com.gandh99.codeblocks.projectPage.completedTasks.CompletedTaskAdapter;
import com.gandh99.codeblocks.projectPage.completedTasks.api.CompletedTaskAPIService;
import com.gandh99.codeblocks.projectPage.completedTasks.repository.CompletedTaskRepository;
import com.gandh99.codeblocks.projectPage.tasks.TaskAdapter;
import com.gandh99.codeblocks.projectPage.tasks.api.TaskAPIService;
import com.gandh99.codeblocks.projectPage.tasks.repository.TaskRepository;
import com.gandh99.codeblocks.projectPage.tasks.taskSorter.TaskSorter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = ViewModelModule.class)
public class CompletedTaskModule {

  @Provides
  @Singleton
  CompletedTaskAPIService provideCompletedTaskAPIService(Retrofit retrofit) {
    return retrofit.create(CompletedTaskAPIService.class);
  }

  @Provides
  @Singleton
  CompletedTaskRepository provideCompletedTaskRepository(TaskAPIService taskAPIService) {
    return new CompletedTaskRepository(taskAPIService);
  }

  @Provides
  @Singleton
  CompletedTaskAdapter provideCompletedTaskListAdapter() {
    return new CompletedTaskAdapter();
  }

  @Provides
  @Singleton
  TaskSorter provideTaskSorter() { return new TaskSorter(); }
}
