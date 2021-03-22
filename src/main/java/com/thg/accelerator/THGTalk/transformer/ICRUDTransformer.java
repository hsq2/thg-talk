package com.thg.accelerator.THGTalk.transformer;

import com.thg.accelerator.THGTalk.event.CommentJSON;
import com.thg.accelerator.THGTalk.model.Comment;

import java.util.List;
import java.util.UUID;

public interface ICRUDTransformer<M, J> {
  public J toJson(M model);
  public List<J> toJsonList(List<M> modelList);
  public M fromJson(J jsonModel);
  public M fromJSONWithUUID(UUID uuid, J jsonModel);
}
