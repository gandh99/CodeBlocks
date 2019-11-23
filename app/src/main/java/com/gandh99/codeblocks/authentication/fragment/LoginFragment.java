package com.gandh99.codeblocks.authentication.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.authentication.Authenticator;
import com.gandh99.codeblocks.homePage.activity.HomeActivity;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
  private EditText editTextUsername, editTextPassword;
  private Button buttonLogin;

  @Inject
  Authenticator authenticator;

  public LoginFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Configure Dagger
    configureDagger();

    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_login, container, false);
    editTextUsername = view.findViewById(R.id.editText_login_username);
    editTextPassword = view.findViewById(R.id.editText_login_password);
    editTextPassword.setTransformationMethod(new PasswordTransformationMethod());
    buttonLogin = view.findViewById(R.id.button_login);

    initLoginButton();

    return view;
  }

  private void configureDagger() {
    AndroidSupportInjection.inject(this);
  }

  private void initLoginButton() {
    buttonLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        final String username = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();

        authenticator.loginUser(LoginFragment.this, username, password);
      }
    });
  }

}
