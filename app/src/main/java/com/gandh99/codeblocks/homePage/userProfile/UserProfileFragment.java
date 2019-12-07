package com.gandh99.codeblocks.homePage.userProfile;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gandh99.codeblocks.R;

import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment {


  public UserProfileFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Setup Dagger 2
    initDagger();

    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_user_profile, container, false);


    return view;
  }

  private void initDagger() {
    AndroidSupportInjection.inject(this);
  }

}
