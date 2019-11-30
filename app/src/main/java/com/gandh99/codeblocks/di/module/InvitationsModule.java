package com.gandh99.codeblocks.di.module;

import com.gandh99.codeblocks.di.module.common.ViewModelModule;
import com.gandh99.codeblocks.homePage.invitations.api.InvitationAPIService;
import com.gandh99.codeblocks.homePage.invitations.InvitationsAdapter;
import com.gandh99.codeblocks.homePage.invitations.repository.InvitationsRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = ViewModelModule.class)
public class InvitationsModule {

  @Provides
  @Singleton
  InvitationAPIService provideInvitationAPIService(Retrofit retrofit) {
    return retrofit.create(InvitationAPIService.class);
  }

  @Provides
  @Singleton
  InvitationsRepository provideInvitationsRepository(InvitationAPIService invitationAPIService) {
    return new InvitationsRepository(invitationAPIService);
  }

  @Provides
  @Singleton
  InvitationsAdapter provideInvitationsAdapter() {
    return new InvitationsAdapter();
  }
}
