package com.gandh99.codeblocks.projectPage.tasks;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gandh99.codeblocks.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends Fragment {
  private RecyclerView recyclerView;
  private FloatingActionButton fab;

  @Inject
  TaskListAdapter taskListAdapter;

  public TasksFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Configure Dagger
    configureDagger();

    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_tasks, container, false);
    recyclerView = view.findViewById(R.id.recyclerView_tasks);
    fab = view.findViewById(R.id.fab_tasks);

    // Setup recyclerView
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(taskListAdapter);

    // Add task button
    initFloatingActionButton();

    return view;
  }

  private void configureDagger() {
    AndroidSupportInjection.inject(this);
  }

  private void initFloatingActionButton() {
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

      }
    });
  }

}
