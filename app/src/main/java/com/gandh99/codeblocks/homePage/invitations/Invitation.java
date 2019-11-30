package com.gandh99.codeblocks.homePage.invitations;

public class Invitation {
  private int id;
  private String projectTitle;
  private String inviter;

  public Invitation(int id, String projectTitle, String inviter) {
    this.id = id;
    this.projectTitle = projectTitle;
    this.inviter = inviter;
  }

  public int getId() {
    return id;
  }

  public String getProjectTitle() {
    return projectTitle;
  }

  public String getInviter() {
    return inviter;
  }
}
