package com.gandh99.codeblocks.homePage.dashboard.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


@SuppressWarnings("all")
public class Project implements Serializable {
  @SerializedName("pk")
  private final int id;

  @SerializedName("title")
  private final String title;

  @SerializedName("description")
  private final String description;

  public Project(int id, String title, String description) {
    this.id = id;
    this.title = title;
    this.description = description;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public int getId() {
    return id;
  }
}
