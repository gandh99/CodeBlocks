package com.gandh99.codeblocks.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.gandh99.codeblocks.R;

public class DashboardListAdapter extends ListAdapter<Project, DashboardListAdapter.ProjectViewHolder> {

  public DashboardListAdapter() {
    super(DIFF_CALLBACK);
  }

  private static final DiffUtil.ItemCallback<Project> DIFF_CALLBACK = new DiffUtil.ItemCallback<Project>() {
    @Override
    public boolean areItemsTheSame(@NonNull Project oldItem, @NonNull Project newItem) {
      return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Project oldItem, @NonNull Project newItem) {
      return (oldItem.getTitle().equals(newItem.getTitle())
        && oldItem.getLeader().equals(newItem.getLeader())
        && oldItem.getDescription().equals(newItem.getDescription()));
    }
  };

  @NonNull
  @Override
  public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
      LayoutInflater
        .from(parent.getContext())
        .inflate(R.layout.list_item_project, parent, false);

    return new ProjectViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
    Project project = getItem(position);
    holder.textViewTitle.setText(project.getTitle());
    holder.textViewLeader.setText(project.getLeader());
    holder.textViewDescription.setText(project.getDescription());
  }

  class ProjectViewHolder extends RecyclerView.ViewHolder {
    TextView textViewTitle, textViewLeader, textViewDescription;

    public ProjectViewHolder(@NonNull View itemView) {
      super(itemView);
      textViewTitle = itemView.findViewById(R.id.list_item_project_title);
      textViewLeader = itemView.findViewById(R.id.list_item_project_leader);
      textViewDescription = itemView.findViewById(R.id.list_item_project_description);
    }
  }
}
