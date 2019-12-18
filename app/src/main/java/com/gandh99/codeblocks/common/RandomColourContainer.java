package com.gandh99.codeblocks.common;

import com.gandh99.codeblocks.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RandomColourContainer {
  private static List<Integer> randomColours = new ArrayList<>(Arrays.asList(
    R.color.colorRed,
    R.color.colorOrange,
    R.color.colorYellow,
    R.color.colorLightGreen,
    R.color.colorDarkGreen,
    R.color.colorLightBlue,
    R.color.colorLightIndigo,
    R.color.colorPurple,
    R.color.colorDarkPurple
  ));

  public static List<Integer> getAllColours() {
    return randomColours;
  }
}
