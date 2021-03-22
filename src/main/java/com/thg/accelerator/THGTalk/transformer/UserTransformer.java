package com.thg.accelerator.THGTalk.transformer;

import com.thg.accelerator.THGTalk.event.UserJSON;
import com.thg.accelerator.THGTalk.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class UserTransformer implements ICRUDTransformer<User, UserJSON> {
  @Override
  public UserJSON toJson(User model) {
    return new UserJSON(model.getUserID(), model.getUserName(), model.getDateCreated());
  }

  @Override
  public List<UserJSON> toJsonList(List<User> modelList) {
    List<UserJSON> jsonList = new ArrayList<>();
    for (int i = 0; i < modelList.size(); i++) {
      jsonList.add(toJson(modelList.get(i)));

    }
    return jsonList;
  }

  @Override
  public User fromJson(UserJSON jsonModel) {
    return new User(jsonModel.getUserID(), jsonModel.getUserName(), jsonModel.getDateCreated());
  }

  @Override
  public User fromJSONWithUUID(UUID uuid, UserJSON jsonModel) {
    return new User(uuid, jsonModel.getUserName(), jsonModel.getDateCreated());
  }
}
