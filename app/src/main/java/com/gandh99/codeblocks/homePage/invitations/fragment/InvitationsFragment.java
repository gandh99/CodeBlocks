package com.gandh99.codeblocks.homePage.invitations.fragment;


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
import com.gandh99.codeblocks.homePage.invitations.InvitationsAdapter;
import com.gandh99.codeblocks.homePage.invitations.api.Invitation;
import com.gandh99.codeblocks.homePage.invitations.viewModel.InvitationsViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class InvitationsFragment extends Fragment {
  private RecyclerView recyclerView;
  private InvitationsViewModel invitationsViewModel;

  @Inject
  InvitationsAdapter invitationsAdapter;

  @Inject
  ViewModelProvider.Factory viewModelFactory;

  public InvitationsFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Configure Dagger
    configureDagger();

    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_notification, container, false);
    recyclerView = view.findViewById(R.id.recyclerView_invitations);

    // Setup recyclerView
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(invitationsAdapter);

    // Init viewModel
    initViewModel();

    return view;
  }

  private void configureDagger() {
    AndroidSupportInjection.inject(this);
  }

  private void initViewModel() {
    invitationsViewModel = ViewModelProviders.of(this, viewModelFactory).get(InvitationsViewModel.class);
    invitationsViewModel.getInvitations().observe(this, new Observer<List<Invitation>>() {
      @Override
      public void onChanged(List<Invitation> invitations) {
        invitationsAdapter.submitList(invitations);
      }
    });
  }

}
