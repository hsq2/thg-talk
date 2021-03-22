package com.thg.accelerator.THGTalk.transformer;

import com.thg.accelerator.THGTalk.event.CommentJSON;
import com.thg.accelerator.THGTalk.model.Comment;
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
class CommentTransformerTest {

  @Autowired
  CommentTransformer transformer;

  UUID UUIDComment = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc41");
  UUID UUIDPost = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc41");
  UUID UUIDUser = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39");
  UUID UUIDNew = UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc40");
  Date date = convertStringToDate("2017-03-31 9:30:20");

  Comment comment = new Comment(UUIDComment, UUIDPost, UUIDUser, "comment", date);
  CommentJSON commentJSON = new CommentJSON(UUIDComment, UUIDPost, UUIDUser, "comment", date);

  CommentTransformerTest() throws ParseException {
  }

  private Date convertStringToDate(String stringDate) throws ParseException {
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
    Date date = sdfDate.parse(stringDate);
    return date;
  }

  @Test
  void toJson() {
    CommentJSON newCommentJSON = transformer.toJson(comment);
    assertEquals(newCommentJSON, commentJSON);
  }

  @Test
  void toJsonList() {
    Comment comment2 = new Comment(UUIDNew, UUIDPost, UUIDUser, "comment", date);
    CommentJSON commentJSON2 = new CommentJSON(UUIDNew, UUIDPost, UUIDUser, "comment", date);

    List<Comment> commentVoteList = Arrays.asList(comment, comment2);
    List<CommentJSON> commentVoteJSONList = transformer.toJsonList(commentVoteList);

    assertEquals(commentVoteJSONList.size(), 2);
    assertEquals(commentVoteJSONList.get(0), commentJSON);
    assertEquals(commentVoteJSONList.get(1), commentJSON2);
  }

  @Test
  void fromJson() {
    Comment newComment = transformer.fromJson(commentJSON);
    assertEquals(newComment, comment);
  }

  @Test
  void fromJsonWithUUID() {
    Comment newComment = transformer.fromJSONWithUUID(UUIDNew, commentJSON);
    assertEquals(newComment.getComment(), comment.getComment());
    assertEquals(newComment.getDate(), comment.getDate());
    assertEquals(newComment.getCommentID(), UUIDNew);
    assertEquals(newComment.getUserID(), comment.getUserID());
    assertEquals(newComment.getPostID(), comment.getPostID());
  }

}