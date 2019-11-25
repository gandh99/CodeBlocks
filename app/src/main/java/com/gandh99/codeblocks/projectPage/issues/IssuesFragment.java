package com.gandh99.codeblocks.projectPage.issues;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gandh99.codeblocks.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IssuesFragment extends Fragment {


  public IssuesFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_issues, container, false);
  }

}
