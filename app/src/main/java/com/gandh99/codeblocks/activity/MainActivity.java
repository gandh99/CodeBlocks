package com.gandh99.codeblocks.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.fragment.LoginFragment;
import com.gandh99.codeblocks.fragment.RegisterFragment;

public class MainActivity extends AppCompatActivity {
  private Button buttonLoginRegister;
  private State currentState = State.LOGIN;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    buttonLoginRegister = findViewById(R.id.button_login_register);

    initLoginRegisterButton();
    startLoginFragment();
  }

  private void initLoginRegisterButton() {
    buttonLoginRegister.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (currentState == State.LOGIN) {
          startRegisterFragment();
          buttonLoginRegister.setText(State.REGISTER.message);
          currentState = State.REGISTER;
        } else {
          startLoginFragment();
          buttonLoginRegister.setText(State.LOGIN.message);
          currentState = State.LOGIN;
        }
      }
    });
  }

  private void startLoginFragment() {
    getSupportFragmentManager()
      .beginTransaction()
      .replace(R.id.main_fragment_container, new LoginFragment())
      .commit();
  }

  private void startRegisterFragment() {
    getSupportFragmentManager()
      .beginTransaction()
      .replace(R.id.main_fragment_container, new RegisterFragment())
      .commit();
  }

  private enum State {
    LOGIN("New around here? Tap to register"),
    REGISTER("Already have an account? Tap to login");

    private String message;

    State(String message) {
      this.message = message;
    }
  }
}
