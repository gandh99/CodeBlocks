package com.gandh99.codeblocks.homePage.invitations.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gandh99.codeblocks.homePage.invitations.api.Invitation;
import com.gandh99.codeblocks.homePage.invitations.repository.InvitationsRepository;

import java.util.List;

import javax.inject.Inject;

public class InvitationsViewModel extends ViewModel {
  private InvitationsRepository repository;

  @Inject
  public InvitationsViewModel(InvitationsRepository repository) {
    this.repository = repository;
  }

  public MutableLiveData<List<Invitation>> getInvitations() {
    return repository.getInvitations();
  }

  public void refreshInvitations() {
    repository.refreshInvitations();
  }
}
