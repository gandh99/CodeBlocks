package com.gandh99.codeblocks.authentication;

import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.gandh99.codeblocks.authentication.api.AuthAPIService;
import com.gandh99.codeblocks.authentication.api.User;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Authenticator {
  private static final String TAG = "Authenticator";
  private AuthAPIService authAPIService;
  private InputValidator inputValidator;

  @Inject
  public Authenticator(AuthAPIService authAPIService, InputValidator inputValidator) {
    this.authAPIService = authAPIService;
    this.inputValidator = inputValidator;
  }

  public void registerUser(final Fragment fragment, String username, String password) {
    if (!inputValidator.isValidUsername(username)) {
      Toast.makeText(fragment.getContext(), "Invalid username", Toast.LENGTH_SHORT).show();
      return;
    }

    if (!inputValidator.isValidPassword(password)) {
      Toast.makeText(fragment.getContext(), "Invalid password", Toast.LENGTH_SHORT).show();
      return;
    }

    authAPIService.registerUser(username, password).enqueue(new Callback<User>() {
      @Override
      public void onResponse(Call<User> call, Response<User> response) {
        if (response.isSuccessful()) {
          Toast.makeText(fragment.getContext(), "Registration successful",
            Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(fragment.getContext(), "Response code: " + response.code(),
          Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onFailure(Call<User> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
        Toast.makeText(fragment.getContext(), "Something went wrong. Please try again later",
          Toast.LENGTH_SHORT).show();
      }
    });
  }
}
