package com.thg.accelerator.THGTalk.repository;

import com.thg.accelerator.THGTalk.exceptions.RepositoryException;
import com.thg.accelerator.THGTalk.model.CommentVote;
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
public class CommentVoteRepository implements ICRUDRepository<CommentVote> {

  @Autowired
  NamedParameterJdbcTemplate jdbcTemplate;


  @Override
  public boolean save(CommentVote model) throws RepositoryException {
    try {
      SqlParameterSource namedParameters = new MapSqlParameterSource()
          .addValue("commentID", model.getCommentID())
          .addValue("voteID", model.getVoteID())
          .addValue("voteType", model.getVoteType().getType())
          .addValue("userID", model.getUserID());
      if (!getModelByID(model.getVoteID()).isPresent()) {
        int status1 = jdbcTemplate.update(
            "INSERT INTO Votes (voteID, userID, vote) VALUES (:voteID, :userID, :voteType::VOTE)",
            namedParameters);
        int status2 = jdbcTemplate.update(
            "INSERT INTO VotesComments (voteID, commentID) VALUES (:voteID, :commentID)",
            namedParameters);
        return (status1 != 0 && status2 != 0); //check
      }
      return false;
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to insert comment vote with id " + model.getVoteID(),
          e);
    }
  }

  @Override
  public Optional<CommentVote> getModelByID(UUID modelID) throws RepositoryException {
    try {
      SqlParameterSource namedParameters = new MapSqlParameterSource()
          .addValue("id", modelID);
      return jdbcTemplate.queryForObject(
          "SELECT Votes.voteID, userID, vote, commentID "
              + "FROM Votes "
              + "JOIN VotesComments ON VotesComments.voteID = Votes.voteID "
              + "WHERE Votes.voteID=:id  ",
          namedParameters,
          (resultSet, i) -> {
            UUID userID = resultSet.getObject("userID", UUID.class);
            UUID commentID = resultSet.getObject("commentID", UUID.class);
            String voteType = resultSet.getString("vote");
            return Optional
                .of(new CommentVote(modelID, VoteEnum.valueOf(voteType), userID, commentID));
          });
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to query for comment vote with id " + modelID, e);
    }
  }

  @Override
  public boolean update(CommentVote model) throws RepositoryException {
    try {
      SqlParameterSource namedParameters = new MapSqlParameterSource()
          .addValue("commentID", model.getCommentID())
          .addValue("voteID", model.getVoteID())
          .addValue("voteType", model.getVoteType().getType())
          .addValue("userID", model.getUserID());
      int status1 = jdbcTemplate.update(
          "UPDATE Votes SET userID = :userID, vote = :voteType::VOTE where voteID = :voteID",
          namedParameters);
      int status2 = jdbcTemplate.update(
          "UPDATE VotesComments SET commentID = :commentID where voteID = :voteID",
          namedParameters);
      return (status1 != 0 && status2 != 0);
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to update comment vote with id " + model.getVoteID(),
          e);
    }
  }

  @Override
  public boolean deleteByID(UUID modelID) throws RepositoryException {
    SqlParameterSource namedParameters = new MapSqlParameterSource()
        .addValue("id", modelID);
    try {
      int status2 =
          jdbcTemplate.update("DELETE FROM VotesComments WHERE voteID = :id", namedParameters);
      int status1 =
          jdbcTemplate.update("DELETE FROM Votes WHERE voteID = :id", namedParameters);
      return (status1 != 0 && status2 != 0);
    } catch (DataAccessException e) {
      throw new RepositoryException("Could not delete comment vote with id " + modelID);
    }
  }

  @Override
  public List<CommentVote> getAllModels() throws RepositoryException {
    try {
      List<CommentVote> commentVotes;
      commentVotes = jdbcTemplate
          .query(
              "SELECT Votes.voteID, userID, vote, commentID "
                  + "FROM Votes "
                  + "JOIN VotesComments ON VotesComments.voteID = Votes.voteID ",
              (resultSet, i) -> {
                UUID voteID = resultSet.getObject("voteID", UUID.class);
                UUID userID = resultSet.getObject("userID", UUID.class);
                UUID commentID = resultSet.getObject("commentID", UUID.class);
                String voteType = resultSet.getString("vote");
                return new CommentVote(voteID, VoteEnum.valueOf(voteType), userID, commentID);
              });
      return commentVotes;
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to get all comment votes", e);
    }
  }

  public List<CommentVote> getCommentVotesByCommentID(UUID commentID) throws RepositoryException {
    try {
      SqlParameterSource namedParameters = new MapSqlParameterSource()
          .addValue("id", commentID);
      List<CommentVote> commentVotes;
      commentVotes = jdbcTemplate
          .query(
              "SELECT Votes.voteID, userID, vote, commentID "
                  + "FROM Votes "
                  + "JOIN VotesComments ON VotesComments.voteID = Votes.voteID "
                  + "WHERE commentID = :id", namedParameters,
              (resultSet, i) -> {
                UUID voteID = resultSet.getObject("voteID", UUID.class);
                UUID userID = resultSet.getObject("userID", UUID.class);
                String voteType = resultSet.getString("vote");
                return new CommentVote(voteID, VoteEnum.valueOf(voteType), userID, commentID);
              });
      return commentVotes;
    } catch (DataAccessException e) {
      throw new RepositoryException("failed to get all comment votes by comment id " + commentID, e);
    }
  }
}
