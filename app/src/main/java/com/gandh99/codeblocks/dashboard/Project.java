package com.gandh99.codeblocks.dashboard;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "project_table")
public class Project {
  @PrimaryKey(autoGenerate = true)
  private int id;
  private String title;
  private String leader;
  private String description;

  public Project(String title, String leader, String description) {
    this.title = title;
    this.leader = leader;
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLeader() {
    return leader;
  }

  public void setLeader(String leader) {
    this.leader = leader;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
