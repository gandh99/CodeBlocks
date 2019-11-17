package com.gandh99.codeblocks.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.fragment.LoginFragment;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    startLoginFragment();
  }

  private void startLoginFragment() {
    getSupportFragmentManager()
      .beginTransaction()
      .replace(R.id.main_fragment_container, new LoginFragment())
      .commit();
  }
}
