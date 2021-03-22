import { CommentOutlined } from "@ant-design/icons";
import { Modal } from "@material-ui/core";
import { Card, Divider } from "antd";
import moment from "moment";
import { useContext, useEffect, useState } from "react";
import { Comment, context, Post, PostVote } from "../../App";
import { StyledArrowDownward, StyledArrowUpward } from "../../App.styles";
import { DeleteModal } from "../Modals/DeleteModal";
// import { formatDate, formatTime } from "../../resources/DateFunctions";
import {
  DeleteButton,
  // H3,
  DeleteButtonContainer,
  FlatPostRowWrapper,
  HighlightedUserName,
  PostRowContainer,
  PostRowContentWrapper,
  PostRowMain,
  PostRowMainContent,
  PostRowMetadata,
  PostRowMetadataComment,
  PostRowMetadataCommentIcon,
  PostRowMetadataDate,
  PostRowTitle,
  PostRowVotes,
  VoteButton,
} from "./PostRow.styles";
import { FocusLink, SubLink } from "../../App.styles";
import { fetchWrapper } from "../../resources/FetchWrapper";

interface Props {
  post: Post;
}

type VoteOption = "UPVOTE" | "DOWNVOTE" | null;

export const PostRow: React.FC<Props> = ({ post }) => {
  const { users } = useContext(context)!;

  //TODO already voted? from DB
  const [voted, setVoted] = useState<VoteOption>(null);
  const [votes, setVotes] = useState<PostVote[] | undefined>(undefined);
  const [numberOfVotes, setNumberOfVotes] = useState<number>(0);
  const [comments, setComments] = useState<Comment[] | undefined>(undefined);
  const { loggedInUser } = useContext(context)!;

  const [deleteModal, setDeleteModal] = useState<boolean>(false);

  const sub = useContext(context)!.subs.find(
    (sub) => sub.subTalkID === post.subID
  );

  const vote = (vote: "UPVOTE" | "DOWNVOTE") => {
    setVoted(vote);
    setNumberOfVotes(vote === "UPVOTE" ? numberOfVotes + 1 : numberOfVotes - 1);
    fetchWrapper("/api/postvote", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        postID: post.postID,
        userID: loggedInUser.userID,
        voteType: vote,
      }),
    })
      .then((response) =>
        setVotes([
          ...votes!,
          {
            postID: post.postID,
            userID: loggedInUser.userID,
            voteID: response.headers.get("Location")!.split("/").pop()!,
            voteType: vote,
          },
        ])
      )
      .catch(() => {});
  };

  const cancelVote = () => {
    setVoted(null);
    setVotes(
      votes!.filter(
        (vote) =>
          !(vote.userID === loggedInUser.userID && vote.postID === post.postID)
      )
    );
    const voteID = votes!.find(
      (vote) =>
        vote.postID === post.postID && vote.userID === loggedInUser.userID
    )?.voteID;
    fetchWrapper(`/api/postvote/${voteID}`, {
      method: "DELETE",
    });
  };

  useEffect(() => {
    const fetchVotes = () => {
      fetchWrapper(`/api/postvote/bypost/${post.postID}`, {
        method: "GET",
      })
        .then((response) => {
          if (response.status === 500) throw new Error("Error loading votes");
          else return response.json();
        })
        .then((votes: PostVote[]) => {
          setVotes(votes);
        })
        .catch(() => {
          console.log("Error loading votes");
        });
    };

    const fetchComments = () => {
      fetchWrapper(`/api/comment/bypost/${post.postID}`, {
        method: "GET",
      })
        .then((response) => {
          if (response.status === 500)
            throw new Error("Error loading comments");
          else return response.json();
        })
        .then((comments: Comment[]) => {
          setComments(comments);
        })
        .catch(() => console.log("Error loading comments"));
    };

    fetchVotes();
    fetchComments();
  }, [post]);

  useEffect(() => {
    const vote: PostVote | undefined = votes?.find(
      (vote) =>
        vote.postID === post.postID && vote.userID === loggedInUser.userID
    );
    setVoted(vote ? vote.voteType : null);
    setNumberOfVotes(
      votes
        ? votes?.filter(
            (vote) => vote.postID === post.postID && vote.voteType === "UPVOTE"
          ).length -
            votes?.filter(
              (vote) =>
                vote.postID === post.postID && vote.voteType === "DOWNVOTE"
            ).length
        : 0
    );
  }, [votes, loggedInUser, post]);

  return comments && votes ? (
    <Card
      style={{
        width: "100%",
        marginBottom: "2em",
        boxShadow:
          "0px 5px 6px -5px rgba(151,163,184,.5), 0px 15px 12px -15px rgba(151,163,184,.5), 0px 25px 18px -25px rgba(151,163,184,.5)",
      }}
    >
      <FlatPostRowWrapper>
        <PostRowVotes>
          <VoteButton
            onClick={voted === "DOWNVOTE" ? cancelVote : () => vote("UPVOTE")}
            disabled={voted === "UPVOTE"}
            aria-label="Upvote"
          >
            <StyledArrowUpward />
          </VoteButton>
          {numberOfVotes}
          <VoteButton
            onClick={voted === "UPVOTE" ? cancelVote : () => vote("DOWNVOTE")}
            disabled={voted === "DOWNVOTE"}
            aria-label="Downvote"
          >
            <StyledArrowDownward />
          </VoteButton>
        </PostRowVotes>
        <FocusLink to={`/post/${post.postID}`} style={{ overflow: "auto" }}>
          <PostRowMain>
            <PostRowContentWrapper>
              <PostRowMainContent>
                <PostRowTitle>
                  <strong>{post.postTitle}</strong>
                </PostRowTitle>
                <div>
                  {post.post.slice(0, 300).trim()}
                  {post.post.length > 300 && "..."}
                </div>
              </PostRowMainContent>
              <Divider
                style={{
                  margin: "0.75em 0",
                }}
              />
              <PostRowMetadata>
                <p>
                  u/
                  <SubLink
                    to={`/user/${
                      users?.find((user) => user.userID === post.userID)?.userID
                    }`}
                  >
                    <HighlightedUserName>
                      {
                        users?.find((user) => user.userID === post.userID)
                          ?.userName
                      }
                    </HighlightedUserName>
                  </SubLink>{" "}
                  in <span />
                  <SubLink to={`/sub/${sub?.subTalkID}`}>
                    <HighlightedUserName>
                      {sub?.subTalkName}
                    </HighlightedUserName>
                  </SubLink>
                </p>
                <PostRowMetadataDate>
                  {moment(new Date(post.dateCreated)).fromNow()}
                </PostRowMetadataDate>
                <PostRowMetadataComment>
                  <PostRowMetadataCommentIcon>
                    <CommentOutlined />
                  </PostRowMetadataCommentIcon>
                  <p>{comments!.length}</p>
                </PostRowMetadataComment>
              </PostRowMetadata>
            </PostRowContentWrapper>
          </PostRowMain>
        </FocusLink>
        <DeleteButtonContainer>
          <DeleteButton
            aria-label="Delete"
            onClick={() => setDeleteModal(true)}
            disabled={post.userID !== loggedInUser.userID}
          >
            &times;
          </DeleteButton>
        </DeleteButtonContainer>
        <Modal
          open={deleteModal}
          onClose={() => setDeleteModal(false)}
          disableBackdropClick
          disableEscapeKeyDown
        >
          <DeleteModal
            screen="home"
            sub={sub!}
            setModalOpen={setDeleteModal}
            id={post.postID}
            toDelete="post"
          />
        </Modal>
      </FlatPostRowWrapper>
    </Card>
  ) : (
    <PostRowContainer></PostRowContainer>
  );
};
