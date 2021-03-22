package com.thg.accelerator.THGTalk.transformer;

import com.thg.accelerator.THGTalk.event.PostVoteJSON;
import com.thg.accelerator.THGTalk.model.PostVote;
import com.thg.accelerator.THGTalk.model.VoteEnum;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class PostVoteTransformerTest {

  @Autowired
  PostVoteTransformer transformer;

  UUID UUIDPost = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc41");
  UUID UUIDUser = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39");
  UUID UUIDVote = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc43");
  UUID UUIDNewPost = UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc39");
  UUID UUIDNew = UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc40");

  PostVote postVote = new PostVote(UUIDVote, VoteEnum.UPVOTE, UUIDUser, UUIDPost);
  PostVoteJSON postVoteJSON = new PostVoteJSON(UUIDVote, UUIDUser, UUIDPost, VoteEnum.UPVOTE);

  @Test
  void toJson() {
    PostVoteJSON newPostVoteJSON = transformer.toJson(postVote);
    assertEquals(newPostVoteJSON, postVoteJSON);
  }

  @Test
  void toJsonList() {
    PostVote postVote2 = new PostVote(UUIDNew, VoteEnum.UPVOTE, UUIDUser, UUIDNewPost);
    PostVoteJSON postVoteJSON2 = new PostVoteJSON(UUIDNew, UUIDUser, UUIDNewPost, VoteEnum.UPVOTE);

    List<PostVote> postVoteList = Arrays.asList(postVote, postVote2);
    List<PostVoteJSON> postVoteJSONList = transformer.toJsonList(postVoteList);

    assertEquals(postVoteJSONList.size(), 2);
    assertEquals(postVoteJSONList.get(0), postVoteJSON);
    assertEquals(postVoteJSONList.get(1), postVoteJSON2);
  }

  @Test
  void fromJson() {
    PostVote newPostVote = transformer.fromJson(postVoteJSON);
    assertEquals(newPostVote.getVoteID(), postVote.getVoteID());
    assertEquals(newPostVote.getVoteType(), postVote.getVoteType());
    assertEquals(newPostVote.getPostID(), postVote.getPostID());
    assertEquals(newPostVote.getUserID(), postVote.getUserID());
  }

  @Test
  void fromJsonWithUUID() {
    PostVote newPostVote = transformer.fromJSONWithUUID(UUIDNew, postVoteJSON);
    assertEquals(newPostVote.getVoteID(), UUIDNew);
    assertEquals(newPostVote.getVoteType(), postVote.getVoteType());
    assertEquals(newPostVote.getPostID(), postVote.getPostID());
    assertEquals(newPostVote.getUserID(), postVote.getUserID());
  }

}