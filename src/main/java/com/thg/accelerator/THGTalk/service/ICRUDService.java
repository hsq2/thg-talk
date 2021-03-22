package com.thg.accelerator.THGTalk.service;

import com.thg.accelerator.THGTalk.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICRUDService<T> {
  boolean save(T model) throws ServiceException;

  Optional<T> getModelByID(UUID modelID) throws ServiceException;

  boolean update(T model) throws ServiceException;

  boolean deleteByID(UUID modelID) throws ServiceException;

  List<T> getAllModels() throws ServiceException;

}
