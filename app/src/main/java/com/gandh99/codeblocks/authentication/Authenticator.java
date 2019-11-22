package com.gandh99.codeblocks.authentication;

import android.util.Log;
import android.widget.Toast;

import com.gandh99.codeblocks.authentication.api.AuthAPIService;
import com.gandh99.codeblocks.authentication.api.User;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Authenticator {
  private static final String TAG = "Authenticator";
  private AuthAPIService authAPIService;

  @Inject
  public Authenticator(AuthAPIService authAPIService) {
    this.authAPIService = authAPIService;
  }

  public void registerUser(String username, String password) {
    final boolean[] isSuccess = { false };

    authAPIService.registerUser(username, password).enqueue(new Callback<User>() {
      @Override
      public void onResponse(Call<User> call, Response<User> response) {
        if (response.isSuccessful()) {
          isSuccess[0] = true;
          Log.d(TAG, "onResponse: " + response.code());
        }

        Log.d(TAG, "onResponse: " + response.code());
      }

      @Override
      public void onFailure(Call<User> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
      }
    });

//    return isSuccess[0];
  }
}
