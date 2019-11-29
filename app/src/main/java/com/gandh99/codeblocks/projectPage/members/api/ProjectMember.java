package com.gandh99.codeblocks.projectPage.members.api;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("all")
public class ProjectMember {
  @SerializedName("pk")
  private final int pk;

  @SerializedName("username")
  private final String username;

  @SerializedName("rank")
  private final String rank;

  public ProjectMember(int pk, String username, String rank) {
    this.pk = pk;
    this.username = username;
    this.rank = rank;
  }

  public int getPk() {
    return pk;
  }

  public String getUsername() {
    return username;
  }

  public String getRank() {
    return rank;
  }
}
