package com.gandh99.codeblocks.homePage.userProfile.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.authentication.AuthenticationInterceptor;
import com.gandh99.codeblocks.common.Base64EncoderDecoder;
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
import static com.gandh99.codeblocks.homePage.userProfile.activity.EditUserProfileActivity.PROFILE_PICTURE_INTENT;
import static com.gandh99.codeblocks.homePage.userProfile.activity.EditUserProfileActivity.WEBSITE_INTENT;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment {
  private static final String TAG = "UserProfileFragment";
  private ImageView imageViewProfilePicture;
  private TextView textViewUsername, textViewLocation, textViewCompany, textViewJobTitle, textViewEmail,
    textViewWebsite, textViewPersonalMessage;
  private Button buttonEditProfile;

  @Inject
  UserProfileAPIService userProfileAPIService;

  @Inject
  AuthenticationInterceptor authenticationInterceptor;

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
    imageViewProfilePicture = view.findViewById(R.id.imageView_profile_picture);
    textViewUsername = view.findViewById(R.id.textView_username);
    textViewLocation = view.findViewById(R.id.textView_location);
    textViewCompany = view.findViewById(R.id.textView_company);
    textViewJobTitle = view.findViewById(R.id.textView_job_title);
    textViewEmail = view.findViewById(R.id.textView_email);
    textViewWebsite = view.findViewById(R.id.textView_website);
    textViewPersonalMessage = view.findViewById(R.id.textView_personal_message);
    buttonEditProfile = view.findViewById(R.id.button_edit_profile);

    textViewUsername.setText(authenticationInterceptor.getUsername());

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
          String profilePictureString = userProfile.getProfilePicture();
          String location = userProfile.getLocation().equals("") ? "Location" : userProfile.getLocation();
          String company = userProfile.getCompany().equals("") ? "Company" : userProfile.getCompany();
          String jobTitle = userProfile.getJobTitle().equals("") ? "Job Title" : userProfile.getJobTitle();
          String email = userProfile.getEmail().equals("") ? "Email" : userProfile.getEmail();
          String website = userProfile.getWebsite().equals("") ? "Website" : userProfile.getWebsite();
          String personalMessage = userProfile.getPersonalMessage().equals("")
            ? "Personal Message" : userProfile.getPersonalMessage();

          // Decode the base64 string into a bitmap. Then, convert the bitmap into a RoundedBitmapDrawable
          RoundedBitmapDrawable roundedBitmapDrawable =
            Base64EncoderDecoder.toRoundedBitmapDrawable(getResources(), profilePictureString);

          imageViewProfilePicture.setImageDrawable(roundedBitmapDrawable);
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

      // Convert RoundedBitmapDrawable from ImageView into a bitmap
      Drawable drawable = imageViewProfilePicture.getDrawable();
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        drawable = (DrawableCompat.wrap(drawable)).mutate();
      }
      Bitmap bitmapProfilePicture = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
        drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(bitmapProfilePicture);
      drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
      drawable.draw(canvas);

      // Set the intent and start the activity
      intent.putExtra(PROFILE_PICTURE_INTENT, bitmapProfilePicture);
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
    /*
     * It is possible to simply replace this by making a call to loadProfile()
     * However, that would put an unnecessary workload on the server, which is why it was left to
     * the client to update the text views in the profile page
     * */
    if (requestCode == EDIT_PROFILE_REQUEST_CODE && resultCode == RESULT_OK) {
      loadProfile();
      Toast.makeText(getContext(), "Successfully updated profile", Toast.LENGTH_SHORT).show();
    }
  }

}
