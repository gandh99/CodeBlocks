package com.gandh99.codeblocks.projectPage.members.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.projectPage.members.AddMemberDialog;
import com.gandh99.codeblocks.projectPage.members.MemberListAdapter;
import com.gandh99.codeblocks.projectPage.members.activity.ViewProfileActivity;
import com.gandh99.codeblocks.projectPage.members.api.ProjectMember;
import com.gandh99.codeblocks.projectPage.members.viewModel.MemberViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class MembersFragment extends Fragment {
  private static final int DIALOG_REQUEST_ADD_CODE = 1;
  private RecyclerView recyclerView;
  private FloatingActionButton fab;
  private MemberViewModel memberViewModel;

  @Inject
  MemberListAdapter memberListAdapter;

  @Inject
  ViewModelProvider.Factory viewModelFactory;

  public MembersFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Configure Dagger
    configureDagger();

    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_members, container, false);
    recyclerView = view.findViewById(R.id.recyclerView_members);
    fab = view.findViewById(R.id.fab_members);

    // Setup recyclerView
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(memberListAdapter);

    // Give the adapter the resources so that it can help set the profile picture of the user
    memberListAdapter.setResources(getResources());

    // Add member button
    initFloatingActionButton();

    // Setup viewModel
    initViewModel();

    // Allow user profiles to be viewed with a tap
    initProfileViewer();

    return view;
  }

  private void configureDagger() {
    AndroidSupportInjection.inject(this);
  }

  private void initFloatingActionButton() {
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        AddMemberDialog dialog = new AddMemberDialog();
        dialog.setTargetFragment(MembersFragment.this, DIALOG_REQUEST_ADD_CODE);
        dialog.show(getActivity().getSupportFragmentManager(), "Invite Member");
      }
    });
  }

  private void initViewModel() {
    memberViewModel = ViewModelProviders.of(this, viewModelFactory).get(MemberViewModel.class);
    memberViewModel.getProjectMembers().observe(this, new Observer<List<ProjectMember>>() {
      @Override
      public void onChanged(List<ProjectMember> projectMembers) {
        memberListAdapter.submitList(projectMembers);
      }
    });
  }

  private void initProfileViewer() {
    memberListAdapter.setOnMemberSelectedListener(projectMember -> {
      Intent intent = new Intent(getContext(), ViewProfileActivity.class);
      intent.putExtra(ViewProfileActivity.USERNAME_INTENT, projectMember.getUsername());
      intent.putExtra(ViewProfileActivity.PROFILE_PICTURE_INTENT, projectMember.getProfilePicture());
      intent.putExtra(ViewProfileActivity.LOCATION_INTENT, projectMember.getLocation());
      intent.putExtra(ViewProfileActivity.COMPANY_INTENT, projectMember.getCompany());
      intent.putExtra(ViewProfileActivity.JOB_TITLE_INTENT, projectMember.getJobTitle());
      intent.putExtra(ViewProfileActivity.EMAIL_INTENT, projectMember.getEmail());
      intent.putExtra(ViewProfileActivity.WEBSITE_INTENT, projectMember.getWebsite());
      intent.putExtra(ViewProfileActivity.PERSONAL_MESSAGE_INTENT, projectMember.getPersonalMessage());
      startActivity(intent);
    });
  }

}
