package com.gandh99.codeblocks.projectPage.tasks.api;

import java.io.Serializable;

public class Task implements Serializable {
  private int id;
  private String title;
  private String description;
  private String dateCreated;
  private String deadline;
  private String priority;
  private String[] assignees;
  private String[] categories;
  private String completed;

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

  public String[] getAssignees() {
    return assignees;
  }

  public String[] getTaskCategories() {
    return categories;
  }

  public String isCompleted() {
    return completed;
  }
}
