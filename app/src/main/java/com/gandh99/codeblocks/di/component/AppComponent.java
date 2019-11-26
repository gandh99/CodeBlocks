package com.gandh99.codeblocks.di.component;

import android.app.Application;

import com.gandh99.codeblocks.App;
import com.gandh99.codeblocks.di.module.ActivityModule;
import com.gandh99.codeblocks.di.module.AuthenticationModule;
import com.gandh99.codeblocks.di.module.DashboardModule;
import com.gandh99.codeblocks.di.module.HomeActivityFragmentModule;
import com.gandh99.codeblocks.di.module.MainActivityFragmentModule;
import com.gandh99.codeblocks.di.module.ProjectActivityFragmentModule;
import com.gandh99.codeblocks.di.module.TaskModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(
  modules = {
    ActivityModule.class,
    AndroidSupportInjectionModule.class,
    AuthenticationModule.class,
    DashboardModule.class,
    TaskModule.class,
    MainActivityFragmentModule.class,
    HomeActivityFragmentModule.class,
    ProjectActivityFragmentModule.class
  }
)

public interface AppComponent extends AndroidInjector<App> {
  @Component.Builder
  interface Builder {
    @BindsInstance
    Builder application(Application application);
    AppComponent build();
  }
}
