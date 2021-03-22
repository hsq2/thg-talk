package com.thg.accelerator.THGTalk.transformer;

import com.thg.accelerator.THGTalk.event.PostVoteJSON;
import com.thg.accelerator.THGTalk.model.PostVote;
import com.thg.accelerator.THGTalk.model.VoteEnum;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class PostVoteTransformer implements ICRUDTransformer<PostVote, PostVoteJSON> {
  @Override
  public PostVoteJSON toJson(PostVote model) {
    return new PostVoteJSON(model.getVoteID(), model.getUserID(), model.getPostID(),
        model.getVoteType());
  }

  @Override
  public List<PostVoteJSON> toJsonList(List<PostVote> modelList) {
    List<PostVoteJSON> jsonList = new ArrayList<>();
    for (int i = 0; i < modelList.size(); i++) {
      jsonList.add(toJson(modelList.get(i)));
    }
    return jsonList;
  }

  @Override
  public PostVote fromJson(PostVoteJSON jsonModel) {
    return new PostVote(jsonModel.getVoteID(), jsonModel.getVoteType(), jsonModel.getUserID(),
        jsonModel.getPostID());
  }

  @Override
  public PostVote fromJSONWithUUID(UUID uuid, PostVoteJSON jsonModel) {
    return new PostVote(uuid, jsonModel.getVoteType(), jsonModel.getUserID(),
        jsonModel.getPostID());
  }
}
