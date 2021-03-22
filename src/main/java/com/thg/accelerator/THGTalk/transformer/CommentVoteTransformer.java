package com.thg.accelerator.THGTalk.transformer;

import com.thg.accelerator.THGTalk.controller.ICRUDController;
import com.thg.accelerator.THGTalk.event.CommentJSON;
import com.thg.accelerator.THGTalk.event.CommentVoteJSON;
import com.thg.accelerator.THGTalk.model.CommentVote;
import com.thg.accelerator.THGTalk.model.VoteEnum;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CommentVoteTransformer implements ICRUDTransformer<CommentVote, CommentVoteJSON> {

  @Override
  public CommentVoteJSON toJson(CommentVote model) {
    return new CommentVoteJSON(model.getVoteID(), model.getVoteType(), model.getUserID(), model.getCommentID());
  }

  @Override
  public List<CommentVoteJSON> toJsonList(List<CommentVote> modelList) {
    List<CommentVoteJSON> jsonList = new ArrayList<>();
    for (int i = 0; i < modelList.size(); i++) {
      jsonList.add(toJson(modelList.get(i)));

    }
    return jsonList;
  }

  @Override
  public CommentVote fromJson(CommentVoteJSON jsonModel) {
    return new CommentVote(jsonModel.getVoteID(), jsonModel.getVoteType(), jsonModel.getUserID(), jsonModel.getCommentID());
  }

  @Override
  public CommentVote fromJSONWithUUID(UUID uuid, CommentVoteJSON jsonModel) {
    return new CommentVote(uuid, jsonModel.getVoteType(), jsonModel.getUserID(), jsonModel.getCommentID());
  }
}
