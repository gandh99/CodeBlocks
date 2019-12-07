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
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.common.dateFormatting.CustomDateFormatter;
import com.gandh99.codeblocks.common.dateFormatting.DatePortion;
import com.gandh99.codeblocks.projectPage.tasks.api.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//public class TaskListAdapter extends ListAdapter<Task, TaskListAdapter.TaskViewHolder> {

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {
  private static final String TAG = "TaskListAdapter";
  private List<Task> taskList = new ArrayList<>();

//  public TaskListAdapter() {
//    super(DIFF_CALLBACK);
//  }

  public TaskListAdapter() {}

  private static final DiffUtil.ItemCallback<Task> DIFF_CALLBACK = new DiffUtil.ItemCallback<Task>() {
    @Override
    public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
      return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
      return (oldItem.getTitle().equals(newItem.getTitle())
        && oldItem.getDescription().equals(newItem.getDescription())
        && oldItem.getDateCreated().equals(newItem.getDateCreated())
        && oldItem.getDeadline().equals(newItem.getDeadline()));
    }
  };

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
//    Task task = getItem(position);
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
//    holder.textViewDeadlineCountdown.setText(deadlineCountdown);
    holder.textViewDeadlineCountdown.setText(task.getDeadline());
  }

  @Override
  public int getItemCount() {
    return taskList.size();
  }

  class TaskViewHolder extends RecyclerView.ViewHolder {
    TextView textViewTitle, textViewDescription, textViewDayCreated, textViewMonthCreated, textViewDeadlineCountdown;

    public TaskViewHolder(@NonNull View itemView) {
      super(itemView);

      textViewTitle = itemView.findViewById(R.id.list_item_task_title);
      textViewDescription = itemView.findViewById(R.id.list_item_task_description);
      textViewDayCreated = itemView.findViewById(R.id.list_item_task_day_created);
      textViewMonthCreated = itemView.findViewById(R.id.list_item_task_month_created);
      textViewDeadlineCountdown = itemView.findViewById(R.id.list_item_task_deadline_countdown);
    }
  }

//  @Override
  public void submitList(@Nullable List<Task> list) {
    taskList = list;
    notifyDataSetChanged();
//    super.submitList(list);
  }

  public List<Task> getTaskList() { return taskList; }
}
