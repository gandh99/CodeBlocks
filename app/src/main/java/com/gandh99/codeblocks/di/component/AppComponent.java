package com.gandh99.codeblocks.di.component;

import android.app.Application;

import com.gandh99.codeblocks.App;
import com.gandh99.codeblocks.di.module.InvitationsModule;
import com.gandh99.codeblocks.di.module.MemberModule;
import com.gandh99.codeblocks.di.module.UserProfileModule;
import com.gandh99.codeblocks.di.module.common.ActivityModule;
import com.gandh99.codeblocks.di.module.common.AuthenticationModule;
import com.gandh99.codeblocks.di.module.DashboardModule;
import com.gandh99.codeblocks.di.module.fragmentModule.HomeActivityFragmentModule;
import com.gandh99.codeblocks.di.module.fragmentModule.MainActivityFragmentModule;
import com.gandh99.codeblocks.di.module.fragmentModule.ProjectActivityFragmentModule;
import com.gandh99.codeblocks.di.module.TaskModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(
  modules = {
    AndroidSupportInjectionModule.class,
    AuthenticationModule.class,

    ActivityModule.class,

    DashboardModule.class,
    TaskModule.class,
    MemberModule.class,
    InvitationsModule.class,
    UserProfileModule.class,

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
