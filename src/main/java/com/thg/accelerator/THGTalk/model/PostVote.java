package com.thg.accelerator.THGTalk.model;

import java.util.UUID;

public class PostVote extends Vote {

  private final UUID postID;

  public PostVote(UUID voteID, VoteEnum voteType, UUID userID, UUID postID) {
    super(voteID, voteType, userID);
    this.postID = postID;
  }

  public UUID getPostID() {
    return postID;
  }
}
