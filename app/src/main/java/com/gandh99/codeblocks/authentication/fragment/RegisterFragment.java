package com.gandh99.codeblocks.authentication.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.authentication.Authenticator;
import com.gandh99.codeblocks.common.Encryptor;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
  private EditText editTextUsername, editTextPassword;
  private Button buttonRegister;

  @Inject
  Authenticator authenticator;

  public RegisterFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Configure Dagger
    configureDagger();

    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_register, container, false);
    editTextUsername = view.findViewById(R.id.editText_register_username);
    editTextPassword = view.findViewById(R.id.editText_register_password);
    editTextPassword.setTransformationMethod(new PasswordTransformationMethod());
    buttonRegister = view.findViewById(R.id.button_register);

    setupRegisterButton();

    return view;
  }

  private void configureDagger() {
    AndroidSupportInjection.inject(this);
  }

  private void setupRegisterButton() {
    buttonRegister.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        final String username = editTextUsername.getText().toString();
        String plaintextPassword = editTextPassword.getText().toString();

        authenticator.registerUser(RegisterFragment.this, username, plaintextPassword);
      }
    });
  }

}
