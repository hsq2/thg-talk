package com.thg.accelerator.THGTalk.controller;

import com.thg.accelerator.THGTalk.event.PostJSON;
import com.thg.accelerator.THGTalk.exceptions.ServiceException;
import com.thg.accelerator.THGTalk.model.Post;
import com.thg.accelerator.THGTalk.service.PostService;
import com.thg.accelerator.THGTalk.transformer.PostTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/post")
public class PostController extends CRUDController<Post, PostJSON> {
  @Autowired
  PostService service;

  @Autowired
  PostTransformer transformer;

  @GetMapping(value = "/bysubtalk/{id}")
  public ResponseEntity<List<PostJSON>> getPostsBySubTalkID(@PathVariable UUID id) {
    try {
      List<Post> model = service.getPostsBySubTalkID(id);
      if (model.isEmpty()) {
        return new ResponseEntity<>(transformer.toJsonList(model), HttpStatus.NOT_FOUND);
      } else {
        return new ResponseEntity<>(transformer.toJsonList(model), HttpStatus.OK);
      }
    } catch (ServiceException exception) {
      logger.error("Failed to retrieve all posts with subtalk ID: " + id, exception);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/top")
  public ResponseEntity<List<PostJSON>> getTopPosts(@RequestParam int number) {
    try {
      if (number < 0) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      List<Post> model = service.getTopPosts(number);
      return new ResponseEntity<>(transformer.toJsonList(model), HttpStatus.OK);
    } catch (ServiceException exception) {
      logger.error("Failed to retrieve top " + number + " posts", exception);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(value = "/byuser/{id}")
  public ResponseEntity<List<PostJSON>> getPostsByUserID(@PathVariable UUID id) {
    try {
      List<Post> model = service.getPostsByUserID(id);
      return new ResponseEntity<>(transformer.toJsonList(model), HttpStatus.OK);
    } catch (ServiceException exception) {
      logger.error("Failed to retrieve all posts with user ID: " + id, exception);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
