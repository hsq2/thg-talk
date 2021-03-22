package com.thg.accelerator.THGTalk.repository;

import com.thg.accelerator.THGTalk.exceptions.RepositoryException;
import com.thg.accelerator.THGTalk.model.Comment;
import com.thg.accelerator.THGTalk.model.Post;
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
public class PostRepository implements ICRUDRepository<Post> {

  @Autowired
  NamedParameterJdbcTemplate jdbcTemplate;

  @Override
  public boolean save(Post model) throws RepositoryException {
    try {
      SqlParameterSource namedParameters = new MapSqlParameterSource()
          .addValue("postID", model.getPostID())
          .addValue("userID", model.getUserID())
          .addValue("subTalkID", model.getSubID())
          .addValue("postTitle", model.getPostTitle())
          .addValue("postContent", model.getPost())
          .addValue("dateCreated", model.getDateCreated());

      if (!getModelByID(model.getPostID()).isPresent()) {
        jdbcTemplate.update(
            "INSERT INTO Posts (postID, userID, subTalkID, postTitle, postContent, dateCreated) VALUES (:postID, :userID, :subTalkID, :postTitle, :postContent, :dateCreated)",
            namedParameters);
        return true;
      }
      return false;
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to insert post with id " + model.getPostID(), e);
    }
  }

  @Override
  public Optional<Post> getModelByID(UUID modelID) throws RepositoryException {
    try {
      SqlParameterSource namedParameters = new MapSqlParameterSource()
          .addValue("id", modelID);
      return jdbcTemplate
          .queryForObject(
              "SELECT postID, userID, subTalkID, postTitle, postContent, dateCreated "
                  + "FROM Posts WHERE postID=:id  ",
              namedParameters,
              (resultSet, i) -> {
                UUID userID = resultSet.getObject("userID", UUID.class);
                UUID subTalkID = resultSet.getObject("subTalkID", UUID.class);
                Date date = resultSet.getTimestamp("dateCreated");
                String postContent = resultSet.getString("postContent");
                String postTitle = resultSet.getString("postTitle");
                return Optional
                    .of(new Post(modelID, userID, subTalkID, postTitle, postContent, date));
              });
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to query for post with id " + modelID, e);
    }
  }

  @Override
  public boolean update(Post model) throws RepositoryException {
    try {
      SqlParameterSource namedParameters = new MapSqlParameterSource()
          .addValue("postID", model.getPostID())
          .addValue("userID", model.getUserID())
          .addValue("subTalkID", model.getSubID())
          .addValue("postTitle", model.getPostTitle())
          .addValue("postContent", model.getPost())
          .addValue("dateCreated", model.getDateCreated());
      int status = jdbcTemplate.update(
          "UPDATE Posts SET userID = :userID, subTalkID = :subTalkID, postTitle = :postTitle, postContent = :postContent, dateCreated = :dateCreated where postID = :postID",
          namedParameters);
      return status != 0;
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to update post with id " + model.getPostID(), e);
    }
  }

  @Override
  public boolean deleteByID(UUID modelID) throws RepositoryException {
    SqlParameterSource namedParameters = new MapSqlParameterSource()
        .addValue("id", modelID);
    try {
      int status =
          jdbcTemplate.update("DELETE FROM Posts WHERE postID = :id", namedParameters);
      return status != 0;
    } catch (DataAccessException e) {
      throw new RepositoryException("Could not delete post with id " + modelID);
    }
  }

  @Override
  public List<Post> getAllModels() throws RepositoryException {
    try {
      List<Post> posts;
      posts = jdbcTemplate
          .query(
              "SELECT postID, userID, subTalkID, postTitle, postContent, dateCreated FROM Posts",
              (resultSet, i) -> {
                UUID postID = resultSet.getObject("postID", UUID.class);
                UUID userID = resultSet.getObject("userID", UUID.class);
                UUID subTalkID = resultSet.getObject("subTalkID", UUID.class);
                Date date = resultSet.getTimestamp("dateCreated");
                String postContent = resultSet.getString("postContent");
                String postTitle = resultSet.getString("postTitle");
                return new Post(postID, userID, subTalkID, postTitle, postContent, date);
              });
      return posts;
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to get all posts", e);
    }
  }

  public List<Post> getPostsBySubTalkID(UUID subTalkID) throws RepositoryException {
    try {
      SqlParameterSource namedParameters = new MapSqlParameterSource()
          .addValue("id", subTalkID);
      List<Post> posts;
      posts = jdbcTemplate
          .query(
              "SELECT postID, userID, subTalkID, postTitle, postContent, dateCreated "
                  + "FROM Posts WHERE subTalkID=:id ",
              namedParameters,
              (resultSet, i) -> {
                UUID postID = resultSet.getObject("postID", UUID.class);
                UUID userID = resultSet.getObject("userID", UUID.class);
                Date date = resultSet.getTimestamp("dateCreated");
                String postTitle = resultSet.getString("postTitle");
                String postContent = resultSet.getString("postContent");
                return new Post(postID, userID, subTalkID, postTitle, postContent, date);
              });
      return posts;
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to get all posts by subtalk id " + subTalkID, e);
    }
  }

  public List<Post> getPostsByUserID(UUID userID) throws RepositoryException {
    try {
      SqlParameterSource namedParameters = new MapSqlParameterSource()
          .addValue("id", userID);
      List<Post> posts;
      posts = jdbcTemplate
          .query(
              "SELECT postID, userID, subTalkID, postTitle, postContent, dateCreated "
                  + "FROM Posts WHERE userID=:id ",
              namedParameters,
              (resultSet, i) -> {
                UUID postID = resultSet.getObject("postID", UUID.class);
                UUID subTalkID = resultSet.getObject("subTalkID", UUID.class);
                Date date = resultSet.getTimestamp("dateCreated");
                String postTitle = resultSet.getString("postTitle");
                String postContent = resultSet.getString("postContent");
                return new Post(postID, userID, subTalkID, postTitle, postContent, date);
              });
      return posts;
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to get all posts by user id " + userID, e);
    }
  }

  public List<Post> getTopPosts(int number) throws RepositoryException {
    try {
      SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("number", number);
      List<Post> posts;
      posts = jdbcTemplate.query(
          "SELECT B.postID, userID, subTalkID, postTitle, postContent, dateCreated, coalesce(A.c, 0) as c FROM (select T.pid as postId, (T.upCount - T.downCount) as c from (" +
              "select " +
              "v.postid pid, " +
              "Count(*) filter (where v2.vote = 'DOWNVOTE') as downCount, " +
              "Count(*) filter (where v2.vote = 'UPVOTE') as upCount " +
              "from " +
              "votesposts v " +
              "inner join votes v2 on " +
              "v.voteid = v2.voteid " +
              "group by " +
              "v.postid " +
              ") as T) " +
              " A RIGHT JOIN Posts B ON A.postId = B.postId Order By c DESC LIMIT :number",
          parameterSource, (resultSet, i) -> {
            UUID postID = resultSet.getObject("postID", UUID.class);
            UUID userID = resultSet.getObject("userID", UUID.class);
            UUID subTalkID = resultSet.getObject("subTalkID", UUID.class);
            Date date = resultSet.getTimestamp("dateCreated");
            String postContent = resultSet.getString("postContent");
            String postTitle = resultSet.getString("postTitle");
            return new Post(postID, userID, subTalkID, postTitle, postContent, date);
          });
      return posts;
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to get top posts", e);
    }
  }
}
