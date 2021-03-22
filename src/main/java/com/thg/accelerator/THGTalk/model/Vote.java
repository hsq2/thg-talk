package com.thg.accelerator.THGTalk.model;

import java.util.Objects;
import java.util.UUID;

public abstract class Vote {
  private UUID voteID;
  private VoteEnum voteType;
  private UUID userID;

  public Vote(UUID voteID, VoteEnum voteType, UUID userID) {
    this.voteID = voteID;
    this.voteType = voteType;
    this.userID = userID;
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

  @Override
  public String toString() {
    return "Vote{" +
        "voteID=" + voteID +
        ", voteType=" + voteType +
        ", userID=" + userID +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Vote vote = (Vote) o;
    return Objects.equals(voteID, vote.voteID) &&
        voteType == vote.voteType &&
        Objects.equals(userID, vote.userID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(voteID, voteType, userID);
  }
}
