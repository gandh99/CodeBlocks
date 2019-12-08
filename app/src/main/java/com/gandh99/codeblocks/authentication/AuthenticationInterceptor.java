package com.gandh99.codeblocks.authentication;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public class AuthenticationInterceptor implements Interceptor {
  private String SESSION_TOKEN_PREFIX = "Token ";
  private String sessionToken = "";
  private String username = "";
  private String projectID = "";

  @Inject
  public AuthenticationInterceptor() {}

  public void setSessionToken(String sessionToken) {
    this.sessionToken = SESSION_TOKEN_PREFIX + sessionToken;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setProjectID(String projectID) {
    this.projectID = projectID;
  }

  @NotNull
  @Override
  public Response intercept(@NotNull Chain chain) throws IOException {
    Request request = chain.request();
    Request.Builder requestBuilder = request.newBuilder();
    requestBuilder.addHeader("Authorization", sessionToken);
    requestBuilder.addHeader("username", username);
    requestBuilder.addHeader("projectID", projectID);

    return chain.proceed(requestBuilder.build());
  }
}
