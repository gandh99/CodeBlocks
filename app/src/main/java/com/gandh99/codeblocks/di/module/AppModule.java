package com.gandh99.codeblocks.di.module;

import com.gandh99.codeblocks.api.LoginAPIService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {
  private static final String BASE_URL = "http://192.168.86.125:8000/";
  private static final int CONNECTION_TIMEOUT_SECONDS = 7;

  // --- NETWORK INJECTION ---

//  @Provides
//  @Singleton
//  AuthenticationInterceptor provideAuthenticationInterceptor() {
//    return new AuthenticationInterceptor();
//  }

//  @Provides
//  OkHttpClient provideOkHttpClient(AuthenticationInterceptor interceptor) {
//    return new OkHttpClient
//      .Builder()
//      .addInterceptor(interceptor)
//      .connectTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
//      .build();
//  }

  @Provides
  Gson provideGson() {
    return new GsonBuilder().serializeNulls().create();
  }

  @Provides
  Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
    return
      new Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build();
  }

  @Provides
  @Singleton
  LoginAPIService provideUserAPIService(Retrofit retrofit){
    return retrofit.create(LoginAPIService.class);
  }
}
