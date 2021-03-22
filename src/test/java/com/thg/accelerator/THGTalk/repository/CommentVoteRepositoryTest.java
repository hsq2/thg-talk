package com.thg.accelerator.THGTalk.repository;

import com.thg.accelerator.THGTalk.exceptions.RepositoryException;
import com.thg.accelerator.THGTalk.model.CommentVote;
import com.thg.accelerator.THGTalk.model.PostVote;
import com.thg.accelerator.THGTalk.model.VoteEnum;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
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
class CommentVoteRepositoryTest {

  @Autowired
  CommentVoteRepository repository;

  UUID UUIDComment = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc42");
  UUID UUIDNewComment = UUID.fromString("71614667-d279-11e7-a5ac-f941ac8dfc42");
  UUID UUIDUser = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39");
  UUID UUIDVote = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc44");
  UUID UUIDDoesntExist = UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc39");
  UUID UUIDNew = UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc40");
  UUID UUIDToDelete = UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc41");

  CommentVote vote = new CommentVote(UUIDVote, VoteEnum.UPVOTE, UUIDUser, UUIDComment);

  @Test
  void getCommentVoteByID() throws RepositoryException {
    Optional<CommentVote> retrievedCommentVote = repository.getModelByID(UUIDVote);
    assertEquals(retrievedCommentVote.get().getCommentID(), Optional.of(vote).get().getCommentID());
    assertEquals(retrievedCommentVote.get().getUserID(), Optional.of(vote).get().getUserID());
    assertEquals(retrievedCommentVote.get().getVoteID(), Optional.of(vote).get().getVoteID());
    assertEquals(retrievedCommentVote.get().getVoteType(), Optional.of(vote).get().getVoteType());
  }

  @Test
  void addCommentVoteAlreadyExists() throws RepositoryException {
    assertFalse(repository.save(vote));
  }

  @Test
  void addCommentVote() throws RepositoryException {
    CommentVote newVote = new CommentVote(UUIDNew, VoteEnum.UPVOTE, UUIDUser, UUIDComment);
    assertTrue(repository.save(newVote));
    Optional<CommentVote> retrievedProduct = repository.getModelByID(UUIDNew);
    assertTrue(retrievedProduct.isPresent());
    assertEquals(retrievedProduct.get().getCommentID(), Optional.of(newVote).get().getCommentID());
    assertEquals(retrievedProduct.get().getUserID(), Optional.of(newVote).get().getUserID());
    assertEquals(retrievedProduct.get().getVoteID(), Optional.of(newVote).get().getVoteID());
    assertEquals(retrievedProduct.get().getVoteType(), Optional.of(newVote).get().getVoteType());
  }

  @Test
  void deleteCommentVoteByID() throws RepositoryException {
    CommentVote newVote = new CommentVote(UUIDToDelete, VoteEnum.DOWNVOTE, UUIDUser, UUIDComment);
    assertTrue(repository.save(newVote));
    assertTrue(repository.getModelByID(UUIDToDelete).isPresent());
    assertTrue(repository.deleteByID(UUIDToDelete));
    assertFalse(repository.getAllModels().contains(newVote));
  }

  @Test
  void getAllCommentVotes() throws RepositoryException {
    List<CommentVote> commentVotes = repository.getAllModels();
    assertTrue(1 <= commentVotes.size());
    assertEquals(commentVotes.get(0).getVoteID(), Optional.of(vote).get().getVoteID());
    assertEquals(commentVotes.get(0).getVoteType(), Optional.of(vote).get().getVoteType());
    assertEquals(commentVotes.get(0).getUserID(), Optional.of(vote).get().getUserID());
    assertEquals(commentVotes.get(0).getCommentID(), Optional.of(vote).get().getCommentID());
  }

  @Test
  void deleteCommentVotesByIDDoesntExist() throws RepositoryException {
    assertFalse(repository.deleteByID(UUIDDoesntExist));
  }

  @Test
  void updateCommentVote() throws RepositoryException, ParseException {
    CommentVote newVote = new CommentVote(UUIDToDelete, VoteEnum.DOWNVOTE, UUIDUser, UUIDComment);
    assertTrue(repository.save(newVote));
    assertEquals(repository.getModelByID(UUIDToDelete).get().getVoteType(), VoteEnum.DOWNVOTE);
    CommentVote updatedVote = new CommentVote(UUIDToDelete, VoteEnum.UPVOTE, UUIDUser, UUIDComment);
    assertTrue(repository.update(updatedVote));
    assertEquals(repository.getModelByID(UUIDToDelete).get().getVoteType(), VoteEnum.UPVOTE);
    repository.deleteByID(UUIDToDelete);
  }

  @Test
  void updateCommentVoteDoesntExist() throws RepositoryException {
    CommentVote newVote =
        new CommentVote(UUIDDoesntExist, VoteEnum.DOWNVOTE, UUIDUser, UUIDComment);
    assertFalse(repository.update(newVote));
  }

  @Test
  void getVotesByCommentID() throws RepositoryException {
    CommentVote newCommentVote = new CommentVote(UUIDDoesntExist, VoteEnum.DOWNVOTE, UUIDUser, UUIDNewComment);
    CommentVote newCommentVote2 = new CommentVote(UUIDToDelete, VoteEnum.UPVOTE, UUIDUser, UUIDComment);
    repository.save(newCommentVote);
    repository.save(newCommentVote2);
    List<CommentVote> commentVoteList = repository.getCommentVotesByCommentID(UUIDNewComment);
    assertEquals(commentVoteList.size(), 1);
    List<CommentVote> commentVoteList2 = repository.getCommentVotesByCommentID(UUIDComment);
    assertTrue(commentVoteList2.size() >= 2);
    repository.deleteByID(UUIDDoesntExist);
    repository.deleteByID(UUIDToDelete);
  }

  @Test
  void getVotesByCommentIDDoesntExist() throws RepositoryException {
    List<CommentVote> commentVoteList = repository.getCommentVotesByCommentID(UUIDNewComment);
    assertEquals(commentVoteList.size(), 0);
  }

}