package com.thg.accelerator.THGTalk.transformer;

import com.thg.accelerator.THGTalk.event.CommentJSON;
import com.thg.accelerator.THGTalk.model.Comment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CommentTransformer implements ICRUDTransformer<Comment, CommentJSON> {
  public CommentJSON toJson(Comment model) {
    return new CommentJSON(model.getCommentID(), model.getPostID(), model.getUserID(),
        model.getComment(), model.getDate());
  }

  public List<CommentJSON> toJsonList(List<Comment> modelList) {
    List<CommentJSON> jsonList = new ArrayList<>();
    for (int i = 0; i < modelList.size(); i++) {
      jsonList.add(toJson(modelList.get(i)));

    }
    return jsonList;
  }

  public Comment fromJson(CommentJSON jsonModel) {
    return new Comment(jsonModel.getCommentID(), jsonModel.getPostID(), jsonModel.getUserID(),
        jsonModel.getComment(), jsonModel.getDate());
  }

  public Comment fromJSONWithUUID(UUID uuid, CommentJSON jsonModel) {
    return new Comment(uuid, jsonModel.getPostID(), jsonModel.getUserID(),
        jsonModel.getComment(), jsonModel.getDate());
  }

}
