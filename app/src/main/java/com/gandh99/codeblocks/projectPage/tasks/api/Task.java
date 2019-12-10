package com.gandh99.codeblocks.projectPage.tasks.api;

public class Task {
  private int id;
  private String title;
  private String description;
  private String dateCreated;
  private String deadline;
  private String priority;

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public String getDateCreated() {
    return dateCreated;
  }

  public String getDeadline() {
    return deadline;
  }

  public String getPriority() {
    return priority;
  }
}
