package com.thg.accelerator.THGTalk.model;

import java.util.UUID;

public class CommentVote extends Vote {
  private final UUID commentID;

  public CommentVote(UUID voteID, VoteEnum voteType, UUID userID, UUID commentID) {
    super(voteID, voteType, userID);
    this.commentID = commentID;
  }

  public UUID getCommentID() {
    return commentID;
  }
}
