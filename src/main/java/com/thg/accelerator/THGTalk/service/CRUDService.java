package com.thg.accelerator.THGTalk.service;

import com.thg.accelerator.THGTalk.exceptions.RepositoryException;
import com.thg.accelerator.THGTalk.exceptions.ServiceException;
import com.thg.accelerator.THGTalk.repository.ICRUDRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CRUDService<T> implements ICRUDService<T> {
  @Autowired
  private ICRUDRepository<T> crudRepository;

  @Override
  public boolean save(T model) throws ServiceException {
    try {
      return crudRepository.save(model);
    } catch (RepositoryException exception) {
      throw new ServiceException(
          "Error during save operation for model: " + model, exception);
    }
  }

  @Override
  public Optional<T> getModelByID(UUID modelID) throws ServiceException {
    try {
      return crudRepository.getModelByID(modelID);
    } catch (RepositoryException exception) {
      throw new ServiceException(
          "Error during get operation for model with ID" + modelID, exception);
    }
  }

  @Override
  public boolean update(T model) throws ServiceException {
    try {
      return crudRepository.update(model);
    } catch (RepositoryException exception) {
      throw new ServiceException(
          "Error during update operation for model: " + model, exception);
    }
  }

  @Override
  public boolean deleteByID(UUID modelID) throws ServiceException {
    try {
      return crudRepository.deleteByID(modelID);
    } catch (RepositoryException exception) {
      throw new ServiceException(
          "Error during delete operation for model with ID" + modelID, exception);
    }
  }

  @Override
  public List<T> getAllModels() throws ServiceException {
    try {
      return crudRepository.getAllModels();
    } catch (RepositoryException exception) {
      throw new ServiceException(
          "Error during query operation for all models", exception);
    }
  }
}
