package com.gandh99.codeblocks.homePage.invitations.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.gandh99.codeblocks.homePage.invitations.InvitationResponse;
import com.gandh99.codeblocks.homePage.invitations.api.Invitation;
import com.gandh99.codeblocks.homePage.invitations.api.InvitationAPIService;

import java.util.List;

import javax.inject.Inject;

import okhttp3.ResponseBody;
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
        if (response.isSuccessful()) {
          List<Invitation> invitationList = response.body();
          InvitationsRepository.this.invitationList.postValue(invitationList);
        }
      }

      @Override
      public void onFailure(Call<List<Invitation>> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
      }
    });
  }

  public void sendInvitationResponse(Invitation invitation, InvitationResponse response) {
    invitationAPIService.sendInvitationResponse(invitation.getId(), response.getResponse())
      .enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

      }

      @Override
      public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
      }
    });
  }
}
