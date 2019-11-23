package com.gandh99.codeblocks.authentication;

import javax.inject.Inject;

public class InputValidator {

  @Inject
  public InputValidator() {}

  public boolean isValidUsername(String username) {
    if (username.equals("")) {
      return false;
    } else if (username.length() < 3 || username.length() > 10) {
      return false;
    } else if (!username.matches("^[a-zA-Z0-9_-]*$")) {
      return false;
    }

    return true;
  }

  public boolean isValidPassword(String password) {
    if (password.equals("")) {
      return false;
    } else if (password.length() < 3 || password.length() > 20) {
      return false;
    }

    return true;
  }
}
