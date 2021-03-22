package com.thg.accelerator.THGTalk.repository;

import com.thg.accelerator.THGTalk.exceptions.RepositoryException;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class CommentRepositoryTest {

  @Autowired
  CommentRepository repository;

  UUID UUIDComment = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc42");
  UUID UUIDPost = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc41");
  UUID UUIDNewPost = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc51");
  UUID UUIDUser = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39");
  UUID UUIDNewUser = UUID.fromString("71614667-d279-11e7-a5ac-f941ac8dfc39");
  UUID UUIDDoesntExist = UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc39");
  UUID UUIDNew = UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc40");
  UUID UUIDToDelete = UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc41");
  Date date = convertStringToDate("2017-03-31 9:30:20");

  Comment comment = new Comment(UUIDComment, UUIDPost, UUIDUser, "Nice",
      convertStringToDate("2017-03-31 9:30:20"));
  //convertStringToDate("2017-03-31 9:30:20")
  //Mon Feb 15 17:27:36 GMT 2021

  CommentRepositoryTest() throws ParseException {
  }

  private Date convertStringToDate(String stringDate) throws ParseException {
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
    Date date = sdfDate.parse(stringDate);
    return date;
  }

  @Test
  void getCommentByID() throws RepositoryException {
    Optional<Comment> retrievedComment = repository.getModelByID(UUIDComment);
    assertEquals(retrievedComment.get().getCommentID(), Optional.of(comment).get().getCommentID());
    assertEquals(retrievedComment.get().getPostID(), Optional.of(comment).get().getPostID());
    assertEquals(retrievedComment.get().getUserID(), Optional.of(comment).get().getUserID());
    assertEquals(retrievedComment.get().getComment(), Optional.of(comment).get().getComment());
    assertEquals(retrievedComment.get().getDate().toString(), "2017-03-31 09:30:20.0");
//    assertEquals(retrievedComment.get().getDate(), Optional.of(comment).get().getDate());
  }

  @Test
  void deleteCommentByID() throws RepositoryException, ParseException {
    Comment newComment = new Comment(UUIDToDelete, UUIDPost, UUIDUser, "Nice", date);
    assertTrue(repository.save(newComment));
    assertTrue(repository.deleteByID(UUIDToDelete));
    assertFalse(repository.getAllModels().contains(newComment));
  }

  @Test
  void getAllComments() throws RepositoryException {
    List<Comment> comments = repository.getAllModels();
    assertTrue(1 <= comments.size());
    assertEquals(comments.get(0).getCommentID(), Optional.of(comment).get().getCommentID());
    assertEquals(comments.get(0).getPostID(), Optional.of(comment).get().getPostID());
    assertEquals(comments.get(0).getUserID(), Optional.of(comment).get().getUserID());
    assertEquals(comments.get(0).getComment(), Optional.of(comment).get().getComment());
    assertEquals(comments.get(0).getDate().toString(), "2017-03-31 09:30:20.0");
  }

  @Test
  void deleteCommentByIDDoesntExist() throws RepositoryException {
    assertFalse(repository.deleteByID(UUIDDoesntExist));
  }

  @Test
  void addCommentAlreadyExists() throws RepositoryException {
    assertFalse(repository.save(comment));
  }

  @Test
  void addComment() throws RepositoryException, ParseException {
    Comment newComment =
        new Comment(UUIDNew, UUIDPost, UUIDUser, "Nice", date);
    assertTrue(repository.save(newComment));
    Optional<Comment> retrievedComment = repository.getModelByID(UUIDNew);
    assertEquals(retrievedComment.get().getCommentID(),
        Optional.of(newComment).get().getCommentID());
    assertEquals(retrievedComment.get().getPostID(), Optional.of(newComment).get().getPostID());
    assertEquals(retrievedComment.get().getUserID(), Optional.of(newComment).get().getUserID());
    assertEquals(retrievedComment.get().getComment(), Optional.of(newComment).get().getComment());
    assertEquals(retrievedComment.get().getDate().toString(), "2017-03-31 09:30:20.0");
  }

  @Test
  void updateComment() throws RepositoryException, ParseException {
    Comment newComment = new Comment(UUIDToDelete, UUIDPost, UUIDUser, "Nice", date);
    assertTrue(repository.save(newComment));
    assertEquals(repository.getModelByID(UUIDToDelete).get().getComment(), "Nice");
    Comment updatedComment = new Comment(UUIDToDelete, UUIDPost, UUIDUser, "Updated Nice",
        date);
    assertTrue(repository.update(updatedComment));
    assertEquals(repository.getModelByID(UUIDToDelete).get().getComment(), "Updated Nice");
    repository.deleteByID(UUIDToDelete);
    assertFalse(repository.getModelByID(UUIDToDelete).isPresent());
  }

  @Test
  void updateCommentDoesntExist() throws RepositoryException, ParseException {
    Comment newComment = new Comment(UUIDDoesntExist, UUIDPost, UUIDUser, "Nice", date);
    assertFalse(repository.update(newComment));
  }

  @Test
  void getCommentsByPostID() throws RepositoryException {
    Comment newComment = new Comment(UUIDDoesntExist, UUIDPost, UUIDUser, "Nice", date);
    Comment newComment2 = new Comment(UUIDToDelete, UUIDNewPost, UUIDUser, "cool", date);
    repository.save(newComment);
    repository.save(newComment2);
    List<Comment> commentsByPostID = repository.getCommentsByPostID(UUIDNewPost);
    assertEquals(commentsByPostID.size(), 1);
    List<Comment> commentsByPostID1 = repository.getCommentsByPostID(UUIDPost);
    assertTrue(commentsByPostID1.size() >= 2);
    repository.deleteByID(UUIDDoesntExist);
    repository.deleteByID(UUIDToDelete);
  }

  @Test
  void getCommentsByPostIDDoesntExist() throws RepositoryException {
    List<Comment> commentsByPostID = repository.getCommentsByPostID(UUIDNewPost);
    assertEquals(commentsByPostID.size(), 0);
  }

  @Test
  void getCommentsByUserID() throws RepositoryException {
    Comment newComment = new Comment(UUIDDoesntExist, UUIDPost, UUIDUser, "Nice", date);
    Comment newComment2 = new Comment(UUIDToDelete, UUIDNewPost, UUIDNewUser, "cool", date);
    repository.save(newComment);
    repository.save(newComment2);
    List<Comment> commentsByUserID = repository.getCommentsByUserID(UUIDNewUser);
    assertEquals(commentsByUserID.size(), 1);
    List<Comment> commentsByUserID1 = repository.getCommentsByUserID(UUIDUser);
    assertTrue(commentsByUserID1.size() >= 2);
    repository.deleteByID(UUIDDoesntExist);
    repository.deleteByID(UUIDToDelete);
  }

  @Test
  void getCommentsByUserIDDoesntExist() throws RepositoryException {
    List<Comment> commentsByUserID = repository.getCommentsByUserID(UUIDComment);
    assertEquals(commentsByUserID.size(), 0);
  }
}