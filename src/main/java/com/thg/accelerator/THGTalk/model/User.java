package com.thg.accelerator.THGTalk.model;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class User {

  private final UUID userID;
  private final String userName;
  private final Date dateCreated;

  public User(UUID userID, String userName, Date dateCreated) {
    this.userID = userID;
    this.userName = userName;
    this.dateCreated = dateCreated;
  }

  public UUID getUserID() {
    return userID;
  }

  public String getUserName() {
    return userName;
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
    User user = (User) o;
    return Objects.equals(userID, user.userID) &&
        Objects.equals(userName, user.userName) &&
        Objects.equals(dateCreated, user.dateCreated);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userID, userName, dateCreated);
  }

  @Override
  public String toString() {
    return "User{" +
        "userID=" + userID +
        ", userName=" + userName +
        ", dateCreated=" + dateCreated +
        '}';
  }
}
