package com.gandh99.codeblocks.projectPage.members.fragment;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.authentication.AuthenticationInterceptor;
import com.gandh99.codeblocks.projectPage.members.AddMemberDialog;
import com.gandh99.codeblocks.projectPage.members.MemberListAdapter;
import com.gandh99.codeblocks.projectPage.members.activity.ViewProfileActivity;
import com.gandh99.codeblocks.projectPage.members.api.ProjectMember;
import com.gandh99.codeblocks.projectPage.members.viewModel.MemberViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class MembersFragment extends Fragment {
  private static final int DIALOG_REQUEST_ADD_CODE = 1;
  private RecyclerView recyclerView;
  private FloatingActionButton fab;
  private MemberViewModel memberViewModel;
  private ProjectMember currentUser;

  @Inject
  MemberListAdapter memberListAdapter;

  @Inject
  AuthenticationInterceptor authenticationInterceptor;

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

    // Setup viewModel
    initViewModel();

    // Add member button
    initFloatingActionButton();

    // Allow user profiles to be viewed with a tap
    initProfileViewer();

    return view;
  }

  private void configureDagger() {
    AndroidSupportInjection.inject(this);
  }

  private void initViewModel() {
    memberViewModel = ViewModelProviders.of(this, viewModelFactory).get(MemberViewModel.class);
    memberViewModel.getProjectMembers().observe(this, new Observer<List<ProjectMember>>() {
      @Override
      public void onChanged(List<ProjectMember> projectMembers) {
        try {
          currentUser = getCurrentUser(projectMembers);
        } catch (Resources.NotFoundException e) {
          /* This may sometimes occur when a user is just added to the project group and
          * tries to view the list of members for the first time. */
        }

        memberListAdapter.submitList(projectMembers);
      }
    });
  }

  /* The currentUser will be used to determine if they can have access to the fab */
  private ProjectMember getCurrentUser(List<ProjectMember> projectMembers) {
    String currentUsername = authenticationInterceptor.getUsername();
    for (ProjectMember member : projectMembers) {
      if (member.getUsername().equals(currentUsername)) {
        return member;
      }
    }

    throw new Resources.NotFoundException("Unable to locate current user");
  }

  private void initFloatingActionButton() {
    fab.setOnClickListener(view -> {
      if (!currentUser.isAdmin()) {
        Toast.makeText(getContext(), "Only admins may invite users to the project",
          Toast.LENGTH_SHORT).show();
        return;
      }

      AddMemberDialog dialog = new AddMemberDialog();
      dialog.setTargetFragment(MembersFragment.this, DIALOG_REQUEST_ADD_CODE);
      dialog.show(getActivity().getSupportFragmentManager(), "Invite Member");
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
