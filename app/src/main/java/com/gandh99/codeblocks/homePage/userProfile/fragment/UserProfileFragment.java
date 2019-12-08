package com.gandh99.codeblocks.homePage.userProfile.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.homePage.userProfile.activity.EditProfileActivity;

import dagger.android.support.AndroidSupportInjection;

import static com.gandh99.codeblocks.homePage.userProfile.activity.EditProfileActivity.COMPANY_INTENT;
import static com.gandh99.codeblocks.homePage.userProfile.activity.EditProfileActivity.EDIT_PROFILE_REQUEST_CODE;
import static com.gandh99.codeblocks.homePage.userProfile.activity.EditProfileActivity.EMAIL_INTENT;
import static com.gandh99.codeblocks.homePage.userProfile.activity.EditProfileActivity.JOB_TITLE_INTENT;
import static com.gandh99.codeblocks.homePage.userProfile.activity.EditProfileActivity.LOCATION_INTENT;
import static com.gandh99.codeblocks.homePage.userProfile.activity.EditProfileActivity.PERSONAL_MESSAGE_INTENT;
import static com.gandh99.codeblocks.homePage.userProfile.activity.EditProfileActivity.WEBSITE_INTENT;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment {
  private TextView textViewLocation, textViewCompany, textViewJobTitle, textViewEmail,
    textViewWebsite, textViewPersonalMessage;
  private Button buttonEditProfile;

  public UserProfileFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Setup Dagger 2
    initDagger();

    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
    textViewLocation = view.findViewById(R.id.textView_location);
    textViewCompany = view.findViewById(R.id.textView_company);
    textViewJobTitle = view.findViewById(R.id.textView_job_title);
    textViewEmail = view.findViewById(R.id.textView_email);
    textViewWebsite = view.findViewById(R.id.textView_website);
    textViewPersonalMessage = view.findViewById(R.id.textView_personal_message);
    buttonEditProfile = view.findViewById(R.id.button_edit_profile);

    initEditProfileButton();

    return view;
  }

  private void initDagger() {
    AndroidSupportInjection.inject(this);
  }

  private void initEditProfileButton() {
    buttonEditProfile.setOnClickListener(view -> {
      Intent intent = new Intent(getContext(), EditProfileActivity.class);
      intent.putExtra(LOCATION_INTENT, textViewLocation.getText().toString());
      intent.putExtra(COMPANY_INTENT, textViewCompany.getText().toString());
      intent.putExtra(JOB_TITLE_INTENT, textViewJobTitle.getText().toString());
      intent.putExtra(EMAIL_INTENT, textViewEmail.getText().toString());
      intent.putExtra(WEBSITE_INTENT, textViewWebsite.getText().toString());
      intent.putExtra(PERSONAL_MESSAGE_INTENT, textViewPersonalMessage.getText().toString());
      startActivityForResult(intent, EDIT_PROFILE_REQUEST_CODE);
    });
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
  }
}
