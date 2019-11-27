package com.gandh99.codeblocks.homePage.dashboard.fragment;


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
import com.gandh99.codeblocks.authentication.AuthenticationInterceptor;
import com.gandh99.codeblocks.homePage.dashboard.AddProjectDialog;
import com.gandh99.codeblocks.homePage.dashboard.DashboardListAdapter;
import com.gandh99.codeblocks.homePage.dashboard.api.Project;
import com.gandh99.codeblocks.homePage.dashboard.viewModel.DashboardViewModel;
import com.gandh99.codeblocks.projectPage.ProjectActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {
  private static final int DIALOG_REQUEST_ADD_CODE = 1;
  public static final String INTENT_PROJECT = "project";
  private FloatingActionButton fab;
  private RecyclerView recyclerView;
  private DashboardViewModel dashboardViewModel;

  @Inject
  DashboardListAdapter dashboardListAdapter;

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

    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
    fab = view.findViewById(R.id.fab_dashboard);
    recyclerView = view.findViewById(R.id.recyclerView_projects);

    // Setup recyclerView
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(dashboardListAdapter);

    // Add project button
    initFloatingActionButton();

    // Configure ViewModel
    initViewModel();

    // Setup project item click listener
    initProjectItemClickListener();

    return view;
  }

  private void configureDagger() {
    AndroidSupportInjection.inject(this);
  }

  private void initFloatingActionButton() {
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        AddProjectDialog dialog = new AddProjectDialog();
        dialog.setTargetFragment(DashboardFragment.this, DIALOG_REQUEST_ADD_CODE);
        dialog.show(getActivity().getSupportFragmentManager(), "Create ProjectList");
      }
    });
  }

  private void initViewModel() {
    dashboardViewModel = ViewModelProviders.of(this, viewModelFactory).get(DashboardViewModel.class);
    dashboardViewModel.getProjects().observe(this, new Observer<List<Project>>() {
      @Override
      public void onChanged(List<Project> projects) {
        dashboardListAdapter.submitList(projects);
      }
    });
  }

  private void initProjectItemClickListener() {
    dashboardListAdapter.setOnProjectItemClickListener(new DashboardListAdapter.OnProjectItemClickListener() {
      @Override
      public void onProjectItemClick(Project project) {
        Intent intent = new Intent(DashboardFragment.this.getContext(), ProjectActivity.class);
        intent.putExtra(INTENT_PROJECT, project);
        startActivity(intent);
      }
    });
  }

}
