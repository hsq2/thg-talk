package com.thg.accelerator.THGTalk.service;

import com.thg.accelerator.THGTalk.exceptions.RepositoryException;
import com.thg.accelerator.THGTalk.exceptions.ServiceException;
import com.thg.accelerator.THGTalk.model.PostVote;
import com.thg.accelerator.THGTalk.model.VoteEnum;
import com.thg.accelerator.THGTalk.repository.PostVoteRepository;
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
public class PostVoteServiceTest {
  private final PostVote postVote1 = new PostVote(
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc44"),
      VoteEnum.UPVOTE,
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39"),
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc42")
  );

  private final PostVote postVote2 = new PostVote(
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc40"),
      VoteEnum.UPVOTE,
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc39"),
      UUID.fromString("61614667-d279-11e7-a5ac-f941ac8dfc43")
  );

  @Autowired
  private PostVoteService postVoteService;

  @MockBean
  private PostVoteRepository postVoteRepository;

  @Test
  public void testSave() throws RepositoryException, ServiceException {
    when(postVoteRepository.save(postVote1)).thenReturn(true);
    when(postVoteRepository.save(postVote2))
        .thenThrow(new RepositoryException("Testing exception handling"));
    postVoteService.save(postVote1);
    verify(postVoteRepository, times(1)).save(postVote1);
    assertThrows(ServiceException.class, () -> postVoteService.save(postVote2));
  }

  @Test
  public void testUpdate() throws RepositoryException, ServiceException {
    when(postVoteRepository.update(postVote1)).thenReturn(true);
    when(postVoteRepository.update(postVote2))
        .thenThrow(new RepositoryException("Testing exception handling"));
    postVoteService.update(postVote1);
    verify(postVoteRepository, times(1)).update(postVote1);
    assertThrows(ServiceException.class, () -> postVoteService.update(postVote2));
  }

  @Test
  public void testDelete() throws RepositoryException, ServiceException {
    when(postVoteRepository.deleteByID(postVote1.getVoteID())).thenReturn(true);
    when(postVoteRepository.deleteByID(postVote2.getVoteID()))
        .thenThrow(new RepositoryException("Testing exception handling"));
    postVoteService.deleteByID(postVote1.getVoteID());
    verify(postVoteRepository, times(1)).deleteByID(postVote1.getVoteID());
    assertThrows(ServiceException.class, () -> postVoteService.deleteByID(postVote2.getVoteID()));
  }

  @Test
  public void testGetModelByID() throws RepositoryException, ServiceException {
    when(postVoteRepository.getModelByID(postVote1.getVoteID()))
        .thenReturn(java.util.Optional.of(postVote1));
    when(postVoteRepository.getModelByID(postVote2.getVoteID()))
        .thenThrow(new RepositoryException("Testing exception handling"));
    postVoteService.getModelByID(postVote1.getVoteID());
    verify(postVoteRepository, times(1)).getModelByID(postVote1.getVoteID());
    assertThrows(ServiceException.class,
        () -> postVoteService.getModelByID(postVote2.getVoteID()));
  }

  @Test
  public void testGetPostVotesByPostID() throws RepositoryException, ServiceException {
    when(postVoteRepository.getPostVotesByPostID(postVote1.getPostID()))
        .thenReturn(Collections.singletonList(postVote1));
    when(postVoteRepository.getPostVotesByPostID(postVote2.getPostID()))
        .thenThrow(new RepositoryException("Testing exception handling"));
    postVoteService.getPostVotesByPostID(postVote1.getPostID());
    verify(postVoteRepository, times(1)).getPostVotesByPostID(postVote1.getPostID());
    assertThrows(ServiceException.class,
        () -> postVoteService.getPostVotesByPostID(postVote2.getPostID()));
  }

}