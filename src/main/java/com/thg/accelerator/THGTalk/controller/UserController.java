package com.thg.accelerator.THGTalk.controller;

import com.thg.accelerator.THGTalk.event.UserJSON;
import com.thg.accelerator.THGTalk.exceptions.ServiceException;
import com.thg.accelerator.THGTalk.model.User;
import com.thg.accelerator.THGTalk.transformer.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/user")
public class UserController extends CRUDController<User, UserJSON> {

  @Override
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> save(@Valid UserJSON jsonModel) {
    User model = transformer.fromJson(jsonModel);
    try {
      if (service.save(model)) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(jsonModel.getUserID()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION,
            String.valueOf(uri)).build();
      } else {
        logger
            .error("Database update command affected 0 rows for the following model: " + model);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
      }
    } catch (ServiceException exception) {
      logger.error("Failed to create data for the following request: " + model, exception);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
