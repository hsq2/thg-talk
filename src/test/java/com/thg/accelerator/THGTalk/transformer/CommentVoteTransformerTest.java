package com.thg.accelerator.THGTalk.transformer;

import com.thg.accelerator.THGTalk.event.CommentVoteJSON;
import com.thg.accelerator.THGTalk.model.CommentVote;
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
class CommentVoteTransformerTest {

  @Autowired
  CommentVoteTransformer transformer;

  UUID UUIDComment = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc41");
  UUID UUIDUser = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39");
  UUID UUIDVote = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc43");
  UUID UUIDNewComment = UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc39");
  UUID UUIDNew = UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc40");

  CommentVote commentVote = new CommentVote(UUIDVote, VoteEnum.UPVOTE, UUIDUser, UUIDComment);
  CommentVoteJSON commentVoteJSON =
      new CommentVoteJSON(UUIDVote, VoteEnum.UPVOTE, UUIDUser, UUIDComment);

  @Test
  void toJson() {
    CommentVoteJSON newCommentVoteJSON = transformer.toJson(commentVote);
    assertEquals(newCommentVoteJSON, commentVoteJSON);
  }

  @Test
  void toJsonList() {
    CommentVote commentVote2 = new CommentVote(UUIDNew, VoteEnum.UPVOTE, UUIDUser, UUIDNewComment);
    CommentVoteJSON commentVoteJSON2 = new CommentVoteJSON(UUIDNew, VoteEnum.UPVOTE, UUIDUser,
        UUIDNewComment);

    List<CommentVote> commentVoteList = Arrays.asList(commentVote, commentVote2);
    List<CommentVoteJSON> commentVoteJSONList = transformer.toJsonList(commentVoteList);

    assertEquals(commentVoteJSONList.size(), 2);
    assertEquals(commentVoteJSONList.get(0), commentVoteJSON);
    assertEquals(commentVoteJSONList.get(1), commentVoteJSON2);
  }

  @Test
  void fromJson() {
    CommentVote newCommentVote = transformer.fromJson(commentVoteJSON);
    assertEquals(newCommentVote.getVoteID(), commentVote.getVoteID());
    assertEquals(newCommentVote.getVoteType(), commentVote.getVoteType());
    assertEquals(newCommentVote.getCommentID(), commentVote.getCommentID());
    assertEquals(newCommentVote.getUserID(), commentVote.getUserID());
  }

  @Test
  void fromJsonWithUUID() {
    CommentVote newCommentVote = transformer.fromJSONWithUUID(UUIDNew, commentVoteJSON);
    assertEquals(newCommentVote.getVoteID(), UUIDNew);
    assertEquals(newCommentVote.getVoteType(), commentVote.getVoteType());
    assertEquals(newCommentVote.getCommentID(), commentVote.getCommentID());
    assertEquals(newCommentVote.getUserID(), commentVote.getUserID());
  }

}