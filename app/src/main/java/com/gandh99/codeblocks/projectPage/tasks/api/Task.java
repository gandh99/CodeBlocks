package com.gandh99.codeblocks.projectPage.tasks.api;

import java.text.DateFormatSymbols;

public class Task {
  private int id;
  private String title;
  private String description;
  private String dateCreated;
  private String deadline;

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

  public String getDayCreated() {
    String[] datePieces = dateCreated.split("-");
    return datePieces[2];
  }

  public String getMonthCreatedShortForm() {
    String[] datePieces = dateCreated.split("-");
    int monthValue = Integer.valueOf(datePieces[1]);
    String month = new DateFormatSymbols().getMonths()[monthValue - 1];
    return month.toUpperCase().substring(0, 3);
  }
}
