package com.thg.accelerator.THGTalk.repository;

import com.thg.accelerator.THGTalk.exceptions.RepositoryException;
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
class PostVoteRepositoryTest {

  @Autowired
  PostVoteRepository repository;

  UUID UUIDPost = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc41");
  UUID UUIDNewPost = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc51");
  UUID UUIDNew2 = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc52");
  UUID UUIDUser = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39");
  UUID UUIDVote = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc43");
  UUID UUIDDoesntExist = UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc39");
  UUID UUIDNew = UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc40");
  UUID UUIDToDelete = UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc41");

  PostVote vote = new PostVote(UUIDVote, VoteEnum.UPVOTE, UUIDUser, UUIDPost);

  @Test
  void getPostVoteByID() throws RepositoryException {
    Optional<PostVote> retrievedProduct = repository.getModelByID(UUIDVote);
    assertEquals(retrievedProduct.get().getPostID(), Optional.of(vote).get().getPostID());
    assertEquals(retrievedProduct.get().getUserID(), Optional.of(vote).get().getUserID());
    assertEquals(retrievedProduct.get().getVoteID(), Optional.of(vote).get().getVoteID());
    assertEquals(retrievedProduct.get().getVoteType(), Optional.of(vote).get().getVoteType());
  }

  @Test
  void addPostVoteAlreadyExists() throws RepositoryException {
    assertFalse(repository.save(vote));
  }

  @Test
  void addPostVote() throws RepositoryException {
    PostVote newVote = new PostVote(UUIDNew, VoteEnum.UPVOTE, UUIDUser, UUIDPost);
    assertTrue(repository.save(newVote));
    Optional<PostVote> retrievedPostVote = repository.getModelByID(UUIDNew);
    assertTrue(retrievedPostVote.isPresent());
    assertEquals(retrievedPostVote.get().getPostID(), Optional.of(newVote).get().getPostID());
    assertEquals(retrievedPostVote.get().getUserID(), Optional.of(newVote).get().getUserID());
    assertEquals(retrievedPostVote.get().getVoteID(), Optional.of(newVote).get().getVoteID());
    assertEquals(retrievedPostVote.get().getVoteType(), Optional.of(newVote).get().getVoteType());
  }

  @Test
  void deletePostVoteByID() throws RepositoryException {
    PostVote newPostVote = new PostVote(UUIDToDelete, VoteEnum.DOWNVOTE, UUIDUser, UUIDPost);
    assertTrue(repository.save(newPostVote));
    assertTrue(repository.getModelByID(UUIDToDelete).isPresent());
    assertTrue(repository.deleteByID(UUIDToDelete));
    assertFalse(repository.getAllModels().contains(newPostVote));
  }

  @Test
  void getAllPostVotes() throws RepositoryException {
    List<PostVote> postVotes = repository.getAllModels();
    assertTrue(1 <= postVotes.size());
    assertEquals(postVotes.get(0).getVoteID(), Optional.of(vote).get().getVoteID());
    assertEquals(postVotes.get(0).getVoteType(), Optional.of(vote).get().getVoteType());
    assertEquals(postVotes.get(0).getUserID(), Optional.of(vote).get().getUserID());
    assertEquals(postVotes.get(0).getPostID(), Optional.of(vote).get().getPostID());
  }

  @Test
  void deletePostVotesByIDDoesntExist() throws RepositoryException {
    assertFalse(repository.deleteByID(UUIDDoesntExist));
  }

  @Test
  void updatePostVote() throws RepositoryException, ParseException {
    PostVote newPostVote = new PostVote(UUIDToDelete, VoteEnum.DOWNVOTE, UUIDUser, UUIDPost);
    assertTrue(repository.save(newPostVote));
    assertEquals(repository.getModelByID(UUIDToDelete).get().getVoteType(), VoteEnum.DOWNVOTE);
    PostVote updatedPostVote = new PostVote(UUIDToDelete, VoteEnum.UPVOTE, UUIDUser, UUIDPost);
    assertTrue(repository.update(updatedPostVote));
    assertEquals(repository.getModelByID(UUIDToDelete).get().getVoteType(), VoteEnum.UPVOTE);
    repository.deleteByID(UUIDToDelete);
  }

  @Test
  void updatePostVoteDoesntExist() throws RepositoryException {
    PostVote newPostVote = new PostVote(UUIDDoesntExist, VoteEnum.DOWNVOTE, UUIDUser, UUIDPost);
    assertFalse(repository.update(newPostVote));
  }

  @Test
  void getVotesByPostID() throws RepositoryException {
    PostVote newPostVote = new PostVote(UUIDNew, VoteEnum.DOWNVOTE, UUIDUser, UUIDNewPost);
    PostVote newPostVote2 = new PostVote(UUIDNew2, VoteEnum.UPVOTE, UUIDUser, UUIDPost);
    repository.save(newPostVote);
    repository.save(newPostVote2);
    List<PostVote> postVoteList = repository.getPostVotesByPostID(UUIDNewPost);
    assertEquals(postVoteList.size(), 1);
    List<PostVote> postVoteList2 = repository.getPostVotesByPostID(UUIDPost);
    assertTrue(postVoteList2.size() >= 2);
    repository.deleteByID(UUIDNew);
    repository.deleteByID(UUIDNew2);
  }

  @Test
  void getVotesByPostIDDoesntExist() throws RepositoryException {
    List<PostVote> postVoteList = repository.getPostVotesByPostID(UUIDNewPost);
    assertEquals(postVoteList.size(), 0);
  }

}