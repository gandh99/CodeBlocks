package com.gandh99.codeblocks.common.dateFormatting;

public enum DatePortion {
  YEAR(0),
  MONTH(1),
  DAY(2);

  private int index;

  DatePortion(int index) {
    this.index = index;
  }

  int getIndex() {
    return index;
  }
}
