package com.thg.accelerator.THGTalk.model;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Post {
  private final UUID postID;
  private final UUID userID;
  private final UUID subID;
  private final String postTitle;
  private final String post;
  private final Date dateCreated;

  public Post(UUID postID, UUID userID, UUID subID, String postTitle, String post,
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
    Post post1 = (Post) o;
    return Objects.equals(postID, post1.postID) &&
        Objects.equals(userID, post1.userID) &&
        Objects.equals(subID, post1.subID) &&
        Objects.equals(postTitle, post1.postTitle) &&
        Objects.equals(post, post1.post) &&
        Objects.equals(dateCreated, post1.dateCreated);
  }

  @Override
  public int hashCode() {
    return Objects.hash(postID, userID, subID, postTitle, post, dateCreated);
  }

  @Override
  public String toString() {
    return "Post{" +
        "postID=" + postID +
        ", userID=" + userID +
        ", subID=" + subID +
        ", postTitle='" + postTitle + '\'' +
        ", post='" + post + '\'' +
        ", dateCreated=" + dateCreated +
        '}';
  }
}
