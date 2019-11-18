package com.gandh99.codeblocks.di.component;

import android.app.Application;

import com.gandh99.codeblocks.App;
import com.gandh99.codeblocks.di.module.ActivityModule;
import com.gandh99.codeblocks.di.module.AppModule;
import com.gandh99.codeblocks.di.module.HomeActivityFragmentModule;
import com.gandh99.codeblocks.di.module.MainActivityFragmentModule;

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
    AppModule.class,
    MainActivityFragmentModule.class,
    HomeActivityFragmentModule.class
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
