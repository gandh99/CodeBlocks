package com.gandh99.codeblocks.projectPage.members;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.gandh99.codeblocks.App;
import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.authentication.InputValidator;
import com.gandh99.codeblocks.projectPage.members.api.MemberAPIService;
import com.gandh99.codeblocks.projectPage.members.viewModel.MemberViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMemberDialog extends DialogFragment {
  private EditText editTextUsername;
  private ChipGroup chipGroupRank;
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
    chipGroupRank = view.findViewById(R.id.dialog_member_rank);
    buttonInviteUser = view.findViewById(R.id.dialog_member_invite);

    initViewModel();
    initChipGroupRank();
    initInviteButton();

    return new AlertDialog.Builder(getActivity())
      .setView(view)
      .create();  }

  private void initViewModel() {
    memberViewModel = ViewModelProviders.of(this, viewModelFactory).get(MemberViewModel.class);
  }

  private void initChipGroupRank() {
    String[] allRanks = App.getAppResources().getStringArray(R.array.rank);
    for (String rank : allRanks) {
      Chip chip =
        (Chip) getActivity()
          .getLayoutInflater()
          .inflate(R.layout.chip_checkable, chipGroupRank, false);
      chip.setText(rank);
      chip.setCheckable(true);
      chip.setCheckedIconVisible(true);
      chipGroupRank.addView(chip);
    }
  }

  private void initInviteButton() {
    buttonInviteUser.setOnClickListener(view -> {
      String username = editTextUsername.getText().toString();
      String rank = getSelectedRank();

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
    });
  }

  private String getSelectedRank() {
    String defaultRank = "MEMBER";

    for (int i = 0; i < chipGroupRank.getChildCount(); i++) {
      Chip chip = (Chip) chipGroupRank.getChildAt(i);
      if (chip.isChecked()) {
        return chip.getText().toString();
      }
    }

    return defaultRank;
  }
}
