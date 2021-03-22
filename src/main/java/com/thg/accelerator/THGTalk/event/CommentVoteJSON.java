package com.thg.accelerator.THGTalk.event;

import com.thg.accelerator.THGTalk.model.VoteEnum;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

public class CommentVoteJSON {
  private UUID voteID;
  @NotNull
  private VoteEnum voteType;
  @NotNull
  private UUID userID;
  @NotNull
  private UUID commentID;

  public CommentVoteJSON(UUID voteID, VoteEnum voteType, UUID userID, UUID commentID) {
    this.voteID = voteID;
    this.voteType = voteType;
    this.userID = userID;
    this.commentID = commentID;
  }

  public UUID getVoteID() {
    return voteID;
  }

  public VoteEnum getVoteType() {
    return voteType;
  }

  public UUID getUserID() {
    return userID;
  }

  public UUID getCommentID() {
    return commentID;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommentVoteJSON that = (CommentVoteJSON) o;
    return Objects.equals(voteID, that.voteID) &&
        voteType == that.voteType &&
        Objects.equals(userID, that.userID) &&
        Objects.equals(commentID, that.commentID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(voteID, voteType, userID, commentID);
  }

  @Override
  public String toString() {
    return "CommentVoteJSON{" +
        "voteID=" + voteID +
        ", voteType=" + voteType +
        ", userID=" + userID +
        ", commentID=" + commentID +
        '}';
  }
}
