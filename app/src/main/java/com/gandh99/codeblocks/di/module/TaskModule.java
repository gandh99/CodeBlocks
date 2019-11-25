package com.gandh99.codeblocks.di.module;

import com.gandh99.codeblocks.projectPage.tasks.TaskListAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TaskModule {

  @Provides
  @Singleton
  TaskListAdapter provideTaskListAdapter() {
    return new TaskListAdapter();
  }
}
