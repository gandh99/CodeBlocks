package com.gandh99.codeblocks.dashboard.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.dashboard.AddProjectDialog;
import com.gandh99.codeblocks.dashboard.DashboardListAdapter;
import com.gandh99.codeblocks.dashboard.viewModel.DashboardViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {
  private static final int DIALOG_REQUEST_ADD_CODE = 1;
  private FloatingActionButton fab;
  private RecyclerView recyclerView;
  private DashboardListAdapter dashboardListAdapter = new DashboardListAdapter();
  private DashboardViewModel dashboardViewModel;

  @Inject
  ViewModelProvider.Factory viewModelFactory;

  public DashboardFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // For Dagger injection
    configureDagger();

    // Configure ViewModel
    configureViewModel();

    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
    fab = view.findViewById(R.id.fab_dashboard);
    recyclerView = view.findViewById(R.id.recyclerView_projects);

    // Setup recyclerView
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(dashboardListAdapter);

    // Add project button
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        AddProjectDialog dialog = new AddProjectDialog();
        dialog.setTargetFragment(DashboardFragment.this, DIALOG_REQUEST_ADD_CODE);
        dialog.show(getActivity().getSupportFragmentManager(), "Create Project");
      }
    });

    // Get list of projects
    dashboardViewModel.getProjects();

    return view;
  }

  private void configureDagger() {
    AndroidSupportInjection.inject(this);
  }

  private void configureViewModel() {
    dashboardViewModel = ViewModelProviders.of(this, viewModelFactory).get(DashboardViewModel.class);
  }

}
