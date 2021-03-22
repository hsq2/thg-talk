package com.thg.accelerator.THGTalk.service;

import com.thg.accelerator.THGTalk.exceptions.RepositoryException;
import com.thg.accelerator.THGTalk.exceptions.ServiceException;
import com.thg.accelerator.THGTalk.model.Post;
import com.thg.accelerator.THGTalk.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostService extends CRUDService<Post> {
  @Autowired
  private PostRepository crudRepository;

  public List<Post> getPostsBySubTalkID(UUID modelID) throws ServiceException {
    try {
      return crudRepository.getPostsBySubTalkID(modelID);
    } catch (RepositoryException exception) {
      throw new ServiceException(
          "Error during get operation for model with post ID" + modelID, exception);
    }
  }

  public List<Post> getTopPosts(int number) throws ServiceException {
    try {
      return crudRepository.getTopPosts(number);
    } catch (RepositoryException exception) {
      throw new ServiceException(
          "Error during getting top posts", exception
      );
    }
  }

  public List<Post> getPostsByUserID(UUID modelID) throws ServiceException {
    try {
      return crudRepository.getPostsByUserID(modelID);
    } catch (RepositoryException exception) {
      throw new ServiceException(
          "Error during get operation for model with user ID" + modelID, exception);
    }
  }
}
