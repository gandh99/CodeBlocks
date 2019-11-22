package com.gandh99.codeblocks.authentication.api;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
  @SerializedName("username")
  private String username;
  @SerializedName("password")
  private String password;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }
}
