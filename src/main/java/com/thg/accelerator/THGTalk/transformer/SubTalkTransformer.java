package com.thg.accelerator.THGTalk.transformer;

import com.thg.accelerator.THGTalk.event.SubTalkJSON;
import com.thg.accelerator.THGTalk.model.SubTalk;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class SubTalkTransformer implements ICRUDTransformer<SubTalk, SubTalkJSON> {
  @Override
  public SubTalkJSON toJson(SubTalk model) {
    return new SubTalkJSON(model.getSubTalkID(), model.getUserID(), model.getSubTalkName(),
        model.getSubTalkDescription(), model.getImageURL(), model.getDateCreated());
  }

  @Override
  public List<SubTalkJSON> toJsonList(List<SubTalk> modelList) {
    List<SubTalkJSON> jsonList = new ArrayList<>();
    for (int i = 0; i < modelList.size(); i++) {
      jsonList.add(toJson(modelList.get(i)));

    }
    return jsonList;
  }

  @Override
  public SubTalk fromJson(SubTalkJSON jsonModel) {
    return new SubTalk(jsonModel.getSubTalkID(), jsonModel.getUserID(), jsonModel.getSubTalkName(),
        jsonModel.getSubTalkDescription(), jsonModel.getImageURL(), jsonModel.getDateCreated());
  }

  @Override
  public SubTalk fromJSONWithUUID(UUID uuid, SubTalkJSON jsonModel) {
    return new SubTalk(uuid, jsonModel.getUserID(), jsonModel.getSubTalkName(),
        jsonModel.getSubTalkDescription(), jsonModel.getImageURL(), jsonModel.getDateCreated());
  }
}
