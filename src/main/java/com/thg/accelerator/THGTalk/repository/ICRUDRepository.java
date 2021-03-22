package com.thg.accelerator.THGTalk.repository;

import com.thg.accelerator.THGTalk.exceptions.RepositoryException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ICRUDRepository<T> {
  boolean save(T model) throws RepositoryException;

  Optional<T> getModelByID(UUID modelID) throws RepositoryException;

  boolean update(T model) throws RepositoryException;

  boolean deleteByID(UUID modelID) throws RepositoryException;

  List<T> getAllModels() throws RepositoryException;

}
