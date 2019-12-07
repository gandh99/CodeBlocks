package com.gandh99.codeblocks.projectPage.tasks;

import android.nfc.FormatException;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.common.dateFormatting.CustomDateFormatter;
import com.gandh99.codeblocks.common.dateFormatting.DatePortion;
import com.gandh99.codeblocks.projectPage.tasks.api.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
  private static final String TAG = "TaskAdapter";
  private List<Task> taskList = new ArrayList<>();

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
    String dayCreated, monthCreated, deadlineCountdown;

    // Format some of the dates to be displayed
    try {
      dayCreated = CustomDateFormatter.getDatePortion(task.getDateCreated(), DatePortion.DAY);
      monthCreated = CustomDateFormatter.getShortenedMonthName(task.getDateCreated());
      deadlineCountdown = CustomDateFormatter.getRemainingTime(task.getDeadline());
    } catch (FormatException e) {
      Log.d(TAG, "onBindViewHolder: " + e.getMessage());
      return;
    }

    holder.textViewTitle.setText(task.getTitle());
    holder.textViewDescription.setText(task.getDescription());
    holder.textViewDayCreated.setText(dayCreated);
    holder.textViewMonthCreated.setText(monthCreated);
    holder.textViewDeadlineCountdown.setText(deadlineCountdown);
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

  class TaskViewHolder extends RecyclerView.ViewHolder {
    TextView textViewTitle, textViewDescription, textViewDayCreated, textViewMonthCreated, textViewDeadlineCountdown;

    TaskViewHolder(@NonNull View itemView) {
      super(itemView);

      textViewTitle = itemView.findViewById(R.id.list_item_task_title);
      textViewDescription = itemView.findViewById(R.id.list_item_task_description);
      textViewDayCreated = itemView.findViewById(R.id.list_item_task_day_created);
      textViewMonthCreated = itemView.findViewById(R.id.list_item_task_month_created);
      textViewDeadlineCountdown = itemView.findViewById(R.id.list_item_task_deadline_countdown);
    }
  }

}