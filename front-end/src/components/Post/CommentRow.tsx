import { Modal } from "@material-ui/core";
import moment from "moment";
import { useContext, useEffect, useState } from "react";
import { Comment, CommentVote, context } from "../../App";
import {
  StyledArrowDownward,
  StyledArrowUpward,
  SubLink,
} from "../../App.styles";
import { Loading } from "../../Loading";
import { fetchWrapper } from "../../resources/FetchWrapper";
import { DeleteModal } from "../Modals/DeleteModal";
import { HighlightedUserName, VoteButton } from "../SubTalk/PostRow.styles";
import {
  CommentContainer,
  CommentContentWrapper,
  CommentDate,
  CommentDeleteButton,
  CommentMetadata,
  CommentVotesSection,
  CommentWrapper,
  UserPhoto,
  UserProfileWrapper,
} from "./Comments.styles";

interface Props {
  comment: Comment;
}

type VoteOption = "UPVOTE" | "DOWNVOTE" | null;

export const CommentRow: React.FC<Props> = ({ comment }) => {
  const user = useContext(context)!.users?.find(
    (user) => user.userID === comment.userID
  )?.userName;

  const [votes, setVotes] = useState<CommentVote[] | undefined>(undefined);
  const [numberOfVotes, setNumberOfVotes] = useState<number>(0);
  const { loggedInUser } = useContext(context)!;
  const [voted, setVoted] = useState<VoteOption>(null);

  const [deleteModal, setDeleteModal] = useState<boolean>(false);

  const vote = (vote: "UPVOTE" | "DOWNVOTE") => {
    setNumberOfVotes(vote === "UPVOTE" ? numberOfVotes + 1 : numberOfVotes - 1);
    fetchWrapper("/api/commentvote", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        commentID: comment.commentID,
        userID: loggedInUser.userID,
        voteType: vote,
      }),
    })
      .then((response) =>
        setVotes([
          ...votes!,
          {
            commentID: comment.commentID,
            userID: loggedInUser.userID,
            voteID: response?.headers.get("Location")!.split("/").pop()!,
            voteType: vote,
          },
        ])
      )
      .catch(() => {});
    setVoted(vote);
  };

  const cancelVote = () => {
    setVoted(null);
    setVotes(
      votes!.filter(
        (vote) =>
          !(
            vote.userID === loggedInUser.userID &&
            vote.commentID === comment.commentID
          )
      )
    );
    const voteID = votes!.find(
      (vote) =>
        vote.commentID === comment.commentID &&
        vote.userID === loggedInUser.userID
    )?.voteID;
    fetchWrapper(`/api/commentvote/${voteID}`, {
      method: "DELETE",
    });
  };

  useEffect(() => {
    const fetchVotes = () => {
      fetchWrapper(`/api/commentvote/bycomment/${comment.commentID}`, {
        method: "GET",
      })
        .then((response) => {
          if (response?.status === 500) {
            throw new Error("Error loading comment votes");
          } else {
            return response?.json();
          }
        })
        .then((votes: CommentVote[]) => setVotes(votes))
        .catch();
    };

    fetchVotes();
  }, [comment]);
  useEffect(() => {
    const vote: CommentVote | undefined = votes?.find(
      (vote) =>
        vote.commentID === comment.commentID &&
        vote.userID === loggedInUser.userID
    );
    setVoted(vote ? vote.voteType : null);
    setNumberOfVotes(
      votes
        ? votes?.filter(
            (vote) =>
              vote.commentID === comment.commentID && vote.voteType === "UPVOTE"
          ).length -
            votes?.filter(
              (vote) =>
                vote.commentID === comment.commentID &&
                vote.voteType === "DOWNVOTE"
            ).length
        : 0
    );
  }, [votes, comment, loggedInUser]);

  return (
    <CommentContainer>
      <CommentVotesSection>
        <VoteButton
          onClick={voted === "DOWNVOTE" ? cancelVote : () => vote("UPVOTE")}
          disabled={voted === "UPVOTE"}
          data-testid="upvote-button"
          aria-label="upvote"
        >
          <StyledArrowUpward />
        </VoteButton>
        {votes ? numberOfVotes : <Loading />}
        <VoteButton
          onClick={voted === "UPVOTE" ? cancelVote : () => vote("DOWNVOTE")}
          disabled={voted === "DOWNVOTE"}
          aria-label="Downvote"
        >
          <StyledArrowDownward />
        </VoteButton>
      </CommentVotesSection>
      <CommentContentWrapper>
        <CommentDeleteButton
          aria-label="Close"
          onClick={() => setDeleteModal(true)}
          disabled={comment.userID !== loggedInUser.userID}
        >
          &times;
        </CommentDeleteButton>
        <Modal
          open={deleteModal}
          onClose={() => setDeleteModal(false)}
          disableBackdropClick
          disableEscapeKeyDown
        >
          <div>
            <DeleteModal
            screen = "comment"
              setModalOpen={setDeleteModal}
              id={comment.commentID}
              toDelete="comment"
            />
          </div>
        </Modal>
        <CommentMetadata>
          <UserProfileWrapper>
            <UserPhoto alt="user" src="/logo192.png" />{" "}
            
              u/<SubLink to={`/user/${comment.userID}`}><HighlightedUserName>{user}</HighlightedUserName>
            </SubLink>
          </UserProfileWrapper>
          <CommentDate>{moment(new Date(comment.date)).fromNow()}</CommentDate>
        </CommentMetadata>
        <CommentWrapper>{comment.comment}</CommentWrapper>
      </CommentContentWrapper>
    </CommentContainer>
  );
};
