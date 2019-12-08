package com.gandh99.codeblocks.homePage.userProfile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.gandh99.codeblocks.R;

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
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_profile);
    
    initToolbar();
    getIntentData();
    initViews();

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

  @Override
  public boolean onSupportNavigateUp() {
    finish();
    return true;
  }
}
