package com.thg.accelerator.THGTalk.service;

import com.thg.accelerator.THGTalk.exceptions.RepositoryException;
import com.thg.accelerator.THGTalk.exceptions.ServiceException;
import com.thg.accelerator.THGTalk.model.Comment;
import com.thg.accelerator.THGTalk.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentService extends CRUDService<Comment> {
  @Autowired
  private CommentRepository crudRepository;

  public List<Comment> getCommentsByPostID(UUID modelID) throws ServiceException {
    try {
      return crudRepository.getCommentsByPostID(modelID);
    } catch (RepositoryException exception) {
      throw new ServiceException(
          "Error during get operation for model with post ID" + modelID, exception);
    }
  }

  public List<Comment> getCommentsByUserID(UUID modelID) throws ServiceException {
    try {
      return crudRepository.getCommentsByUserID(modelID);
    } catch (RepositoryException exception) {
      throw new ServiceException(
          "Error during get operation for model with user ID" + modelID, exception);
    }
  }
}
