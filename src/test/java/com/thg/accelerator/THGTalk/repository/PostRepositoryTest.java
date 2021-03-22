package com.thg.accelerator.THGTalk.repository;

import com.thg.accelerator.THGTalk.exceptions.RepositoryException;
import com.thg.accelerator.THGTalk.model.Comment;
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
class PostRepositoryTest {

  @Autowired
  PostRepository repository;

  UUID UUIDPost = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc41");
  UUID UUIDUser = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39");
  UUID UUIDNewUser = UUID.fromString("71614667-d279-11e7-a5ac-f941ac8dfc39");
  UUID UUIDSub = UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc40");
  UUID UUIDNewSub = UUID.fromString("71614667-d279-11e7-a5ac-f941ac8dfc40");
  UUID UUIDDoesntExist = UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc39");
  UUID UUIDNew = UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc40");
  UUID UUIDToDelete = UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc41");
  Date date = convertStringToDate("2017-03-31 9:30:20");

  Post post = new Post(UUIDPost, UUIDUser, UUIDSub, "First post", "Nice", date);

  PostRepositoryTest() throws ParseException {
  }

  private Date convertStringToDate(String stringDate) throws ParseException {
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
    Date date = sdfDate.parse(stringDate);
    return date;
  }

  @Test
  void getPostByID() throws RepositoryException {
    Optional<Post> retrievedPost = repository.getModelByID(UUIDPost);
    assertEquals(retrievedPost.get().getPostID(), Optional.of(post).get().getPostID());
    assertEquals(retrievedPost.get().getSubID(), Optional.of(post).get().getSubID());
    assertEquals(retrievedPost.get().getUserID(), Optional.of(post).get().getUserID());
    assertEquals(retrievedPost.get().getPostTitle(), Optional.of(post).get().getPostTitle());
    assertEquals(retrievedPost.get().getPost(), Optional.of(post).get().getPost());
    assertEquals(retrievedPost.get().getDateCreated().toString(), "2017-03-31 09:30:20.0");
  }

  @Test
  void deletePostByID() throws RepositoryException, ParseException {
    Post newPost = new Post(UUIDToDelete, UUIDUser, UUIDSub, "Second post", "post", date);
    assertTrue(repository.save(newPost));
    assertTrue(repository.deleteByID(UUIDToDelete));
    assertFalse(repository.getAllModels().contains(newPost));
  }

  @Test
  void getAllPosts() throws RepositoryException {
    List<Post> posts = repository.getAllModels();
    assertTrue(1 <= posts.size());
    assertEquals(posts.get(0).getSubID(), Optional.of(post).get().getSubID());
    assertEquals(posts.get(0).getPostID(), Optional.of(post).get().getPostID());
    assertEquals(posts.get(0).getUserID(), Optional.of(post).get().getUserID());
    assertEquals(posts.get(0).getPost(), Optional.of(post).get().getPost());
    assertEquals(posts.get(0).getPostTitle(), Optional.of(post).get().getPostTitle());
    assertEquals(posts.get(0).getDateCreated().toString(), "2017-03-31 09:30:20.0");
  }

  @Test
  void deletePostByIDDoesntExist() throws RepositoryException {
    assertFalse(repository.deleteByID(UUIDDoesntExist));
  }

  @Test
  void addPostAlreadyExists() throws RepositoryException {
    assertFalse(repository.save(post));
  }

  @Test
  void addPost() throws RepositoryException, ParseException {
    Post newPost = new Post(UUIDNew, UUIDUser, UUIDSub, "Third Post", "Nice", date);
    assertTrue(repository.save(newPost));
    Optional<Post> retrievedPost = repository.getModelByID(UUIDNew);
    assertEquals(retrievedPost.get().getPostID(), Optional.of(newPost).get().getPostID());
    assertEquals(retrievedPost.get().getSubID(), Optional.of(newPost).get().getSubID());
    assertEquals(retrievedPost.get().getUserID(), Optional.of(newPost).get().getUserID());
    assertEquals(retrievedPost.get().getPostTitle(), Optional.of(newPost).get().getPostTitle());
    assertEquals(retrievedPost.get().getPost(), Optional.of(newPost).get().getPost());
    assertEquals(retrievedPost.get().getDateCreated().toString(), "2017-03-31 09:30:20.0");
  }

  @Test
  void updatePost() throws RepositoryException, ParseException {
    Post newPost = new Post(UUIDToDelete, UUIDUser, UUIDSub, "Third Post", "Nice", date);
    assertTrue(repository.save(newPost));
    assertEquals(repository.getModelByID(UUIDToDelete).get().getPostTitle(), "Third Post");
    Post updatedPost = new Post(UUIDToDelete, UUIDUser, UUIDSub, "Updated Post", "Nice", date);
    assertTrue(repository.update(updatedPost));
    assertEquals(repository.getModelByID(UUIDToDelete).get().getPostTitle(), "Updated Post");
    repository.deleteByID(UUIDToDelete);
  }

  @Test
  void updatePostDoesntExist() throws RepositoryException, ParseException {
    Post newPost = new Post(UUIDDoesntExist, UUIDUser, UUIDSub, "Third Post", "Nice", date);
    assertFalse(repository.update(newPost));
  }

  @Test
  void getPostsBySubID() throws RepositoryException {
    Post newPost = new Post(UUIDDoesntExist, UUIDUser, UUIDSub, "New post", "this is a post", date);
    Post newPost2 = new Post(UUIDToDelete, UUIDUser, UUIDNewSub, "New post 2", "this is the second post", date);
    repository.save(newPost);
    repository.save(newPost2);
    List<Post> postsBySubID = repository.getPostsBySubTalkID(UUIDNewSub);
    assertEquals(postsBySubID.size(), 1);
    List<Post> postsBySubID1 = repository.getPostsBySubTalkID(UUIDSub);
    assertTrue(postsBySubID1.size() >= 2);
    repository.deleteByID(UUIDDoesntExist);
    repository.deleteByID(UUIDToDelete);
  }

  @Test
  void getPostsBySubIDDoesntExist() throws RepositoryException {
    List<Post> postBySubID = repository.getPostsBySubTalkID(UUIDNewSub);
    assertEquals(postBySubID.size(), 0);
  }

  @Test
  void getPostsByUserID() throws RepositoryException {
    Post newPost = new Post(UUIDDoesntExist, UUIDUser, UUIDSub, "New post", "this is a post", date);
    Post newPost2 = new Post(UUIDToDelete, UUIDNewUser, UUIDNewSub, "New post 2", "this is the second post", date);
    repository.save(newPost);
    repository.save(newPost2);
    List<Post> postsByUserID = repository.getPostsByUserID(UUIDNewUser);
    assertEquals(postsByUserID.size(), 1);
    List<Post> postsByUserID1 = repository.getPostsByUserID(UUIDUser);
    assertTrue(postsByUserID1.size() >= 2);
    repository.deleteByID(UUIDDoesntExist);
    repository.deleteByID(UUIDToDelete);
  }

  @Test
  void getPostsByUserIDDoesntExist() throws RepositoryException {
    List<Post> postByUserID = repository.getPostsBySubTalkID(UUIDPost);
    assertEquals(postByUserID.size(), 0);
  }
}