package com.gandh99.codeblocks.homePage.invitations.api;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("all")
public class Invitation {
  @SerializedName("id")
  private final int id;

  @SerializedName("projectTitle")
  private final String projectTitle;

  @SerializedName("inviter")
  private final String inviter;

  @SerializedName("invitee")
  private final String invitee;

  public Invitation(int id, String projectTitle, String inviter, String invitee) {
    this.id = id;
    this.projectTitle = projectTitle;
    this.inviter = inviter;
    this.invitee = invitee;
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

  public String getInvitee() {
    return invitee;
  }
}
