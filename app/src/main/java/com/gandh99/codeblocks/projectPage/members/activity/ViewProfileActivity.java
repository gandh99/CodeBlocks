package com.gandh99.codeblocks.projectPage.members.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.common.Base64EncoderDecoder;

public class ViewProfileActivity extends AppCompatActivity {
  public static final String USERNAME_INTENT = "username";
  public static final String PROFILE_PICTURE_INTENT = "profilePicture";
  public static final String LOCATION_INTENT = "location";
  public static final String COMPANY_INTENT = "company";
  public static final String JOB_TITLE_INTENT = "jobTitle";
  public static final String EMAIL_INTENT = "email";
  public static final String WEBSITE_INTENT = "website";
  public static final String PERSONAL_MESSAGE_INTENT = "personalMessage";

  private ImageView imageViewProfilePicture;
  private TextView textViewUsername, textViewLocation, textViewCompany, textViewJobTitle,
    textViewEmail, textViewWebsite, textViewPersonalMessage;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_profile);

    imageViewProfilePicture = findViewById(R.id.member_imageView_profile_picture);
    textViewUsername = findViewById(R.id.member_textView_username);
    textViewLocation = findViewById(R.id.member_textView_location);
    textViewCompany = findViewById(R.id.member_textView_company);
    textViewJobTitle = findViewById(R.id.member_textView_job_title);
    textViewEmail = findViewById(R.id.member_textView_email);
    textViewWebsite = findViewById(R.id.member_textView_website);
    textViewPersonalMessage = findViewById(R.id.member_textView_personal_message);

    initViews();
  }

  private void initViews() {
    Intent intent = getIntent();
    String username = ensureNotBlank(intent.getStringExtra(USERNAME_INTENT));
    String location = ensureNotBlank(intent.getStringExtra(LOCATION_INTENT));
    String company = ensureNotBlank(intent.getStringExtra(COMPANY_INTENT));
    String jobTitle = ensureNotBlank(intent.getStringExtra(JOB_TITLE_INTENT));
    String email = ensureNotBlank(intent.getStringExtra(EMAIL_INTENT));
    String website = ensureNotBlank(intent.getStringExtra(WEBSITE_INTENT));
    String personalMessage = ensureNotBlank(intent.getStringExtra(PERSONAL_MESSAGE_INTENT));

    textViewUsername.setText(username);
    textViewLocation.setText(location);
    textViewCompany.setText(company);
    textViewJobTitle.setText(jobTitle);
    textViewEmail.setText(email);
    textViewWebsite.setText(website);
    textViewPersonalMessage.setText(personalMessage);

    // Set the profile picture
    String base64 = intent.getStringExtra(PROFILE_PICTURE_INTENT);
    if (base64.equals("")) {
      imageViewProfilePicture.setImageResource(R.drawable.ic_account_circle_blue_108dp);
    } else {
      RoundedBitmapDrawable drawable =
        Base64EncoderDecoder.toRoundedBitmapDrawable(getResources(), base64);
      imageViewProfilePicture.setImageDrawable(drawable);
    }

    // Set the toolbar
    getSupportActionBar().setTitle(intent.getStringExtra(USERNAME_INTENT));
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  private String ensureNotBlank(String text) {
    return text.equals("") ? "--" : text;
  }

  @Override
  public boolean onSupportNavigateUp() {
    finish();
    return true;
  }
}
