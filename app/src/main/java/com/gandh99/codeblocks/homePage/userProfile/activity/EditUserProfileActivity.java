package com.gandh99.codeblocks.homePage.userProfile.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.authentication.InputValidator;
import com.gandh99.codeblocks.common.Base64EncoderDecoder;
import com.gandh99.codeblocks.homePage.userProfile.api.UserProfileAPIService;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserProfileActivity extends AppCompatActivity {
  private static final String TAG = "EditUserProfileActivity";
  public static final String PROFILE_PICTURE_INTENT = "profilePicture";
  public static final String LOCATION_INTENT = "location";
  public static final String COMPANY_INTENT = "company";
  public static final String JOB_TITLE_INTENT = "jobTitle";
  public static final String EMAIL_INTENT = "email";
  public static final String WEBSITE_INTENT = "website";
  public static final String PERSONAL_MESSAGE_INTENT = "personalMessage";
  public static final int EDIT_PROFILE_REQUEST_CODE = 1;
  public static final int GET_FROM_GALLERY_REQUEST_CODE = 2;

  // Views
  private ImageView imageViewProfilePicture;
  private TextView textViewEditPicture;
  private EditText editTextLocation, editTextCompany, editTextJobTitle, editTextEmail,
    editTextWebsite, editTextPersonalMessage;
  private Button buttonSaveProfile;

  // Data in views
  private String location, company, jobTitle, email, website, personalMessage;
  private Bitmap bitmapProfilePicture;

  @Inject
  InputValidator inputValidator;

  @Inject
  UserProfileAPIService userProfileAPIService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_user_profile);

    initDagger();
    initToolbar();
    getIntentData();
    initViews();
    initEditPictureButton();
    initSaveProfileButton();
  }

  private void initDagger() {
    AndroidInjection.inject(this);
  }

  private void getIntentData() {
    Intent intent = getIntent();
    bitmapProfilePicture = intent.getParcelableExtra(PROFILE_PICTURE_INTENT);
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
    imageViewProfilePicture = findViewById(R.id.imageView_profile_picture);
    textViewEditPicture = findViewById(R.id.textView_edit_picture);
    editTextLocation = findViewById(R.id.editText_profile_location);
    editTextCompany = findViewById(R.id.editText_profile_company);
    editTextJobTitle = findViewById(R.id.editText_profile_job_title);
    editTextEmail = findViewById(R.id.editText_profile_email);
    editTextWebsite = findViewById(R.id.editText_profile_website);
    editTextPersonalMessage = findViewById(R.id.editText_profile_personal_message);
    buttonSaveProfile = findViewById(R.id.button_save_profile);

    // Convert bitmap to RoundedImageDrawable
    RoundedBitmapDrawable roundedBitmapDrawable =
      RoundedBitmapDrawableFactory.create(getResources(), bitmapProfilePicture);
    roundedBitmapDrawable.setCircular(true);
    roundedBitmapDrawable.setAntiAlias(true);

    // Set the views
    imageViewProfilePicture.setImageDrawable(roundedBitmapDrawable);
    editTextLocation.setText(location);
    editTextCompany.setText(company);
    editTextJobTitle.setText(jobTitle);
    editTextEmail.setText(email);
    editTextWebsite.setText(website);
    editTextPersonalMessage.setText(personalMessage);
  }

  private void initEditPictureButton() {
    textViewEditPicture.setOnClickListener(view -> {
      Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
      intent.setType("image/*");
      intent.putExtra("crop", "true");
      intent.putExtra("scale", true);
      intent.putExtra("outputX", 256);
      intent.putExtra("outputY", 256);
      intent.putExtra("aspectX", 1);
      intent.putExtra("aspectY", 1);
      intent.putExtra("return-data", true);
      startActivityForResult(intent, GET_FROM_GALLERY_REQUEST_CODE);
    });
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    // Get the selected image, crop it and then set it as the image view
    if (requestCode == GET_FROM_GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
      Bundle extras = data.getExtras();
      Bitmap bitmap = extras.getParcelable("data");

      // Setting radius to crop the bitmap
      RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
      roundedBitmapDrawable.setCircular(true);
      roundedBitmapDrawable.setAntiAlias(true);

      // Display the rounded bitmap image in the image view
      imageViewProfilePicture.setImageDrawable(roundedBitmapDrawable);
    }
  }

  private void initSaveProfileButton() {
    buttonSaveProfile.setOnClickListener(view -> {
      // Get bitmap from image view. Then, convert the bitmap into a base64 string
      RoundedBitmapDrawable drawable = (RoundedBitmapDrawable) imageViewProfilePicture.getDrawable();
      bitmapProfilePicture = drawable.getBitmap();
      String bitmapString = Base64EncoderDecoder.toBase64String(bitmapProfilePicture);

      // Get the rest of the data from the edit text
      location = editTextLocation.getText().toString();
      company = editTextCompany.getText().toString();
      jobTitle = editTextJobTitle.getText().toString();
      email = editTextEmail.getText().toString();
      website = editTextWebsite.getText().toString();
      personalMessage = editTextPersonalMessage.getText().toString();

      // Ensure that none of the input fields are blank
      if (inputValidator.isInvalidInput(location)
        || inputValidator.isInvalidInput(company)
        || inputValidator.isInvalidInput(jobTitle)
        || inputValidator.isInvalidInput(email)
        || inputValidator.isInvalidInput(website)
        || inputValidator.isInvalidInput(personalMessage)) {
        Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
        return;
      }

      // Save the profile on the server and exit the activity
      userProfileAPIService.updateUserProfile(bitmapString, location, company, jobTitle, email, website,
        personalMessage).enqueue(new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
          if (response.isSuccessful()) {
            Intent returnIntent = new Intent();
            setResult(RESULT_OK, returnIntent);
            finish();
          }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
          Log.d(TAG, "onFailure: " + t.getMessage());
        }
      });
    });
  }

  @Override
  public boolean onSupportNavigateUp() {
    finish();
    return true;
  }
}
