package com.thg.accelerator.THGTalk.event;

import com.thg.accelerator.THGTalk.model.VoteEnum;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

public class PostVoteJSON {
  private final UUID voteID;
  @NotNull
  private final UUID userID;
  @NotNull
  private final UUID postID;
  @NotNull
  private final VoteEnum voteType;

  public PostVoteJSON(UUID voteID, @NotNull UUID userID,
                      @NotNull UUID postID,
                      @NotNull VoteEnum voteType) {
    this.voteID = voteID;
    this.userID = userID;
    this.postID = postID;
    this.voteType = voteType;
  }

  public UUID getVoteID() {
    return voteID;
  }

  public UUID getUserID() {
    return userID;
  }

  public UUID getPostID() {
    return postID;
  }

  public VoteEnum getVoteType() {
    return voteType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostVoteJSON that = (PostVoteJSON) o;
    return Objects.equals(voteID, that.voteID) &&
        Objects.equals(userID, that.userID) &&
        Objects.equals(postID, that.postID) &&
        voteType == that.voteType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(voteID, userID, postID, voteType);
  }

  @Override
  public String toString() {
    return "PostVoteJSON{" +
        "voteID=" + voteID +
        ", userID=" + userID +
        ", postID=" + postID +
        ", voteType=" + voteType +
        '}';
  }
}
