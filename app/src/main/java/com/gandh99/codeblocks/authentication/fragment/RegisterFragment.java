package com.gandh99.codeblocks.authentication.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gandh99.codeblocks.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
  private EditText editTextUsername, editTextPassword;

  public RegisterFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_register, container, false);
    editTextUsername = view.findViewById(R.id.editText_register_username);
    editTextPassword = view.findViewById(R.id.editText_register_password);
    editTextPassword.setTransformationMethod(new PasswordTransformationMethod());

    return view;
  }

}
