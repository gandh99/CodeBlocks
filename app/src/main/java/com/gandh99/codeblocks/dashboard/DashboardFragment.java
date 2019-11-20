package com.gandh99.codeblocks.dashboard;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.dashboard.DashboardListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {
  private FloatingActionButton fab;
  private RecyclerView recyclerView;
  private DashboardListAdapter dashboardListAdapter = new DashboardListAdapter();

  public DashboardFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
    fab = view.findViewById(R.id.fab_dashboard);
    recyclerView = view.findViewById(R.id.recyclerView_projects);

    // Setup recyclerView
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(dashboardListAdapter);

    // Add project
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

      }
    });

    return view;
  }

}
