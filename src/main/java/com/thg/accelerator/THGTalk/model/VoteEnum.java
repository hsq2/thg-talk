package com.thg.accelerator.THGTalk.model;

public enum VoteEnum {
  UPVOTE("UPVOTE"),
  DOWNVOTE("DOWNVOTE");

  private String voteType;

  VoteEnum(String voteType) {
    this.voteType = voteType;
  }

  public String getType() {
    return voteType;
  }


}
