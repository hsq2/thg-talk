package com.thg.accelerator.THGTalk.repository;

import com.thg.accelerator.THGTalk.exceptions.RepositoryException;
import com.thg.accelerator.THGTalk.model.SubTalk;
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
public class SubTalkRepository implements ICRUDRepository<SubTalk> {

  @Autowired
  NamedParameterJdbcTemplate jdbcTemplate;

  @Override
  public boolean save(SubTalk model) throws RepositoryException {
    try {
      SqlParameterSource namedParameters = new MapSqlParameterSource()
          .addValue("creatorID", model.getUserID())
          .addValue("subTalkID", model.getSubTalkID())
          .addValue("subTalkTitle", model.getSubTalkName())
          .addValue("subTalkDescription", model.getSubTalkDescription())
          .addValue("subTalkImageURL", model.getImageURL())
          .addValue("dateCreated", model.getDateCreated());

      if (!getModelByID(model.getSubTalkID()).isPresent()) {
        jdbcTemplate.update(
            "INSERT INTO SubTalks (subTalkID, creatorID, subTalkTitle, subTalkDescription, subTalkImageURL, dateCreated) VALUES (:subTalkID, :creatorID, :subTalkTitle, :subTalkDescription, :subTalkImageURL, :dateCreated)",
            namedParameters);
        return true;
      }
      return false;
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to insert subtalk with id " + model.getSubTalkID(), e);
    }
  }

  @Override
  public Optional<SubTalk> getModelByID(UUID modelID) throws RepositoryException {
    try {
      SqlParameterSource namedParameters = new MapSqlParameterSource()
          .addValue("id", modelID);
      return jdbcTemplate
          .queryForObject(
              "SELECT subTalkID, creatorID, subTalkTitle, subTalkDescription, subTalkImageURL, dateCreated "
                  + "FROM SubTalks WHERE subTalkID=:id  ",
              namedParameters,
              (resultSet, i) -> {
                UUID userID = resultSet.getObject("creatorID", UUID.class);
                Date date = resultSet.getTimestamp("dateCreated");
                String subTalkDescription = resultSet.getString("subTalkDescription");
                String subTalkTitle = resultSet.getString("subTalkTitle");
                String subTalkImageURL = resultSet.getString("subTalkImageURL");
                return Optional
                    .of(new SubTalk(modelID, userID, subTalkTitle, subTalkDescription,
                        subTalkImageURL, date));
              });
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to query for subtalk with id " + modelID, e);
    }
  }

  @Override
  public boolean update(SubTalk model) throws RepositoryException {
    try {
      SqlParameterSource namedParameters = new MapSqlParameterSource()
          .addValue("creatorID", model.getUserID())
          .addValue("subTalkID", model.getSubTalkID())
          .addValue("subTalkTitle", model.getSubTalkName())
          .addValue("subTalkDescription", model.getSubTalkDescription())
          .addValue("subTalkImageURL", model.getImageURL())
          .addValue("dateCreated", model.getDateCreated());
      int status = jdbcTemplate.update(
          "UPDATE SubTalks SET creatorID = :creatorID, subTalkTitle = :subTalkTitle, subTalkDescription = :subTalkDescription, subTalkImageURL = :subTalkImageURL, dateCreated = :dateCreated where subTalkID = :subTalkID",
          namedParameters);
      return status != 0;
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to update subtalk with id " + model.getSubTalkID(), e);
    }
  }

  @Override
  public boolean deleteByID(UUID modelID) throws RepositoryException {
    SqlParameterSource namedParameters = new MapSqlParameterSource()
        .addValue("id", modelID);
    try {
      int status =
          jdbcTemplate.update("DELETE FROM SubTalks WHERE subTalkID = :id", namedParameters);
      return status != 0;
    } catch (DataAccessException e) {
      throw new RepositoryException("Could not delete subtalk with id " + modelID);
    }
  }

  @Override
  public List<SubTalk> getAllModels() throws RepositoryException {
    try {
      List<SubTalk> subs;
      subs = jdbcTemplate
          .query(
              "SELECT subTalkID, creatorID, subTalkTitle, subTalkDescription, subTalkImageURL, dateCreated FROM SubTalks",
              (resultSet, i) -> {
                UUID subID = resultSet.getObject("subTalkID", UUID.class);
                UUID userID = resultSet.getObject("creatorID", UUID.class);
                Date date = resultSet.getTimestamp("dateCreated");
                String subTalkDescription = resultSet.getString("subTalkDescription");
                String subTalkTitle = resultSet.getString("subTalkTitle");
                String subTalkImageURL = resultSet.getString("subTalkImageURL");
                return new SubTalk(subID, userID, subTalkTitle, subTalkDescription, subTalkImageURL,
                    date);
              });
      return subs;
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to get all subtalks", e);
    }
  }
}
