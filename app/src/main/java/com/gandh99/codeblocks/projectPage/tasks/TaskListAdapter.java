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

public class TaskListAdapter extends ListAdapter<Task, TaskListAdapter.TaskViewHolder> {


  public TaskListAdapter() {
    super(DIFF_CALLBACK);
  }

  private static final DiffUtil.ItemCallback<Task> DIFF_CALLBACK = new DiffUtil.ItemCallback<Task>() {
    @Override
    public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
//      return oldItem.getId() == newItem.getId();
      //TODO
      return true;
    }

    @Override
    public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
      //TODO
//      return (oldItem.getTitle().equals(newItem.getTitle())
//        && oldItem.getLeader().equals(newItem.getLeader())
//        && oldItem.getDescription().equals(newItem.getDescription()));

      return true;
    }
  };
  
  @NonNull
  @Override
  public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
      LayoutInflater
      .from(parent.getContext())
        .inflate(R.layout.list_item_project, parent, false);
    //TODO

    return new TaskViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
    Task task = getItem(position);
//    holder.textViewTitle.setText(project.getTitle());
//    holder.textViewLeader.setText(project.getLeader());
//    holder.textViewDescription.setText(project.getDescription());
  }

  class TaskViewHolder extends RecyclerView.ViewHolder {
    TextView textViewTitle, textViewLeader, textViewDescription;

    public TaskViewHolder(@NonNull View itemView) {
      super(itemView);

      textViewTitle = itemView.findViewById(R.id.list_item_project_title);
      textViewLeader = itemView.findViewById(R.id.list_item_project_leader);
      textViewDescription = itemView.findViewById(R.id.list_item_project_description);
    }
  }
}
