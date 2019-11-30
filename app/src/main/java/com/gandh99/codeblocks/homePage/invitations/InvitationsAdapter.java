package com.gandh99.codeblocks.homePage.invitations;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.homePage.invitations.api.Invitation;

import javax.inject.Inject;

public class InvitationsAdapter extends ListAdapter<Invitation, InvitationsAdapter.InvitationsViewHolder> {
  
  @Inject
  public InvitationsAdapter() {
    super(DIFF_CALLBACK);
  }

  private static final DiffUtil.ItemCallback<Invitation> DIFF_CALLBACK = new DiffUtil.ItemCallback<Invitation>() {
    @Override
    public boolean areItemsTheSame(@NonNull Invitation oldItem, @NonNull Invitation newItem) {
      return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Invitation oldItem, @NonNull Invitation newItem) {
      return (oldItem.getInviter().equals(newItem.getInviter())
        && oldItem.getProjectTitle().equals(newItem.getProjectTitle()));
    }
  };

  @NonNull
  @Override
  public InvitationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
      LayoutInflater
      .from(parent.getContext())
      .inflate(R.layout.list_item_invitation, parent, false);

    return new InvitationsViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull InvitationsViewHolder holder, int position) {
    Invitation invitation = getItem(position);
    holder.textViewProjectTitle.setText(invitation.getProjectTitle());
    holder.textViewInviter.setText(invitation.getInviter());
  }


  class InvitationsViewHolder extends RecyclerView.ViewHolder {
    TextView textViewProjectTitle, textViewInviter;

    public InvitationsViewHolder(@NonNull View itemView) {
      super(itemView);
      textViewProjectTitle = itemView.findViewById(R.id.list_item_invitation_project_title);
      textViewInviter = itemView.findViewById(R.id.list_item_invitation_inviter);
    }
  }
}
