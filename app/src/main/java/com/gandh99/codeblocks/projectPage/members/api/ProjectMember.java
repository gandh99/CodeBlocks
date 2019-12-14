package com.gandh99.codeblocks.projectPage.members.api;

import com.gandh99.codeblocks.projectPage.members.Rank;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("all")
public class ProjectMember {
  @SerializedName("pk")
  private final int pk;

  @SerializedName("username")
  private final String username;

  @SerializedName("profilePicture")
  private final String profilePicture;

  @SerializedName("rank")
  private final String rank;

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

  public ProjectMember(int pk, String username, String profilePicture, String rank, String location,
                       String company, String jobTitle, String email, String website,
                       String personalMessage) {
    this.pk = pk;
    this.username = username;
    this.profilePicture = profilePicture;
    this.rank = rank;
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

  public String getUsername() {
    return username;
  }

  public String getProfilePicture() {
    return profilePicture;
  }

  public String getRank() {
    return rank;
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

  public boolean isAdmin() {
    String adminName = Rank.ADMIN.getRankName();
    return rank.toUpperCase().equals(adminName);
  }
}
