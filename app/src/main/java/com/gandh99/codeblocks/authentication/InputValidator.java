package com.gandh99.codeblocks.authentication;

import javax.inject.Inject;

public class InputValidator {

  @Inject
  public InputValidator() {}

  public boolean isInvalidRegistrationUsername(String username) {
    return isInvalidInput(username)
      || username.length() < 3
      || username.length() > 10
      || !username.matches("^[a-zA-Z0-9_-]*$")
      || username.matches("_{2,}")
      || username.matches("-{2,}")
      ;
  }

  public boolean isInvalidRegistrationPassword(String password) {
    return isInvalidInput(password)
      || password.length() < 3
      || password.length() > 20;
  }

  public boolean isInvalidInput(String input) {
    return input.equals("");
  }
}
