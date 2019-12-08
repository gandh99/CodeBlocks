package com.gandh99.codeblocks.homePage.userProfile.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.homePage.userProfile.activity.EditUserProfileActivity;
import com.gandh99.codeblocks.homePage.userProfile.api.UserProfile;
import com.gandh99.codeblocks.homePage.userProfile.api.UserProfileAPIService;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.gandh99.codeblocks.homePage.userProfile.activity.EditUserProfileActivity.COMPANY_INTENT;
import static com.gandh99.codeblocks.homePage.userProfile.activity.EditUserProfileActivity.EDIT_PROFILE_REQUEST_CODE;
import static com.gandh99.codeblocks.homePage.userProfile.activity.EditUserProfileActivity.EMAIL_INTENT;
import static com.gandh99.codeblocks.homePage.userProfile.activity.EditUserProfileActivity.JOB_TITLE_INTENT;
import static com.gandh99.codeblocks.homePage.userProfile.activity.EditUserProfileActivity.LOCATION_INTENT;
import static com.gandh99.codeblocks.homePage.userProfile.activity.EditUserProfileActivity.PERSONAL_MESSAGE_INTENT;
import static com.gandh99.codeblocks.homePage.userProfile.activity.EditUserProfileActivity.WEBSITE_INTENT;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment {
  private static final String TAG = "UserProfileFragment";
  private TextView textViewLocation, textViewCompany, textViewJobTitle, textViewEmail,
    textViewWebsite, textViewPersonalMessage;
  private Button buttonEditProfile;

  @Inject
  UserProfileAPIService userProfileAPIService;

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

    loadProfile();
    initEditProfileButton();

    return view;
  }

  private void initDagger() {
    AndroidSupportInjection.inject(this);
  }

  private void loadProfile() {
    userProfileAPIService.getUserProfile().enqueue(new Callback<UserProfile>() {
      @Override
      public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
        if (response.isSuccessful()) {
          UserProfile userProfile = response.body();
          String location = userProfile.getLocation().equals("") ? "Location" : userProfile.getLocation();
          String company = userProfile.getCompany().equals("") ? "Company" : userProfile.getCompany();
          String jobTitle = userProfile.getJobTitle().equals("") ? "Job Title" : userProfile.getJobTitle();
          String email = userProfile.getEmail().equals("") ? "Email" : userProfile.getEmail();
          String website = userProfile.getWebsite().equals("") ? "Website" : userProfile.getWebsite();
          String personalMessage = userProfile.getPersonalMessage().equals("")
            ? "Personal Message" : userProfile.getPersonalMessage();

          textViewLocation.setText(location);
          textViewCompany.setText(company);
          textViewJobTitle.setText(jobTitle);
          textViewEmail.setText(email);
          textViewWebsite.setText(website);
          textViewPersonalMessage.setText(personalMessage);
        }
      }

      @Override
      public void onFailure(Call<UserProfile> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
      }
    });
  }

  private void initEditProfileButton() {
    buttonEditProfile.setOnClickListener(view -> {
      Intent intent = new Intent(getContext(), EditUserProfileActivity.class);
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
    if (requestCode == EDIT_PROFILE_REQUEST_CODE) {
      if (resultCode == RESULT_OK) {
        Intent intent = data;
        String location = intent.getStringExtra(LOCATION_INTENT);
        String company = intent.getStringExtra(COMPANY_INTENT);
        String jobTitle = intent.getStringExtra(JOB_TITLE_INTENT);
        String email = intent.getStringExtra(EMAIL_INTENT);
        String website = intent.getStringExtra(WEBSITE_INTENT);
        String personalMessage = intent.getStringExtra(PERSONAL_MESSAGE_INTENT);

        textViewLocation.setText(location);
        textViewCompany.setText(company);
        textViewJobTitle.setText(jobTitle);
        textViewEmail.setText(email);
        textViewWebsite.setText(website);
        textViewPersonalMessage.setText(personalMessage);

        Toast.makeText(getContext(), "Successfully updated profile", Toast.LENGTH_SHORT).show();
      }
    }
  }
}
