package com.thg.accelerator.THGTalk.controller;

import com.thg.accelerator.THGTalk.event.PostVoteJSON;
import com.thg.accelerator.THGTalk.exceptions.ServiceException;
import com.thg.accelerator.THGTalk.model.PostVote;
import com.thg.accelerator.THGTalk.service.PostVoteService;
import com.thg.accelerator.THGTalk.transformer.PostVoteTransformer;
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
@RequestMapping(value = "/api/postvote")
public class PostVoteController extends CRUDController<PostVote, PostVoteJSON> {
  @Autowired
  PostVoteService service;

  @Autowired
  PostVoteTransformer transformer;

  @GetMapping(value = "/bypost/{id}")
  public ResponseEntity<List<PostVoteJSON>> getPostVotesByPostID(@PathVariable UUID id) {
    try {
      List<PostVote> model = service.getPostVotesByPostID(id);
      return new ResponseEntity<>(transformer.toJsonList(model), HttpStatus.OK);
    } catch (ServiceException exception) {
      logger.error("Failed to retrieve all post votes by post ID: " + id, exception);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
