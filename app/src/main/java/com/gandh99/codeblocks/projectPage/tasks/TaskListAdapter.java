package com.gandh99.codeblocks.projectPage.tasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.projectPage.tasks.api.Task;

public class TaskListAdapter extends ListAdapter<Task, TaskListAdapter.TaskViewHolder> {

  public TaskListAdapter() {
    super(DIFF_CALLBACK);
  }

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

  @Override
  public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
    Task task = getItem(position);
    holder.textViewTitle.setText(task.getTitle());
    holder.textViewDescription.setText(task.getDescription());
    holder.textViewDayCreated.setText(task.getDayCreated());
    holder.textViewMonthCreated.setText(task.getMonthCreatedShortForm());
    holder.textViewDeadline.setText(task.getDeadline());
  }

  class TaskViewHolder extends RecyclerView.ViewHolder {
    TextView textViewTitle, textViewDescription, textViewDayCreated, textViewMonthCreated, textViewDeadline;

    public TaskViewHolder(@NonNull View itemView) {
      super(itemView);

      textViewTitle = itemView.findViewById(R.id.list_item_task_title);
      textViewDescription = itemView.findViewById(R.id.list_item_task_description);
      textViewDayCreated = itemView.findViewById(R.id.list_item_task_day_created);
      textViewMonthCreated = itemView.findViewById(R.id.list_item_task_month_created);
      textViewDeadline = itemView.findViewById(R.id.list_item_task_deadline);
    }
  }
}
