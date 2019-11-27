package com.gandh99.codeblocks.projectPage.members;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.gandh99.codeblocks.R;

public class MemberListAdapter extends ListAdapter<ProjectMember, MemberListAdapter.MemberViewHolder> {

  protected MemberListAdapter() {
    super(DIFF_CALLBACK);
  }

  private static final DiffUtil.ItemCallback<ProjectMember> DIFF_CALLBACK = new DiffUtil.ItemCallback<ProjectMember>() {
    @Override
    public boolean areItemsTheSame(@NonNull ProjectMember oldItem, @NonNull ProjectMember newItem) {
//      return oldItem.getId() == newItem.getId();
      //TODO
      return true;
    }

    @Override
    public boolean areContentsTheSame(@NonNull ProjectMember oldItem, @NonNull ProjectMember newItem) {
//      return (oldItem.getTitle().equals(newItem.getTitle())
//        && oldItem.getDescription().equals(newItem.getDescription())
//        && oldItem.getDateCreated().equals(newItem.getDateCreated())
//        && oldItem.getDeadline().equals(newItem.getDeadline()));
      //TODO
      return true;
    }
  };
  
  @NonNull
  @Override
  public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
      LayoutInflater
      .from(parent.getContext())
      .inflate(R.layout.list_item_member, parent, false);

    return new MemberViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
    ProjectMember projectMember = getItem(position);
    holder.textViewUsername.setText(projectMember.getUsername());
    holder.textViewRank.setText(projectMember.getRank());
  }

  class MemberViewHolder extends RecyclerView.ViewHolder {
    TextView textViewUsername, textViewRank;

    public MemberViewHolder(@NonNull View itemView) {
      super(itemView);

      textViewUsername = itemView.findViewById(R.id.list_item_member_username);
      textViewRank = itemView.findViewById(R.id.list_item_member_rank);
    }
  }
}
