package com.thg.accelerator.THGTalk.event;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class PostJSON {
  private UUID postID;
  @NotNull
  private UUID userID;
  @NotNull
  private UUID subID;
  @NotNull
  private String postTitle;
  @NotNull
  private String post;
  @NotNull
  private Date dateCreated;

  public PostJSON(UUID postID, UUID userID, UUID subID, String postTitle, String post,
                  Date dateCreated) {
    this.postID = postID;
    this.userID = userID;
    this.subID = subID;
    this.postTitle = postTitle;
    this.post = post;
    this.dateCreated = dateCreated;
  }

  public UUID getPostID() {
    return postID;
  }

  public UUID getUserID() {
    return userID;
  }

  public UUID getSubID() {
    return subID;
  }

  public String getPostTitle() {
    return postTitle;
  }

  public String getPost() {
    return post;
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
    PostJSON postJSON = (PostJSON) o;
    return Objects.equals(postID, postJSON.postID) &&
        Objects.equals(userID, postJSON.userID) &&
        Objects.equals(subID, postJSON.subID) &&
        Objects.equals(postTitle, postJSON.postTitle) &&
        Objects.equals(post, postJSON.post) &&
        Objects.equals(dateCreated, postJSON.dateCreated);
  }

  @Override
  public int hashCode() {
    return Objects.hash(postID, userID, subID, postTitle, post, dateCreated);
  }

  @Override
  public String toString() {
    return "PostJSON{" +
        "postID=" + postID +
        ", userID=" + userID +
        ", subID=" + subID +
        ", postTitle='" + postTitle + '\'' +
        ", post='" + post + '\'' +
        ", dateCreated=" + dateCreated +
        '}';
  }
}
