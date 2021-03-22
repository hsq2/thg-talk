package com.thg.accelerator.THGTalk.controller;

import com.thg.accelerator.THGTalk.event.CommentVoteJSON;
import com.thg.accelerator.THGTalk.exceptions.ServiceException;
import com.thg.accelerator.THGTalk.model.CommentVote;
import com.thg.accelerator.THGTalk.service.CommentVoteService;
import com.thg.accelerator.THGTalk.transformer.CommentVoteTransformer;
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
@RequestMapping(value = "/api/commentvote")
public class CommentVoteController extends CRUDController<CommentVote, CommentVoteJSON> {
  @Autowired
  CommentVoteService service;

  @Autowired
  CommentVoteTransformer transformer;

  @GetMapping(value = "/bycomment/{id}")
  public ResponseEntity<List<CommentVoteJSON>> getCommentVotesByCommentID(@PathVariable UUID id) {
    try {
      List<CommentVote> model = service.getCommentVotesByCommentID(id);
      return new ResponseEntity<>(transformer.toJsonList(model), HttpStatus.OK);
    } catch (ServiceException exception) {
      logger.error("Failed to retrieve all comment votes by comment ID: " + id, exception);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
