package com.gandh99.codeblocks.homePage.invitations;

public enum InvitationResponse {
  ACCEPT("accept"),
  DECLINE("decline");

  private String response;

  InvitationResponse(String response) {
    this.response = response;
  }
  public String getResponse() {
    return response;
  }
}
