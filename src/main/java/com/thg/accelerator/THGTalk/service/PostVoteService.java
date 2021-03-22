package com.thg.accelerator.THGTalk.service;

import com.thg.accelerator.THGTalk.exceptions.RepositoryException;
import com.thg.accelerator.THGTalk.exceptions.ServiceException;
import com.thg.accelerator.THGTalk.model.PostVote;
import com.thg.accelerator.THGTalk.repository.PostVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostVoteService extends CRUDService<PostVote> {
  @Autowired
  private PostVoteRepository crudRepository;

  public List<PostVote> getPostVotesByPostID(UUID modelID) throws ServiceException {
    try {
      return crudRepository.getPostVotesByPostID(modelID);
    } catch (RepositoryException exception) {
      throw new ServiceException(
          "Error during get operation for model with post ID" + modelID, exception);
    }
  }
}
