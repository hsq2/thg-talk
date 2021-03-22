package com.thg.accelerator.THGTalk.event;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class SubTalkJSON {
  private UUID subTalkID;
  @NotNull
  private UUID userID;
  @NotNull
  private String subTalkName;
  @NotNull
  private String subTalkDescription;
  @NotNull
  private String imageURL;
  @NotNull
  private Date dateCreated;

  public SubTalkJSON(UUID subTalkID, UUID userID, String subTalkName,
                     String subTalkDescription, String imageURL, Date dateCreated) {
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
    SubTalkJSON that = (SubTalkJSON) o;
    return Objects.equals(subTalkID, that.subTalkID) &&
        Objects.equals(userID, that.userID) &&
        Objects.equals(subTalkName, that.subTalkName) &&
        Objects.equals(subTalkDescription, that.subTalkDescription) &&
        Objects.equals(imageURL, that.imageURL) &&
        Objects.equals(dateCreated, that.dateCreated);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subTalkID, userID, subTalkName, subTalkDescription, imageURL, dateCreated);
  }

  @Override
  public String toString() {
    return "SubTalkJSON{" +
        "subTalkID=" + subTalkID +
        ", userID=" + userID +
        ", subTalkName='" + subTalkName + '\'' +
        ", subTalkDescription='" + subTalkDescription + '\'' +
        ", imageURL='" + imageURL + '\'' +
        ", dateCreated=" + dateCreated +
        '}';
  }
}
