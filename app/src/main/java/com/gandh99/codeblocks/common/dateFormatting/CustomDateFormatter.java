package com.gandh99.codeblocks.common.dateFormatting;

import android.nfc.FormatException;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CustomDateFormatter {

  public static String getDatePortion(String date, DatePortion portion) throws FormatException {
    if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
      throw new FormatException("Date format must be YYYY-MM-DD");
    }

    String[] datePieces = date.split("-");
    return datePieces[portion.getIndex()];
  }

  public static String getShortenedMonthName(String date) throws FormatException {
    return getMonthName(date).substring(0, 3);
  }

  public static String getMonthName(String date) throws FormatException {
    if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
      throw new FormatException("Date format must be YYYY-MM-DD");
    }

    int monthValue = Integer.valueOf(getDatePortion(date, DatePortion.MONTH));

    return new DateFormatSymbols().getMonths()[monthValue - 1];
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  public static String getRemainingTime(String endDate) throws FormatException {
    if (!endDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
      throw new FormatException("Date format must be YYYY-MM-DD");
    }

    SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
    String countdown = "";

    try {
      Date date1 = new Date();
      Date date2 = myFormat.parse(endDate);
      long diff = date2.getTime() - date1.getTime();
      long diffMin = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);

      if (diff < 0) {
        countdown = "Over";
        return countdown;
      }

      for (DateUnitContainer.DateUnitWrtMinutes unitWrtMinutes : DateUnitContainer.DateUnitWrtMinutes.values()) {
        if (diffMin > unitWrtMinutes.getNumOfMinutesInUnit()) {
          countdown = (diffMin / unitWrtMinutes.getNumOfMinutesInUnit())
            + " " + unitWrtMinutes.getUnitShortForm();
          break;
        }
      }

    } catch (ParseException e) {
      e.printStackTrace();
    }

    return countdown;
  }

  /* Returns date in format: 01 Jan 2020 */
  public static String getFormattedDate(String deadline) {
    String day = "", month = "", year = "";

    try {
      day = getDatePortion(deadline, DatePortion.DAY);
      month = getShortenedMonthName(deadline);
      year = getDatePortion(deadline, DatePortion.YEAR);
    } catch (FormatException e) {
      e.printStackTrace();
    }

    return day + " " + month + " " + year;
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  public static String getCurrentDate() {
    LocalDate localDate = LocalDate.now();
    return localDate.getYear() + "-" + localDate.getMonth().getValue() + "-" + localDate.getDayOfMonth();
  }
}
