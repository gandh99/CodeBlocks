package com.gandh99.codeblocks.homePage.dashboard.api;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("all")
public class Project {
  @SerializedName("pk")
  private final int id;

  @SerializedName("title")
  private final String title;

  @SerializedName("leader")
  private final String leader;

  @SerializedName("description")
  private final String description;

  public Project(int id, String title, String leader, String description) {
    this.id = id;
    this.title = title;
    this.leader = leader;
    this.description = description;
  }

  public String getTitle() {
    return title;
  }

  public String getLeader() {
    return leader;
  }

  public String getDescription() {
    return description;
  }

  public int getId() {
    return id;
  }
}
