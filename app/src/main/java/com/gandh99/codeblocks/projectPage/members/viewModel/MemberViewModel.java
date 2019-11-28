package com.gandh99.codeblocks.projectPage.members.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gandh99.codeblocks.projectPage.members.api.ProjectMember;
import com.gandh99.codeblocks.projectPage.members.repository.MemberRepository;

import java.util.List;

import javax.inject.Inject;

public class MemberViewModel extends ViewModel {
  private MemberRepository repository;

  @Inject
  public MemberViewModel(MemberRepository repository) {
    this.repository = repository;
  }

  public MutableLiveData<List<ProjectMember>> getProjectMembers() {
    return repository.getProjectMembers();
  }

  public void refreshMemberList() {
    repository.refreshMemberList();
  }
}
