package com.thg.accelerator.THGTalk.event;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class CommentJSON {
  private UUID commentID;
  @NotNull
  private UUID userID;
  @NotNull
  private UUID postID;
  @NotNull
  private String comment;
  @NotNull
  private Date date;

  public CommentJSON(UUID commentID, UUID postID, UUID userID, String comment, Date date) {
    this.commentID = commentID;
    this.postID = postID;
    this.userID = userID;
    this.comment = comment;
    this.date = date;
  }

  public CommentJSON() {
  }

  public UUID getCommentID() {
    return commentID;
  }

  public UUID getUserID() {
    return userID;
  }

  public UUID getPostID() {
    return postID;
  }

  public String getComment() {
    return comment;
  }

  public Date getDate() {
    return date;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommentJSON that = (CommentJSON) o;
    return Objects.equals(commentID, that.commentID) &&
        Objects.equals(userID, that.userID) &&
        Objects.equals(postID, that.postID) &&
        Objects.equals(comment, that.comment) &&
        Objects.equals(date, that.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(commentID, userID, postID, comment, date);
  }

  @Override
  public String toString() {
    return "CommentJSON{" +
        "commentID=" + commentID +
        ", userID=" + userID +
        ", postID=" + postID +
        ", comment='" + comment + '\'' +
        ", date=" + date +
        '}';
  }
}
