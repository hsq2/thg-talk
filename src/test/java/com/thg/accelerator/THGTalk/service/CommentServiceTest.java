package com.thg.accelerator.THGTalk.service;

import com.thg.accelerator.THGTalk.exceptions.RepositoryException;
import com.thg.accelerator.THGTalk.exceptions.ServiceException;
import com.thg.accelerator.THGTalk.model.Comment;
import com.thg.accelerator.THGTalk.repository.CommentRepository;
import org.junit.Test;
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
public class CommentServiceTest {
  private final Comment comment1 = new Comment(
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc42"),
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc41"),
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39"),
      "Comment 1",
      convertStringToDate("2017-03-31 9:30:20"));

  private final Comment comment2 = new Comment(
      UUID.fromString("81614667-d279-11e7-a5ac-f941ac8dfc40"),
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc43"),
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc44"),
      "Comment 1",
      convertStringToDate("2017-03-31 9:30:20"));

  @Autowired
  private CommentService commentService;

  @MockBean
  private CommentRepository commentRepository;

  public CommentServiceTest() throws ParseException {
  }

  private Date convertStringToDate(String stringDate) throws ParseException {
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
    Date date = sdfDate.parse(stringDate);
    return date;
  }

  @Test
  public void testSave() throws RepositoryException, ServiceException {
    when(commentRepository.save(comment1)).thenReturn(true);
    when(commentRepository.save(comment2))
        .thenThrow(new RepositoryException("Testing exception handling"));
    commentService.save(comment1);
    verify(commentRepository, times(1)).save(comment1);
    assertThrows(ServiceException.class, () -> commentService.save(comment2));
  }

  @Test
  public void testUpdate() throws RepositoryException, ServiceException {
    when(commentRepository.update(comment1)).thenReturn(true);
    when(commentRepository.update(comment2))
        .thenThrow(new RepositoryException("Testing exception handling"));
    commentService.update(comment1);
    verify(commentRepository, times(1)).update(comment1);
    assertThrows(ServiceException.class, () -> commentService.update(comment2));
  }

  @Test
  public void testDelete() throws RepositoryException, ServiceException {
    when(commentRepository.deleteByID(comment1.getCommentID())).thenReturn(true);
    when(commentRepository.deleteByID(comment2.getCommentID()))
        .thenThrow(new RepositoryException("Testing exception handling"));
    commentService.deleteByID(comment1.getCommentID());
    verify(commentRepository, times(1)).deleteByID(comment1.getCommentID());
    assertThrows(ServiceException.class, () -> commentService.deleteByID(comment2.getCommentID()));
  }

  @Test
  public void testGetModelByID() throws RepositoryException, ServiceException {
    when(commentRepository.getModelByID(comment1.getCommentID())).thenReturn(
        java.util.Optional.of(comment1));
    when(commentRepository.getModelByID(comment2.getCommentID()))
        .thenThrow(new RepositoryException("Testing exception handling"));
    commentService.getModelByID(comment1.getCommentID());
    verify(commentRepository, times(1)).getModelByID(comment1.getCommentID());
    assertThrows(ServiceException.class,
        () -> commentService.getModelByID(comment2.getCommentID()));
  }

  @Test
  public void testGetCommentsByPostID() throws RepositoryException, ServiceException {
    when(commentRepository.getCommentsByPostID(comment1.getPostID()))
        .thenReturn(Collections.singletonList(comment1));
    when(commentRepository.getCommentsByPostID(comment2.getPostID()))
        .thenThrow(new RepositoryException("Testing exception handling"));
    commentService.getCommentsByPostID(comment1.getPostID());
    verify(commentRepository, times(1)).getCommentsByPostID(comment1.getPostID());
    assertThrows(ServiceException.class,
        () -> commentService.getCommentsByPostID(comment2.getPostID()));
  }

  @Test
  public void testGetCommentsByUserID() throws RepositoryException, ServiceException {
    when(commentRepository.getCommentsByUserID(comment1.getUserID()))
        .thenReturn(Collections.singletonList(comment1));
    when(commentRepository.getCommentsByUserID(comment2.getUserID()))
        .thenThrow(new RepositoryException("Testing exception handling"));
    commentService.getCommentsByUserID(comment1.getUserID());
    verify(commentRepository, times(1)).getCommentsByUserID(comment1.getUserID());
    assertThrows(ServiceException.class,
        () -> commentService.getCommentsByUserID(comment2.getUserID()));
  }
}