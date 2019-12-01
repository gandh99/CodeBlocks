package com.gandh99.codeblocks.projectPage.tasks.api;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

  @RequiresApi(api = Build.VERSION_CODES.O)
  public String getDeadlineCountdown() {
    SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
    String inputString2 = deadline;
    String countdown = "";

    try {
      Date date1 = new Date();
      Date date2 = myFormat.parse(inputString2);
      long diff = date2.getTime() - date1.getTime();
      long diffDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
      long diffHours = TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
      long diffMin = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);

      if (diffDays > 365) {
        countdown = (diffDays / 365) + " yr";
      } else if (diffDays > 30) {
        countdown = (diffDays / 30) + " mth";
      } else if (diffDays > 7) {
        countdown = (diffDays / 7) + " wk";
      } else if (diffDays > 0) {
        countdown = (diffDays) + " day";
      } else if (diffHours > 0){
        countdown = diffHours + " hr";
      } else if (diffMin > 0) {
        countdown = diffMin + " min";
      } else if (diff < 0) {
        countdown = "Over";
      }

    } catch (ParseException e) {
      e.printStackTrace();
    }

    return countdown;
  }
}
