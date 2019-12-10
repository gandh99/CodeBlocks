package com.gandh99.codeblocks.projectPage.members;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.authentication.InputValidator;
import com.gandh99.codeblocks.projectPage.members.api.MemberAPIService;
import com.gandh99.codeblocks.projectPage.members.viewModel.MemberViewModel;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMemberDialog extends DialogFragment {
  private EditText editTextUsername;
  private Spinner spinnerRank;
  private Button buttonInviteUser;
  private MemberViewModel memberViewModel;

  @Inject
  ViewModelProvider.Factory viewModelFactory;

  @Inject
  InputValidator inputValidator;

  @Inject
  MemberAPIService memberAPIService;

  public AddMemberDialog() {
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    AndroidSupportInjection.inject(this);

    View view =
      LayoutInflater
        .from(getContext())
        .inflate(R.layout.dialog_member, null);

    editTextUsername = view.findViewById(R.id.dialog_member_username);
    spinnerRank = view.findViewById(R.id.dialog_member_rank);
    buttonInviteUser = view.findViewById(R.id.dialog_member_invite);

    initViewModel();
    initRankSpinner();
    initInviteButton();

    return new AlertDialog.Builder(getActivity())
      .setView(view)
      .create();  }

  private void initViewModel() {
    memberViewModel = ViewModelProviders.of(this, viewModelFactory).get(MemberViewModel.class);
  }

  private void initRankSpinner() {
    ArrayAdapter<CharSequence> adapter =
      ArrayAdapter.createFromResource(getContext(),
        R.array.rank, android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerRank.setAdapter(adapter);
  }

  private void initInviteButton() {
    buttonInviteUser.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String username = editTextUsername.getText().toString();
        String rank = spinnerRank.getSelectedItem().toString();

        if (inputValidator.isInvalidInput(username)
          || inputValidator.isInvalidInput(rank)) {
          Toast.makeText(getContext(), "Please fill in all the required information",
            Toast.LENGTH_SHORT).show();
          return;
        }

        memberAPIService.inviteMember(username, rank).enqueue(new Callback<ResponseBody>() {
          @Override
          public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (response.isSuccessful()) {
              Toast.makeText(getContext(), "Invitation sent", Toast.LENGTH_SHORT).show();
              dismiss();
              return;
            }

            Toast.makeText(getContext(), "Invitation could not be sent", Toast.LENGTH_SHORT).show();
          }

          @Override
          public void onFailure(Call<ResponseBody> call, Throwable t) {

          }
        });
      }
    });
  }
}
