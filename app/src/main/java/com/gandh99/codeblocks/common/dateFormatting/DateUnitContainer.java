package com.gandh99.codeblocks.common.dateFormatting;

public class DateUnitContainer {
  private static final int MINUTES_IN_HOUR = 60;
  private static final int MINUTES_IN_DAY = MINUTES_IN_HOUR * 24;
  private static final int MINUTES_IN_WEEK = MINUTES_IN_DAY * 7;
  private static final int MINUTES_IN_MONTH = MINUTES_IN_WEEK * 4;
  private static final int MINUTES_IN_YEAR = MINUTES_IN_MONTH * 12;

  public enum DateUnitWrtMinutes {
    YEAR(MINUTES_IN_YEAR, "yr"),
    MONTH(MINUTES_IN_MONTH, "mth"),
    WEEK(MINUTES_IN_WEEK, "wk"),
    DAY(MINUTES_IN_DAY, "day"),
    HOUR(MINUTES_IN_HOUR, "hr"),
    MINUTES(1, "min");

    private int numOfMinutesInUnit;
    private String unitShortForm;

    DateUnitWrtMinutes(int numOfMinutesInUnit, String unitShortForm) {
      this.numOfMinutesInUnit = numOfMinutesInUnit;
      this.unitShortForm = unitShortForm;
    }

    public int getNumOfMinutesInUnit() {
      return numOfMinutesInUnit;
    }

    public String getUnitShortForm() {
      return unitShortForm;
    }
  }
}
