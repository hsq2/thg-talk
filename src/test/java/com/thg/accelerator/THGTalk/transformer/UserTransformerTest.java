package com.thg.accelerator.THGTalk.transformer;

import com.thg.accelerator.THGTalk.event.UserJSON;
import com.thg.accelerator.THGTalk.model.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class UserTransformerTest {

  @Autowired
  UserTransformer transformer;

  UUID UUIDUser = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39");
  UUID UUIDNew = UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc40");
  Date date = convertStringToDate("2017-03-31 9:30:20");

  User user = new User(UUIDUser, "user1", date);
  UserJSON userJSON = new UserJSON(UUIDUser, "user1", date);

  UserTransformerTest() throws ParseException {
  }

  private Date convertStringToDate(String stringDate) throws ParseException {
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
    Date date = sdfDate.parse(stringDate);
    return date;
  }

  @Test
  void toJson() {
    UserJSON newUserJSON = transformer.toJson(user);
    assertEquals(newUserJSON, userJSON);
  }

  @Test
  void toJsonList() {
    User newUser = new User(UUIDNew, "user2", date);
    UserJSON newUserJSON = new UserJSON(UUIDNew, "user2", date);

    List<User> userList = Arrays.asList(user, newUser);
    List<UserJSON> userJSONList = Arrays.asList(userJSON, newUserJSON);

    List<UserJSON> actualJsonList = transformer.toJsonList(userList);

    assertEquals(userJSONList, actualJsonList);
  }

  @Test
  void fromJson() {
    User newUser = transformer.fromJson(userJSON);
    assertEquals(newUser, user);
  }

  @Test
  void fromJSONWithUUID() {
    User newUser = transformer.fromJSONWithUUID(UUIDNew, userJSON);
    User expectedUser = new User(UUIDNew, "user1", date);
    assertEquals(newUser, expectedUser);
  }
}