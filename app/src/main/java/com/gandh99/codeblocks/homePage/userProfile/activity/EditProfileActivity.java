package com.gandh99.codeblocks.homePage.userProfile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.authentication.InputValidator;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;

public class EditProfileActivity extends AppCompatActivity {
  public static final String LOCATION_INTENT = "location";
  public static final String COMPANY_INTENT = "company";
  public static final String JOB_TITLE_INTENT = "jobTitle";
  public static final String EMAIL_INTENT = "email";
  public static final String WEBSITE_INTENT = "website";
  public static final String PERSONAL_MESSAGE_INTENT = "personalMessage";
  public static final int EDIT_PROFILE_REQUEST_CODE = 1;

  private String location, company, jobTitle, email, website, personalMessage;
  private EditText editTextLocation, editTextCompany, editTextJobTitle, editTextEmail,
    editTextWebsite, editTextPersonalMessage;
  private Button buttonSaveProfile;

  @Inject
  InputValidator inputValidator;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_profile);

    initDagger();
    initToolbar();
    getIntentData();
    initViews();
    initSaveProfileButton();
  }

  private void initDagger() {
    AndroidInjection.inject(this);
  }

  private void getIntentData() {
    Intent intent = getIntent();
    location = intent.getStringExtra(LOCATION_INTENT);
    company = intent.getStringExtra(COMPANY_INTENT);
    jobTitle = intent.getStringExtra(JOB_TITLE_INTENT);
    email = intent.getStringExtra(EMAIL_INTENT);
    website = intent.getStringExtra(WEBSITE_INTENT);
    personalMessage = intent.getStringExtra(PERSONAL_MESSAGE_INTENT);
  }

  private void initToolbar() {
    getSupportActionBar().setTitle("Edit Profile");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  private void initViews() {
    editTextLocation = findViewById(R.id.editText_profile_location);
    editTextCompany = findViewById(R.id.editText_profile_company);
    editTextJobTitle = findViewById(R.id.editText_profile_job_title);
    editTextEmail = findViewById(R.id.editText_profile_email);
    editTextWebsite = findViewById(R.id.editText_profile_website);
    editTextPersonalMessage = findViewById(R.id.editText_profile_personal_message);
    buttonSaveProfile = findViewById(R.id.button_save_profile);

    editTextLocation.setText(location);
    editTextCompany.setText(company);
    editTextJobTitle.setText(jobTitle);
    editTextEmail.setText(email);
    editTextWebsite.setText(website);
    editTextPersonalMessage.setText(personalMessage);
  }

  private void initSaveProfileButton() {
    buttonSaveProfile.setOnClickListener(view -> {
      location = editTextLocation.getText().toString();
      company = editTextCompany.getText().toString();
      jobTitle = editTextJobTitle.getText().toString();
      email = editTextEmail.getText().toString();
      website = editTextWebsite.getText().toString();
      personalMessage = editTextPersonalMessage.getText().toString();

      if (inputValidator.isInvalidInput(location)
        || inputValidator.isInvalidInput(company)
        || inputValidator.isInvalidInput(jobTitle)
        || inputValidator.isInvalidInput(email)
        || inputValidator.isInvalidInput(website)
        || inputValidator.isInvalidInput(personalMessage)) {
        Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
        return;
      }

      Intent returnIntent = new Intent();
      returnIntent.putExtra(LOCATION_INTENT, location);
      returnIntent.putExtra(COMPANY_INTENT, company);
      returnIntent.putExtra(JOB_TITLE_INTENT, jobTitle);
      returnIntent.putExtra(EMAIL_INTENT, email);
      returnIntent.putExtra(WEBSITE_INTENT, website);
      returnIntent.putExtra(PERSONAL_MESSAGE_INTENT, personalMessage);
      setResult(RESULT_OK, returnIntent);
      finish();
    });
  }

  @Override
  public boolean onSupportNavigateUp() {
    finish();
    return true;
  }
}
