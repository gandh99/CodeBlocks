package com.gandh99.codeblocks.authentication.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


@SuppressWarnings("all")
public class SessionToken implements Serializable {
  @SerializedName("Token")
  private final String token;

  public SessionToken(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }
}
