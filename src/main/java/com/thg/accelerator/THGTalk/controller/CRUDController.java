package com.thg.accelerator.THGTalk.controller;

import com.thg.accelerator.THGTalk.exceptions.ServiceException;
import com.thg.accelerator.THGTalk.service.CRUDService;
import com.thg.accelerator.THGTalk.transformer.ICRUDTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class CRUDController<M, J> implements ICRUDController<M, J> {
  protected static final Logger logger = LoggerFactory.getLogger(CommentController.class);

  @Autowired
  CRUDService<M> service;

  @Autowired
  ICRUDTransformer<M, J> transformer;

  @Override
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> save(@Valid J jsonModel) {
    UUID uuid = UUID.randomUUID();
    M model = transformer.fromJSONWithUUID(uuid, jsonModel);
    try {
      if (service.save(model)) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(uuid).toUri();
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

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return errors;
  }

  @Override
  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> update(@PathVariable UUID id,
                                     @RequestBody J jsonModel) {
    M model = transformer.fromJSONWithUUID(id, jsonModel);
    try {
      if (service.update(model)) {
        return new ResponseEntity<>(HttpStatus.OK);
      } else {
        logger
            .error("Database update command affected 0 rows for the following model: " + model);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } catch (ServiceException exception) {
      logger.error("Failed to update data for the following request: " + model, exception);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> deleteByID(@PathVariable UUID id) {
    try {
      if (service.deleteByID(id)) {
        return new ResponseEntity<>(HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } catch (ServiceException exception) {
      logger.error("Failed to delete data for the following model ID: " + id, exception);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  @GetMapping(value = "/{id}")
  public ResponseEntity<J> getModelByID(@PathVariable UUID id) {
    try {
      Optional<M> model = service.getModelByID(id);
      if (model.isPresent()) {
        return new ResponseEntity<>(transformer.toJson(model.get()), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } catch (ServiceException exception) {
      logger.error("Failed to retrieve data for model ID: " + id, exception);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  @GetMapping
  public ResponseEntity<List<J>> getAllModels() {
    try {
      List<M> model = service.getAllModels();
      return new ResponseEntity<>(transformer.toJsonList(model), HttpStatus.OK);
    } catch (ServiceException exception) {
      logger.error("Failed to retrieve all models", exception);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
