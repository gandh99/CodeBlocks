package com.gandh99.codeblocks.homePage.userProfile.api;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("all")
public class UserProfile {
  @SerializedName("pk")
  private final int pk;

  @SerializedName("location")
  private final String location;

  @SerializedName("company")
  private final String company;

  @SerializedName("jobTitle")
  private final String jobTitle;

  @SerializedName("email")
  private final String email;

  @SerializedName("website")
  private final String website;

  @SerializedName("personalMessage")
  private final String personalMessage;

  public UserProfile(int pk, String location, String company, String jobTitle, String email,
                     String website, String personalMessage) {
    this.pk = pk;
    this.location = location;
    this.company = company;
    this.jobTitle = jobTitle;
    this.email = email;
    this.website = website;
    this.personalMessage = personalMessage;
  }

  public int getPk() {
    return pk;
  }

  public String getLocation() {
    return location;
  }

  public String getCompany() {
    return company;
  }

  public String getJobTitle() {
    return jobTitle;
  }

  public String getEmail() {
    return email;
  }

  public String getWebsite() {
    return website;
  }

  public String getPersonalMessage() {
    return personalMessage;
  }
}
