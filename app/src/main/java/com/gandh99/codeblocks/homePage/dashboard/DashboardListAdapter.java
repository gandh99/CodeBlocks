package com.gandh99.codeblocks.homePage.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.homePage.dashboard.api.Project;

import javax.inject.Inject;

public class DashboardListAdapter extends ListAdapter<Project, DashboardListAdapter.ProjectViewHolder> {
  private OnProjectItemClickListener listener;

  @Inject
  public DashboardListAdapter() {
    super(DIFF_CALLBACK);
  }

  private static final DiffUtil.ItemCallback<Project> DIFF_CALLBACK = new DiffUtil.ItemCallback<Project>() {
    @Override
    public boolean areItemsTheSame(@NonNull Project oldItem, @NonNull Project newItem) {
      //TODO: Change this
      return oldItem.getTitle().equals(newItem.getTitle());
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

    ProjectViewHolder(@NonNull View itemView) {
      super(itemView);
      textViewTitle = itemView.findViewById(R.id.list_item_project_title);
      textViewLeader = itemView.findViewById(R.id.list_item_project_leader);
      textViewDescription = itemView.findViewById(R.id.list_item_project_description);

      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          int position = getAdapterPosition();
          if (listener != null && position != RecyclerView.NO_POSITION) {
            listener.onProjectItemClick(getItem(position));
          }
        }
      });
    }
  }

  public interface OnProjectItemClickListener {
    void onProjectItemClick(Project project);
  }

  public void setOnProjectItemClickListener(OnProjectItemClickListener listener) {
    this.listener = listener;
  }
}
