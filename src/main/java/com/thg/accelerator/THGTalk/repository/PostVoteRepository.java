package com.thg.accelerator.THGTalk.repository;

import com.thg.accelerator.THGTalk.exceptions.RepositoryException;
import com.thg.accelerator.THGTalk.model.PostVote;
import com.thg.accelerator.THGTalk.model.VoteEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PostVoteRepository implements ICRUDRepository<PostVote> {

  @Autowired
  NamedParameterJdbcTemplate jdbcTemplate;

  @Override
  public boolean save(PostVote model) throws RepositoryException {
    try {
      SqlParameterSource namedParameters = new MapSqlParameterSource()
          .addValue("postID", model.getPostID())
          .addValue("voteID", model.getVoteID())
          .addValue("voteType", model.getVoteType().getType())
          .addValue("userID", model.getUserID());

      if (!getModelByID(model.getVoteID()).isPresent()) {
        int status1 = jdbcTemplate.update(
            "INSERT INTO Votes (voteID, userID, vote) VALUES (:voteID, :userID, :voteType::VOTE)",
            namedParameters);
        int status2 = jdbcTemplate.update(
            "INSERT INTO VotesPosts (voteID, postID) VALUES (:voteID, :postID)",
            namedParameters);
        return (status1 != 0 && status2 != 0); //check
      }
      return false;
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to insert post vote with id " + model.getVoteID(), e);
    }
  }

  @Override
  public Optional<PostVote> getModelByID(UUID modelID) throws RepositoryException {
    try {
      SqlParameterSource namedParameters = new MapSqlParameterSource()
          .addValue("id", modelID);
      return jdbcTemplate.queryForObject(
          "SELECT Votes.voteID, userID, vote, postID "
              + "FROM Votes "
              + "JOIN VotesPosts ON VotesPosts.voteID = Votes.voteID "
              + "WHERE Votes.voteID=:id  ",
          namedParameters,
          (resultSet, i) -> {
            UUID userID = resultSet.getObject("userID", UUID.class);
            UUID postID = resultSet.getObject("postID", UUID.class);
            String voteType = resultSet.getString("vote");
            return Optional
                .of(new PostVote(modelID, VoteEnum.valueOf(voteType), userID, postID));
          });
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to query for post vote with id " + modelID, e);
    }
  }

  @Override
  public boolean update(PostVote model) throws RepositoryException {
    try {
      SqlParameterSource namedParameters = new MapSqlParameterSource()
          .addValue("postID", model.getPostID())
          .addValue("voteID", model.getVoteID())
          .addValue("voteType", model.getVoteType().getType())
          .addValue("userID", model.getUserID());
      int status1 = jdbcTemplate.update(
          "UPDATE Votes SET userID = :userID, vote = :voteType::VOTE where voteID = :voteID",
          namedParameters);
      int status2 = jdbcTemplate.update(
          "UPDATE VotesPosts SET postID = :postID where voteID = :voteID",
          namedParameters);
      return (status1 != 0 && status2 != 0);
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to update post vote with id " + model.getVoteID(), e);
    }
  }

  @Override
  public boolean deleteByID(UUID modelID) throws RepositoryException {
    SqlParameterSource namedParameters = new MapSqlParameterSource()
        .addValue("id", modelID);
    try {
      int status2 =
          jdbcTemplate.update("DELETE FROM VotesPosts WHERE voteID = :id", namedParameters);
      int status1 =
          jdbcTemplate.update("DELETE FROM Votes WHERE voteID = :id", namedParameters);
      return (status1 != 0 && status2 != 0);
    } catch (DataAccessException e) {
      throw new RepositoryException("Could not delete post vote with id " + modelID);
    }
  }

  @Override
  public List<PostVote> getAllModels() throws RepositoryException {
    try {
      List<PostVote> postVotes;
      postVotes = jdbcTemplate
          .query(
              "SELECT Votes.voteID, userID, vote, postID "
                  + "FROM Votes "
                  + "JOIN VotesPosts ON VotesPosts.voteID = Votes.voteID ",
              (resultSet, i) -> {
                UUID voteID = resultSet.getObject("voteID", UUID.class);
                UUID userID = resultSet.getObject("userID", UUID.class);
                UUID postID = resultSet.getObject("postID", UUID.class);
                String voteType = resultSet.getString("vote");
                return new PostVote(voteID, VoteEnum.valueOf(voteType), userID, postID);
              });
      return postVotes;
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to get all post votes", e);
    }
  }

  public List<PostVote> getPostVotesByPostID(UUID postID) throws RepositoryException {
    try {
      SqlParameterSource namedParameters = new MapSqlParameterSource()
          .addValue("id", postID);
      List<PostVote> postVotes;
      postVotes = jdbcTemplate
          .query(
              "SELECT Votes.voteID, userID, vote, postID "
                  + "FROM Votes "
                  + "JOIN VotesPosts ON VotesPosts.voteID = Votes.voteID "
                  + "WHERE postID = :id", namedParameters,
              (resultSet, i) -> {
                UUID voteID = resultSet.getObject("voteID", UUID.class);
                UUID userID = resultSet.getObject("userID", UUID.class);
                String voteType = resultSet.getString("vote");
                return new PostVote(voteID, VoteEnum.valueOf(voteType), userID, postID);
              });
      return postVotes;
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to get all post votes by post id " + postID, e);
    }
  }
}
