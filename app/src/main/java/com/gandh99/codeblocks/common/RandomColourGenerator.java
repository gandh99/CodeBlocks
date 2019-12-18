package com.gandh99.codeblocks.common;

import com.gandh99.codeblocks.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

public class RandomColourGenerator {
  private static Map<String, Integer> categoryColourMap = new HashMap<>();
  private static List<Integer> allColours = new ArrayList<>(Arrays.asList(
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
  private static List<Integer> availableColours = new ArrayList<>();

  @Inject
  public RandomColourGenerator() {
  }

  public static int getRandomColour(String category) {
    // If the category already has a colour assigned, we return that colour
    if (categoryColourMap.containsKey(category)) {
      return categoryColourMap.get(category);
    }

    // Otherwise, randomly select a colour from the list of available colours
    Random random = new Random();
    if (availableColours.isEmpty()) {
      availableColours.addAll(allColours);
    }
    int index = random.nextInt(availableColours.size());
    int colour = availableColours.get(index);
    categoryColourMap.put(category, colour);

    // Remove that colour from the list of available colours
    availableColours.remove(index);

    return colour;
  }
}
