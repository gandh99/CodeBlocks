package com.gandh99.codeblocks.projectPage.completedTasks;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gandh99.codeblocks.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedTasksFragment extends Fragment {


  public CompletedTasksFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_completed_tasks, container, false);
  }

}
