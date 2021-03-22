package com.thg.accelerator.THGTalk.service;

import com.thg.accelerator.THGTalk.exceptions.RepositoryException;
import com.thg.accelerator.THGTalk.exceptions.ServiceException;
import com.thg.accelerator.THGTalk.model.Post;
import com.thg.accelerator.THGTalk.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class PostServiceTest {
  private final Post post1 = new Post(
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc41"),
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39"),
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc40"),
      "Post 1",
      "post 1 ipsum",
      convertStringToDate("2017-03-31 9:30:20"));

  private final Post post2 = new Post(
      UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc42"),
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc44"),
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc43"),
      "Post 2",
      "post 2 ipsum",
      convertStringToDate("2017-03-31 9:30:20"));


  @Autowired
  private PostService postService;

  @MockBean
  private PostRepository postRepository;

  public PostServiceTest() throws ParseException {
  }

  private Date convertStringToDate(String stringDate) throws ParseException {
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
    Date date = sdfDate.parse(stringDate);
    return date;
  }

  @Test
  public void testSave() throws RepositoryException, ServiceException {
    when(postService.save(post1)).thenReturn(true);
    when(postRepository.save(post2))
        .thenThrow(new RepositoryException("Testing exception handling"));
    postService.save(post1);
    verify(postRepository, times(1)).save(post1);
    assertThrows(ServiceException.class, () -> postService.save(post2));
  }

  @Test
  public void testUpdate() throws RepositoryException, ServiceException {
    when(postRepository.update(post1)).thenReturn(true);
    when(postRepository.update(post2))
        .thenThrow(new RepositoryException("Testing exception handling"));
    postService.update(post1);
    verify(postRepository, times(1)).update(post1);
    assertThrows(ServiceException.class, () -> postService.update(post2));
  }

  @Test
  public void testDelete() throws RepositoryException, ServiceException {
    when(postRepository.deleteByID(post1.getPostID())).thenReturn(true);
    when(postRepository.deleteByID(post2.getPostID()))
        .thenThrow(new RepositoryException("Testing exception handling"));
    postService.deleteByID(post1.getPostID());
    verify(postRepository, times(1)).deleteByID(post1.getPostID());
    assertThrows(ServiceException.class, () -> postService.deleteByID(post2.getPostID()));
  }

  @Test
  public void testGetModelByID() throws RepositoryException, ServiceException {
    when(postRepository.getModelByID(post1.getPostID())).thenReturn(
        java.util.Optional.of(post1));
    when(postRepository.getModelByID(post2.getPostID()))
        .thenThrow(new RepositoryException("Testing exception handling"));
    postService.getModelByID(post1.getPostID());
    verify(postRepository, times(1)).getModelByID(post1.getPostID());
    assertThrows(ServiceException.class,
        () -> postService.getModelByID(post2.getPostID()));
  }

  @Test
  public void testGetPostsBySubTalkID() throws RepositoryException, ServiceException {
    when(postService.getPostsBySubTalkID(post1.getSubID()))
        .thenReturn(Collections.singletonList(post1));
    when(postService.getPostsBySubTalkID(post2.getSubID()))
        .thenThrow(new RepositoryException("Testing exception handling"));
    postService.getPostsBySubTalkID(post1.getSubID());
    verify(postRepository, times(1)).getPostsBySubTalkID(post1.getSubID());
    assertThrows(ServiceException.class,
        () -> postService.getPostsBySubTalkID(post2.getSubID()));
  }

  @Test
  public void testGetPostsByUserID() throws RepositoryException, ServiceException {
    when(postService.getPostsByUserID(post1.getUserID()))
        .thenReturn(Collections.singletonList(post1));
    when(postService.getPostsByUserID(post2.getUserID()))
        .thenThrow(new RepositoryException("Testing exception handling"));
    postService.getPostsByUserID(post1.getUserID());
    verify(postRepository, times(1)).getPostsByUserID(post1.getUserID());
    assertThrows(ServiceException.class,
        () -> postService.getPostsByUserID(post2.getUserID()));
  }

}