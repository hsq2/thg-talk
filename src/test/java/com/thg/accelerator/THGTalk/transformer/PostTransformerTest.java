package com.thg.accelerator.THGTalk.transformer;

import com.thg.accelerator.THGTalk.event.PostJSON;
import com.thg.accelerator.THGTalk.model.Post;
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
class PostTransformerTest {

  @Autowired
  PostTransformer transformer;

  UUID UUIDPost = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc41");
  UUID UUIDUser = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39");
  UUID UUIDSub = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc40");
  UUID UUIDNew = UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc40");
  Date date = convertStringToDate("2017-03-31 9:30:20");

  Post post = new Post(UUIDPost, UUIDUser, UUIDSub, "First post", "Nice", date);
  PostJSON postJSON = new PostJSON(UUIDPost, UUIDUser, UUIDSub, "First post", "Nice", date);

  PostTransformerTest() throws ParseException {
  }

  private Date convertStringToDate(String stringDate) throws ParseException {
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
    Date date = sdfDate.parse(stringDate);
    return date;
  }

  @Test
  void toJson() {
    PostJSON newPostJSON = transformer.toJson(post);
    assertEquals(newPostJSON, postJSON);
  }

  @Test
  void toJsonList() {
    Post newPost = new Post(UUIDNew, UUIDUser, UUIDSub, "First post", "Nice", date);
    PostJSON newPostJSON = new PostJSON(UUIDNew, UUIDUser, UUIDSub, "First post", "Nice", date);
    List<Post> postList = Arrays.asList(post, newPost);
    List<PostJSON> expectedJSONList = Arrays.asList(postJSON, newPostJSON);

    List<PostJSON> actualJSONList = transformer.toJsonList(postList);

    assertEquals(actualJSONList, expectedJSONList);
  }

  @Test
  void fromJson() {
    Post newPost = transformer.fromJson(postJSON);
    assertEquals(newPost, post);
  }

  @Test
  void fromJSONWithUUID() {
    Post newPost = transformer.fromJSONWithUUID(UUIDNew, postJSON);
    Post expectedPost = new Post(UUIDNew, UUIDUser, UUIDSub, "First post", "Nice", date);
    assertEquals(newPost, expectedPost);
  }
}