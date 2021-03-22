package com.thg.accelerator.THGTalk.transformer;

import com.thg.accelerator.THGTalk.event.PostJSON;
import com.thg.accelerator.THGTalk.model.Post;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class PostTransformer implements ICRUDTransformer<Post, PostJSON> {
  @Override
  public PostJSON toJson(Post model) {
    return new PostJSON(model.getPostID(), model.getUserID(), model.getSubID(), model.getPostTitle(), model.getPost(), model.getDateCreated());
  }

  @Override
  public List<PostJSON> toJsonList(List<Post> modelList) {
    List<PostJSON> jsonList = new ArrayList<>();
    for (int i = 0; i < modelList.size(); i++) {
      jsonList.add(toJson(modelList.get(i)));

    }
    return jsonList;
  }

  @Override
  public Post fromJson(PostJSON jsonModel) {
    return new Post(jsonModel.getPostID(), jsonModel.getUserID(), jsonModel.getSubID(), jsonModel.getPostTitle(), jsonModel.getPost(), jsonModel.getDateCreated());
  }

  @Override
  public Post fromJSONWithUUID(UUID uuid, PostJSON jsonModel) {
    return new Post(uuid, jsonModel.getUserID(), jsonModel.getSubID(), jsonModel.getPostTitle(), jsonModel.getPost(), jsonModel.getDateCreated());
  }
}
