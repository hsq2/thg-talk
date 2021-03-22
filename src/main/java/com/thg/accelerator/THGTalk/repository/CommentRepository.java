package com.thg.accelerator.THGTalk.repository;

import com.thg.accelerator.THGTalk.exceptions.RepositoryException;
import com.thg.accelerator.THGTalk.model.Comment;
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
public class CommentRepository implements ICRUDRepository<Comment> {

  @Autowired
  NamedParameterJdbcTemplate jdbcTemplate;

  @Override
  public boolean save(Comment model) throws RepositoryException {
    try {
      SqlParameterSource namedParameters = new MapSqlParameterSource()
          .addValue("commentID", model.getCommentID())
          .addValue("userID", model.getUserID())
          .addValue("postID", model.getPostID())
          .addValue("commentContent", model.getComment())
          .addValue("dateCreated", model.getDate());

      if (!getModelByID(model.getCommentID()).isPresent()) {
        jdbcTemplate.update(
            "INSERT INTO Comments (commentID, userID, postID, commentContent, dateCreated) VALUES (:commentID, :userID, :postID, :commentContent, :dateCreated)",
            namedParameters);
        return true;
      }
      return false;
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to insert comment with id " + model.getCommentID(), e);
    }
  }

  @Override
  public Optional<Comment> getModelByID(UUID modelID) throws RepositoryException {
    try {
      SqlParameterSource namedParameters = new MapSqlParameterSource()
          .addValue("id", modelID);
      return jdbcTemplate
          .queryForObject(
              "SELECT commentID, userID, postID, commentContent, dateCreated "
                  + "FROM Comments WHERE commentID=:id  ",
              namedParameters,
              (resultSet, i) -> {
                UUID userID = resultSet.getObject("userID", UUID.class);
                UUID postID = resultSet.getObject("postID", UUID.class);
                Date date = resultSet.getTimestamp("dateCreated");
                String commentContent = resultSet.getString("commentContent");
                return Optional.of(new Comment(modelID, postID, userID, commentContent, date));
              });
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to query for comment with id " + modelID, e);
    }
  }

  @Override
  public boolean update(Comment model) throws RepositoryException {
    try {
      SqlParameterSource namedParameters = new MapSqlParameterSource()
          .addValue("commentID", model.getCommentID())
          .addValue("userID", model.getUserID())
          .addValue("postID", model.getPostID())
          .addValue("commentContent", model.getComment())
          .addValue("dateCreated", model.getDate());
      int status = jdbcTemplate.update(
          "UPDATE Comments SET userID = :userID, postID = :postID, commentContent = :commentContent, dateCreated = :dateCreated where commentID = :commentID",
          namedParameters);
      return status != 0;
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to update comment with id " + model.getCommentID(), e);
    }
  }

  @Override
  public boolean deleteByID(UUID modelID) throws RepositoryException {
    SqlParameterSource namedParameters = new MapSqlParameterSource()
        .addValue("id", modelID);
    try {
      int status =
          jdbcTemplate.update("DELETE FROM Comments WHERE commentID = :id", namedParameters);
      return status != 0;
    } catch (DataAccessException e) {
      throw new RepositoryException("Could not delete comment with id " + modelID);
    }
  }

  @Override
  public List<Comment> getAllModels() throws RepositoryException {
    try {
      List<Comment> comments;
      comments = jdbcTemplate
          .query(
              "SELECT commentID, userID, postID, commentContent, dateCreated FROM Comments",
              (resultSet, i) -> {
                UUID commentID = resultSet.getObject("commentID", UUID.class);
                UUID userID = resultSet.getObject("userID", UUID.class);
                UUID postID = resultSet.getObject("postID", UUID.class);
                Date date = resultSet.getTimestamp("dateCreated");
                String commentContent = resultSet.getString("commentContent");
                return new Comment(commentID, postID, userID, commentContent, date);
              });
      return comments;
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to get all comments", e);
    }
  }

  public List<Comment> getCommentsByPostID(UUID postID) throws RepositoryException {
    try {
      SqlParameterSource namedParameters = new MapSqlParameterSource()
          .addValue("id", postID);
      List<Comment> comments;
      comments = jdbcTemplate
          .query(
              "SELECT commentID, userID, postID, commentContent, dateCreated "
                  + "FROM Comments WHERE postID=:id  ",
              namedParameters,
              (resultSet, i) -> {
                UUID commentID = resultSet.getObject("commentID", UUID.class);
                UUID userID = resultSet.getObject("userID", UUID.class);
                Date date = resultSet.getTimestamp("dateCreated");
                String commentContent = resultSet.getString("commentContent");
                return new Comment(commentID, postID, userID, commentContent, date);
              });
      return comments;
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to get all comments by post id " + postID, e);
    }
  }

  public List<Comment> getCommentsByUserID(UUID userID) throws RepositoryException {
    try {
      SqlParameterSource namedParameters = new MapSqlParameterSource()
          .addValue("id", userID);
      List<Comment> comments;
      comments = jdbcTemplate
          .query(
              "SELECT commentID, userID, postID, commentContent, dateCreated "
                  + "FROM Comments WHERE userID=:id  ",
              namedParameters,
              (resultSet, i) -> {
                UUID commentID = resultSet.getObject("commentID", UUID.class);
                UUID postID = resultSet.getObject("postID", UUID.class);
                Date date = resultSet.getTimestamp("dateCreated");
                String commentContent = resultSet.getString("commentContent");
                return new Comment(commentID, postID, userID, commentContent, date);
              });
      return comments;
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to get all comments by user id " + userID, e);
    }
  }

}
