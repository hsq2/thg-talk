package com.thg.accelerator.THGTalk.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface ICRUDController<M, J> {
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Void> save(@RequestBody @Valid J jsonModel);

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> update(@PathVariable UUID id,
                                     @RequestBody J jsonModel);

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> deleteByID(@PathVariable UUID id);

  @GetMapping(value = "/{id}")
  public ResponseEntity<J> getModelByID(@PathVariable UUID id);

  @GetMapping
  public ResponseEntity<List<J>> getAllModels();
}
