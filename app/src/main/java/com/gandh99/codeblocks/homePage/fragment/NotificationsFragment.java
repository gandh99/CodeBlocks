package com.gandh99.codeblocks.homePage.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gandh99.codeblocks.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {


  public NotificationsFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_notification, container, false);
  }

}
