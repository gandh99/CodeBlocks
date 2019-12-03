package com.gandh99.codeblocks.homePage.invitations;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.homePage.invitations.api.Invitation;

import javax.inject.Inject;

public class InvitationsAdapter extends ListAdapter<Invitation, InvitationsAdapter.InvitationsViewHolder> {
  private OnButtonClickListener onButtonClickListener;

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
    final Invitation invitation = getItem(position);
    String projectTitle = invitation.getProjectTitle();
    String filler = " has invited you to join his project: ";
    String inviter = invitation.getInviter();
    SpannableString invitationMessage = new SpannableString(inviter + filler + projectTitle);
    invitationMessage.setSpan(new StyleSpan(Typeface.BOLD), 0, inviter.length(),
      Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    invitationMessage.setSpan(new StyleSpan(Typeface.BOLD), inviter.length() + filler.length(),
      inviter.length() + filler.length() + projectTitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    holder.textViewInvitationMessage.setText(invitationMessage);

    holder.buttonAccept.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onButtonClickListener.onButtonClick(invitation, InvitationResponse.ACCEPT);
      }
    });

    holder.buttonDecline.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onButtonClickListener.onButtonClick(invitation, InvitationResponse.DECLINE);
      }
    });
  }


  class InvitationsViewHolder extends RecyclerView.ViewHolder {
    TextView textViewInvitationMessage;
    Button buttonAccept, buttonDecline;

    public InvitationsViewHolder(@NonNull View itemView) {
      super(itemView);
      textViewInvitationMessage = itemView.findViewById(R.id.list_item_invitation_message);
      buttonAccept = itemView.findViewById(R.id.list_item_button_accept);
      buttonDecline = itemView.findViewById(R.id.list_item_button_decline);
    }
  }

  public interface OnButtonClickListener {
    void onButtonClick(Invitation invitation, InvitationResponse response);
  }

  public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
    this.onButtonClickListener = onButtonClickListener;
  }
}
