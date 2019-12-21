package com.gandh99.codeblocks.projectPage.tasks.taskFilter;

import android.view.View;
import android.widget.RadioGroup;

import com.gandh99.codeblocks.projectPage.tasks.api.Task;

import java.util.List;

public interface TaskFilterCriteria {
  List<Task> filterTasks(View view, RadioGroup radioGroup, List<Task> taskList);
}
