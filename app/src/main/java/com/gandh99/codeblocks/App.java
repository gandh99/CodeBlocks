package com.gandh99.codeblocks;


import android.content.res.Resources;

import com.gandh99.codeblocks.di.component.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class App extends DaggerApplication {
  private static Resources resources;

  @Override
  public void onCreate() {
    super.onCreate();
    resources = getResources();
  }

  @Override
  protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
    return DaggerAppComponent.builder().application(this).build();
  }

  public static Resources getAppResources() {
    return resources;
  }
}
