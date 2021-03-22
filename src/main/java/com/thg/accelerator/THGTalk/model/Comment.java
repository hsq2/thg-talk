package com.thg.accelerator.THGTalk.model;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Comment {
  private final UUID commentID;
  private final UUID userID;
  private final UUID postID;
  private final String comment;
  private final Date date;

  public Comment(UUID commentID, UUID postID, UUID userID, String comment, Date date) {
    this.commentID = commentID;
    this.postID = postID;
    this.userID = userID;
    this.comment = comment;
    this.date = date;
//    try {
//      SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");//dd/MM/yyyy
//      this.date = sdfDate.parse(date.toString());
//    } catch (ParseException e) {
//      throw new ModelException("Could not convert date");
//    }
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
    Comment comment1 = (Comment) o;
    return Objects.equals(commentID, comment1.commentID) &&
        Objects.equals(userID, comment1.userID) &&
        Objects.equals(postID, comment1.postID) &&
        Objects.equals(comment, comment1.comment) &&
        Objects.equals(date, comment1.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(commentID, userID, postID, comment, date);
  }

  @Override
  public String toString() {
    return "Comment{" +
        "commentID=" + commentID +
        ", userID=" + userID +
        ", postID=" + postID +
        ", comment='" + comment + '\'' +
        ", date=" + date +
        '}';
  }
}
