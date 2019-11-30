package com.gandh99.codeblocks.homePage.invitations;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvitationsRepository {
  private static final String TAG = "InvitationsRepository";
  private MutableLiveData<List<Invitation>> invitationList = new MutableLiveData<>();
  private InvitationAPIService invitationAPIService;

  @Inject
  public InvitationsRepository(InvitationAPIService invitationAPIService) {
    this.invitationAPIService = invitationAPIService;
  }

  public MutableLiveData<List<Invitation>> getInvitations() {
    refreshInvitations();
    return invitationList;
  }

  public void refreshInvitations() {
    invitationAPIService.getInvitations().enqueue(new Callback<List<Invitation>>() {
      @Override
      public void onResponse(Call<List<Invitation>> call, Response<List<Invitation>> response) {

      }

      @Override
      public void onFailure(Call<List<Invitation>> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
      }
    });
  }
}
