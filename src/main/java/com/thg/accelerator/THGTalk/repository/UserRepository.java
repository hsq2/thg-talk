package com.thg.accelerator.THGTalk.repository;

import com.thg.accelerator.THGTalk.exceptions.RepositoryException;
import com.thg.accelerator.THGTalk.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepository implements ICRUDRepository<User> {

  @Autowired
  NamedParameterJdbcTemplate jdbcTemplate;

  @Override
  public boolean save(User model) throws RepositoryException {
    try {
      SqlParameterSource namedParameters = new MapSqlParameterSource()
          .addValue("userID", model.getUserID())
          .addValue("userName", model.getUserName())
          .addValue("dateCreated", model.getDateCreated());

      if (!getModelByID(model.getUserID()).isPresent()) {
        jdbcTemplate.update(
            "INSERT INTO Users (userID, userName, dateCreated) VALUES (:userID, :userName, :dateCreated)",
            namedParameters);
        return true;
      }
      return false;
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to insert user with id " + model.getUserID(), e);
    }
  }

  @Override
  public Optional<User> getModelByID(UUID modelID) throws RepositoryException {
    try {
      SqlParameterSource namedParameters = new MapSqlParameterSource()
          .addValue("id", modelID);
      return jdbcTemplate
          .queryForObject(
              "SELECT userID, userName, dateCreated "
                  + "FROM Users WHERE userID=:id  ",
              namedParameters,
              (resultSet, i) -> {
                Date date = resultSet.getTimestamp("dateCreated");
                String userName = resultSet.getString("userName");
                return Optional
                    .of(new User(modelID, userName, date));
              });
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to query for user with id " + modelID, e);
    }
  }

  @Override
  public boolean update(User model) throws RepositoryException {
    try {
      SqlParameterSource namedParameters = new MapSqlParameterSource()
          .addValue("userID", model.getUserID())
          .addValue("userName", model.getUserName())
          .addValue("dateCreated", model.getDateCreated());
      int status = jdbcTemplate.update(
          "UPDATE Users SET userName = :userName, dateCreated = :dateCreated where userID = :userID",
          namedParameters);
      return status != 0;
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to update user with id " + model.getUserID(), e);
    }
  }

  @Override
  public boolean deleteByID(UUID modelID) throws RepositoryException {
    SqlParameterSource namedParameters = new MapSqlParameterSource()
        .addValue("id", modelID);
    try {
      int status =
          jdbcTemplate.update("DELETE FROM Users WHERE userID = :id", namedParameters);
      return status != 0;
    } catch (DataAccessException e) {
      throw new RepositoryException("Could not delete user with id " + modelID);
    }
  }

  @Override
  public List<User> getAllModels() throws RepositoryException {
    try {
      List<User> users;
      users = jdbcTemplate
          .query(
              "SELECT userID, userName, dateCreated FROM Users",
              (resultSet, i) -> {
                UUID userID = resultSet.getObject("userID", UUID.class);
                String userName = resultSet.getString("userName");
                Date date = resultSet.getTimestamp("dateCreated");
                return new User(userID, userName, date);
              });
      return users;
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to get all users", e);
    }
  }
}
