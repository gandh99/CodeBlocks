package com.gandh99.codeblocks.projectPage.tasks;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.nfc.FormatException;
import android.os.Build;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.common.dateFormatting.CustomDateFormatter;
import com.gandh99.codeblocks.common.dateFormatting.DatePortion;
import com.gandh99.codeblocks.projectPage.tasks.api.Task;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
  private static final String TAG = "TaskAdapter";
  private List<Task> taskList = new ArrayList<>();
  private Map<String, Integer> priorityMap = new HashMap<>();

  public TaskAdapter() {}

  @NonNull
  @Override
  public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
      LayoutInflater
        .from(parent.getContext())
        .inflate(R.layout.list_item_task, parent, false);



    return new TaskViewHolder(view);
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  @Override
  public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
    Task task = taskList.get(position);
    String dayCreated, monthCreated, deadline;

    // Format some of the dates to be displayed
    try {
      dayCreated = CustomDateFormatter.getDatePortion(task.getDateCreated(), DatePortion.DAY);
      monthCreated = CustomDateFormatter.getShortenedMonthName(task.getDateCreated());
      deadline = CustomDateFormatter.getFormattedDate(task.getDeadline());
    } catch (FormatException e) {
      Log.d(TAG, "onBindViewHolder: " + e.getMessage());
      return;
    }

    holder.priorityColour.setBackgroundColor(priorityMap.get(task.getPriority()));
    holder.textViewTitle.setText(task.getTitle());
    holder.textViewDescription.setText(task.getDescription());
//    holder.textViewDayCreated.setText(dayCreated);
//    holder.textViewMonthCreated.setText(monthCreated);
    holder.chipDeadlineCountdown.setText(deadline);
  }

  @Override
  public int getItemCount() {
    return taskList.size();
  }

  public void updateList(@Nullable List<Task> list) {
    taskList = list;
    notifyDataSetChanged();
  }

  public List<Task> getTaskList() { return taskList; }

  public void setPriorityTypes(Resources resources) {
    String[] priority = resources.getStringArray(R.array.priority);
    priorityMap.put(priority[0], ResourcesCompat.getColor(resources, R.color.colorWhite, null));
    priorityMap.put(priority[1], ResourcesCompat.getColor(resources, R.color.colorGreen, null));
    priorityMap.put(priority[2], ResourcesCompat.getColor(resources, R.color.colorOrange, null));
    priorityMap.put(priority[3], ResourcesCompat.getColor(resources, R.color.colorRed, null));
  }

  class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener,
    MenuItem.OnMenuItemClickListener {
    View priorityColour;
    TextView textViewTitle, textViewDescription, textViewDayCreated, textViewMonthCreated;
    Chip chipDeadlineCountdown;
    ImageView imageViewActions;
    MenuItem editTask, commentTask, markAsDoneTask;

    TaskViewHolder(@NonNull View itemView) {
      super(itemView);

      priorityColour = itemView.findViewById(R.id.priority_colour);
      textViewTitle = itemView.findViewById(R.id.list_item_task_title);
      textViewDescription = itemView.findViewById(R.id.list_item_task_description);
//      textViewDayCreated = itemView.findViewById(R.id.list_item_task_day_created);
//      textViewMonthCreated = itemView.findViewById(R.id.list_item_task_month_created);
      chipDeadlineCountdown = itemView.findViewById(R.id.list_item_task_deadline_countdown);
      imageViewActions = itemView.findViewById(R.id.list_item_task_actions);

      itemView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
      contextMenu.setHeaderTitle("Select an option");
      editTask = contextMenu.add("Edit");
      commentTask = contextMenu.add("Comment");
      markAsDoneTask = contextMenu.add("Mark as done");
      editTask.setOnMenuItemClickListener(this);
      commentTask.setOnMenuItemClickListener(this);
      markAsDoneTask.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
      if (menuItem == editTask) {
        Log.d(TAG, "onMenuItemClick: " + "editTask");
      } else if (menuItem == commentTask) {
        Log.d(TAG, "onMenuItemClick: " + "commentTask");
      } else if (menuItem == markAsDoneTask) {
        Log.d(TAG, "onMenuItemClick: " + "markAsDoneTask");
      }

      return false;
    }

  }

}
