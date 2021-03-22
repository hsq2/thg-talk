package com.thg.accelerator.THGTalk.service;

import com.thg.accelerator.THGTalk.exceptions.RepositoryException;
import com.thg.accelerator.THGTalk.exceptions.ServiceException;
import com.thg.accelerator.THGTalk.model.CommentVote;
import com.thg.accelerator.THGTalk.model.VoteEnum;
import com.thg.accelerator.THGTalk.repository.CommentVoteRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class CommentVoteServiceTest {
  private final CommentVote commentVote1 = new CommentVote(
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc44"),
      VoteEnum.UPVOTE,
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39"),
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc42")
  );

  private final CommentVote commentVote2 = new CommentVote(
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc40"),
      VoteEnum.UPVOTE,
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39"),
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc43")
  );

  @Autowired
  private CommentVoteService commentVoteService;

  @MockBean
  private CommentVoteRepository commentVoteRepository;

  @Test
  public void testSave() throws RepositoryException, ServiceException {
    when(commentVoteRepository.save(commentVote1)).thenReturn(true);
    when(commentVoteRepository.save(commentVote2))
        .thenThrow(new RepositoryException("Testing exception handling"));
    commentVoteService.save(commentVote1);
    verify(commentVoteRepository, times(1)).save(commentVote1);
    assertThrows(ServiceException.class, () -> commentVoteService.save(commentVote2));
  }

  @Test
  public void testUpdate() throws RepositoryException, ServiceException {
    when(commentVoteRepository.update(commentVote1)).thenReturn(true);
    when(commentVoteRepository.update(commentVote2))
        .thenThrow(new RepositoryException("Testing exception handling"));
    commentVoteService.update(commentVote1);
    verify(commentVoteRepository, times(1)).update(commentVote1);
    assertThrows(ServiceException.class, () -> commentVoteService.update(commentVote2));
  }

  @Test
  public void testDelete() throws RepositoryException, ServiceException {
    when(commentVoteRepository.deleteByID(commentVote1.getVoteID())).thenReturn(true);
    when(commentVoteRepository.deleteByID(commentVote2.getVoteID()))
        .thenThrow(new RepositoryException("Testing exception handling"));
    commentVoteService.deleteByID(commentVote1.getVoteID());
    verify(commentVoteRepository, times(1)).deleteByID(commentVote1.getVoteID());
    assertThrows(ServiceException.class,
        () -> commentVoteService.deleteByID(commentVote2.getVoteID()));
  }

  @Test
  public void testGetModelByID() throws RepositoryException, ServiceException {
    when(commentVoteRepository.getModelByID(commentVote1.getVoteID()))
        .thenReturn(java.util.Optional.of(commentVote1));
    when(commentVoteRepository.getModelByID(commentVote2.getVoteID()))
        .thenThrow(new RepositoryException("Testing exception handling"));
    commentVoteService.getModelByID(commentVote1.getVoteID());
    verify(commentVoteRepository, times(1)).getModelByID(commentVote1.getVoteID());
    assertThrows(ServiceException.class,
        () -> commentVoteService.getModelByID(commentVote2.getVoteID()));
  }

  @Test
  public void testGetCommentVotesByCommentID() throws RepositoryException, ServiceException {
    when(commentVoteRepository.getCommentVotesByCommentID(commentVote1.getCommentID()))
        .thenReturn(Collections.singletonList(commentVote1));
    when(commentVoteRepository.getCommentVotesByCommentID(commentVote2.getCommentID()))
        .thenThrow(new RepositoryException("Testing exception handling"));
    commentVoteService.getCommentVotesByCommentID(commentVote1.getCommentID());
    verify(commentVoteRepository, times(1)).getCommentVotesByCommentID(commentVote1.getCommentID());
    assertThrows(ServiceException.class,
        () -> commentVoteService.getCommentVotesByCommentID(commentVote2.getCommentID()));
  }
}