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
import com.gandh99.codeblocks.homePage.activity.HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
  private EditText editTextUsername, editTextPassword;
  private Button buttonLogin;

  public LoginFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_login, container, false);
    editTextUsername = view.findViewById(R.id.editText_login_username);
    editTextPassword = view.findViewById(R.id.editText_login_password);
    editTextPassword.setTransformationMethod(new PasswordTransformationMethod());
    buttonLogin = view.findViewById(R.id.button_login);

    initLoginButton();

    return view;
  }

  private void initLoginButton() {
    buttonLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //TODO
        Intent intent = new Intent(getContext(), HomeActivity.class);
        startActivity(intent);
      }
    });
  }

}
