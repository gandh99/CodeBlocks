package com.gandh99.codeblocks.authentication;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.gandh99.codeblocks.authentication.api.AuthAPIService;
import com.gandh99.codeblocks.authentication.api.SessionToken;
import com.gandh99.codeblocks.authentication.api.User;
import com.gandh99.codeblocks.common.Encryptor;
import com.gandh99.codeblocks.homePage.activity.HomeActivity;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Authenticator {
  private static final String TAG = "Authenticator";
  public static final String INTENT_SESSION_TOKEN = "sessionToken";
  public static final String INTENT_USERNAME = "username";
  private AuthAPIService authAPIService;
  private InputValidator inputValidator;

  @Inject
  public Authenticator(AuthAPIService authAPIService, InputValidator inputValidator) {
    this.authAPIService = authAPIService;
    this.inputValidator = inputValidator;
  }

  public void registerUser(final Fragment fragment, String username, String password) {
    if (inputValidator.isInvalidRegistrationUsername(username)) {
      Toast.makeText(fragment.getContext(), "Invalid username", Toast.LENGTH_SHORT).show();
      return;
    }

    if (inputValidator.isInvalidRegistrationPassword(password)) {
      Toast.makeText(fragment.getContext(), "Invalid password", Toast.LENGTH_SHORT).show();
      return;
    }

    String encryptedPassword = Encryptor.hash512(password);
    authAPIService.registerUser(username, encryptedPassword).enqueue(new Callback<User>() {
      @Override
      public void onResponse(Call<User> call, Response<User> response) {
        if (response.isSuccessful()) {
          Toast.makeText(fragment.getContext(), "Registration successful",
            Toast.LENGTH_SHORT).show();
          return;
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

  public void loginUser(final Fragment fragment, final String username, final String password) {
    if (inputValidator.isInvalidInput(username)
      || inputValidator.isInvalidInput(password)) {
      Toast.makeText(fragment.getContext(), "Please enter a username and password",
        Toast.LENGTH_SHORT).show();
      return;
    }

    String encryptedPassword = Encryptor.hash512(password);
    authAPIService.loginUser(username, encryptedPassword).enqueue(new Callback<SessionToken>() {
      @Override
      public void onResponse(Call<SessionToken> call, Response<SessionToken> response) {
        if (response.isSuccessful()) {
          // Start HomeActivity and send token + username over
          SessionToken token = response.body();
          Intent intent = new Intent(fragment.getContext(), HomeActivity.class);
          intent.putExtra(INTENT_SESSION_TOKEN, token);
          intent.putExtra(INTENT_USERNAME, username);
          fragment.startActivity(intent);
          return;
        }

        Toast.makeText(fragment.getContext(), "Response code: " + response.code(),
          Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onFailure(Call<SessionToken> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
        Toast.makeText(fragment.getContext(), "Something went wrong. Please try again later",
          Toast.LENGTH_SHORT).show();
      }
    });
  }
}
