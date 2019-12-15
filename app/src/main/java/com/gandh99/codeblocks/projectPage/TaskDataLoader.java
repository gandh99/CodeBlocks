package com.gandh99.codeblocks.projectPage;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.projectPage.tasks.api.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.Map;

public class TaskDataLoader {
  public static final String TITLE = "title";
  public static final String DESCRIPTION = "description";
  public static final String DEADLINE = "deadline";
  public static final String PRIORITY = "priority";
  public static final String CATEGORY = "category";

  public static void loadData(Task task, Map<String, View> stringViewMap, LayoutInflater inflater) {
    EditText editTextTitle = (EditText) stringViewMap.get(TITLE);
    EditText editTextDescription = (EditText) stringViewMap.get(DESCRIPTION);
    EditText editTextDeadline = (EditText) stringViewMap.get(DEADLINE);
    ChipGroup chipGroupPriority = (ChipGroup) stringViewMap.get(PRIORITY);
    ChipGroup chipGroupCategory = (ChipGroup) stringViewMap.get(CATEGORY);

    editTextTitle.setText(task.getTitle());
    editTextDescription.setText(task.getDescription());
    editTextDeadline.setText(task.getDeadline());
    loadPriority(chipGroupPriority, task.getPriority());
    loadCategories(chipGroupCategory, task.getTaskCategories(), inflater);
  }

  private static void loadPriority(ChipGroup chipGroupTaskPriority, String priority) {
    for (int i = 0; i < chipGroupTaskPriority.getChildCount(); i++) {
      Chip chip = (Chip) chipGroupTaskPriority.getChildAt(i);
      if (chip.getText().toString().equals(priority)) {
        chip.setChecked(true);
        return;
      }
    }
  }

  private static void loadCategories(ChipGroup chipGroupTaskCategories, String[] taskCategories,
                                     LayoutInflater inflater) {
    for (String category : taskCategories) {
      Chip chip =
        (Chip) inflater
          .inflate(R.layout.chip_closable, chipGroupTaskCategories, false);
      chip.setText(category);
      chipGroupTaskCategories.addView(chip);
    }
  }
}
