package com.gandh99.codeblocks.di.module;

import com.gandh99.codeblocks.di.module.common.ViewModelModule;
import com.gandh99.codeblocks.projectPage.members.MemberListAdapter;
import com.gandh99.codeblocks.projectPage.members.api.MemberAPIService;
import com.gandh99.codeblocks.projectPage.members.repository.MemberRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = ViewModelModule.class)
public class MemberModule {

  @Provides
  @Singleton
  MemberAPIService provideMemberAPIService(Retrofit retrofit) {
    return retrofit.create(MemberAPIService.class);
  }

  @Provides
  @Singleton
  MemberRepository provideMemberRepository(MemberAPIService memberAPIService) {
    return new MemberRepository(memberAPIService);
  }

  @Provides
  @Singleton
  MemberListAdapter provideMemberListAdapter() {
    return new MemberListAdapter();
  }

}
