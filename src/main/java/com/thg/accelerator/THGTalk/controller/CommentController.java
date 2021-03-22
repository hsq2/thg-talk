package com.thg.accelerator.THGTalk.controller;

import com.thg.accelerator.THGTalk.event.CommentJSON;
import com.thg.accelerator.THGTalk.exceptions.ServiceException;
import com.thg.accelerator.THGTalk.model.Comment;
import com.thg.accelerator.THGTalk.service.CommentService;
import com.thg.accelerator.THGTalk.transformer.CommentTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/comment")
public class CommentController extends CRUDController<Comment, CommentJSON> {
  @Autowired
  CommentService service;

  @Autowired
  CommentTransformer transformer;

  @GetMapping(value = "/bypost/{id}")
  public ResponseEntity<List<CommentJSON>> getCommentsByPostID(@PathVariable UUID id) {
    try {
      List<Comment> model = service.getCommentsByPostID(id);
        return new ResponseEntity<>(transformer.toJsonList(model), HttpStatus.OK);
    } catch (ServiceException exception) {
      logger.error("Failed to retrieve all comments with post ID: " + id, exception);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(value = "/byuser/{id}")
  public ResponseEntity<List<CommentJSON>> getCommentsByUserID(@PathVariable UUID id) {
    try {
      List<Comment> model = service.getCommentsByUserID(id);
      return new ResponseEntity<>(transformer.toJsonList(model), HttpStatus.OK);
    } catch (ServiceException exception) {
      logger.error("Failed to retrieve all comments with user ID: " + id, exception);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
