package com.thg.accelerator.THGTalk.event;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class UserJSON {
  private UUID userID;
  @NotNull
  private String userName;
  @NotNull
  private Date dateCreated;

  public UserJSON(UUID userID, String userName, Date dateCreated) {
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
    UserJSON userJSON = (UserJSON) o;
    return Objects.equals(userID, userJSON.userID) &&
        Objects.equals(userName, userJSON.userName) &&
        Objects.equals(dateCreated, userJSON.dateCreated);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userID, userName, dateCreated);
  }

  @Override
  public String toString() {
    return "UserJSON{" +
        "userID=" + userID +
        ", userName='" + userName + '\'' +
        ", dateCreated=" + dateCreated +
        '}';
  }
}
