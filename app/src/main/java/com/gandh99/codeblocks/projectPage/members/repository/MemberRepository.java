package com.gandh99.codeblocks.projectPage.members.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.gandh99.codeblocks.projectPage.members.api.MemberAPIService;
import com.gandh99.codeblocks.projectPage.members.api.ProjectMember;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberRepository {
  private static final String TAG = "MemberRepository";
  private MutableLiveData<List<ProjectMember>> memberList = new MutableLiveData<>();
  private MemberAPIService memberAPIService;

  @Inject
  public MemberRepository(MemberAPIService memberAPIService) {
    this.memberAPIService = memberAPIService;
  }

  public MutableLiveData<List<ProjectMember>> getProjectMembers() {
    refreshMemberList();
    return memberList;
  }

  public void refreshMemberList() {
    memberAPIService.getProjectMembers().enqueue(new Callback<List<ProjectMember>>() {
      @Override
      public void onResponse(Call<List<ProjectMember>> call, Response<List<ProjectMember>> response) {
        if (response.isSuccessful()) {
          List<ProjectMember> projectMembers = response.body();
          MemberRepository.this.memberList.setValue(projectMembers);
        }
      }

      @Override
      public void onFailure(Call<List<ProjectMember>> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
      }
    });
  }

}
