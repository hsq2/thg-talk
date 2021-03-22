package com.thg.accelerator.THGTalk.model;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class SubTalk {
  private final UUID subTalkID;
  private final UUID userID;
  private final String subTalkName;
  private final String subTalkDescription;
  private final String imageURL;
  private final Date dateCreated;

  public SubTalk(UUID subTalkID, UUID userID, String subTalkName, String subTalkDescription,
                 String imageURL, Date dateCreated) {
    this.subTalkID = subTalkID;
    this.userID = userID;
    this.subTalkName = subTalkName;
    this.subTalkDescription = subTalkDescription;
    this.imageURL = imageURL;
    this.dateCreated = dateCreated;
  }

  public UUID getSubTalkID() {
    return subTalkID;
  }

  public UUID getUserID() {
    return userID;
  }

  public String getSubTalkName() {
    return subTalkName;
  }

  public String getSubTalkDescription() {
    return subTalkDescription;
  }

  public String getImageURL() {
    return imageURL;
  }

  public Date getDateCreated() {
    return dateCreated;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SubTalk subTalk = (SubTalk) o;
    return Objects.equals(subTalkID, subTalk.subTalkID) &&
        Objects.equals(userID, subTalk.userID) &&
        Objects.equals(subTalkName, subTalk.subTalkName) &&
        Objects.equals(subTalkDescription, subTalk.subTalkDescription) &&
        Objects.equals(imageURL, subTalk.imageURL) &&
        Objects.equals(dateCreated, subTalk.dateCreated);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subTalkID, userID, subTalkName, subTalkDescription, imageURL, dateCreated);
  }

  @Override
  public String toString() {
    return "SubTalk{" +
        "subTalkID=" + subTalkID +
        ", userID=" + userID +
        ", subTalkName='" + subTalkName + '\'' +
        ", subTalkDescription='" + subTalkDescription + '\'' +
        ", imageURL='" + imageURL + '\'' +
        ", dateCreated=" + dateCreated +
        '}';
  }
}
