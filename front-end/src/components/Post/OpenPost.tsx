import { CommentOutlined } from "@ant-design/icons";
import { Modal } from "@material-ui/core";
import { Card, Divider, PageHeader } from "antd";
import moment from "moment";
import React, { useContext, useEffect, useState } from "react";
import { useHistory, useParams } from "react-router-dom";
import { Comment, context, Post, PostVote } from "../../App";
import {
  StyledArrowDownward,
  StyledArrowUpward,
  SubLink,
} from "../../App.styles";
import { Loading } from "../../Loading";
import { NotFound } from "../../NotFound";
import { fetchWrapper } from "../../resources/FetchWrapper";
import { PageWrapperSingle } from "../HomePage/Homepage.styles";
import { DeleteModal } from "../Modals/DeleteModal";
import {
  DeleteButton,
  FlatPostRowWrapper,
  HighlightedUserName,
  PostRowContentWrapper,
  PostRowMain,
  PostRowMainContentLarge,
  PostRowMetadata,
  PostRowMetadataComment,
  PostRowMetadataCommentIcon,
  PostRowMetadataDate,
  PostRowTitle,
  PostRowVotes,
  VoteButton,
} from "../SubTalk/PostRow.styles";
import { AddCommentSection } from "./AddCommentSection";
import { CommentRow } from "./CommentRow";
import { CommentList } from "./Comments.styles";
import { Comments, PostDeleteButtonContainer } from "./OpenPost.styles";

type Params = {
  id: string;
};

type VoteOption = "UPVOTE" | "DOWNVOTE" | null;

export const OpenPost: React.FC = () => {
  const [post, setPost] = useState<Post | null | undefined>(undefined);
  const [comments, setComments] = useState<Comment[] | null | undefined>(
    undefined
  );
  const [votes, setVotes] = useState<PostVote[] | null | undefined>(undefined);
  const [numberOfVotes, setNumberOfVotes] = useState<number>(0);

  const [deleteModal, setDeleteModal] = useState<boolean>(false);

  const [voted, setVoted] = useState<VoteOption>(null);

  const id = useParams<Params>().id || 0;

  const { loggedInUser, users } = useContext(context)!;
  const sub = useContext(context)!.subs.find(
    (sub) => sub.subTalkID === post?.subID
  );

  const history = useHistory();

  useEffect(() => {
    const fetchPost = () => {
      fetchWrapper(`/api/post/${id}`, {
        method: "GET",
      })
        .then((response) => {
          if (!response?.ok) throw new Error("Post not found");
          else return response?.json();
        })
        .then((data) => setPost(data))
        .catch(() => {
          console.log("Post not found");
          setPost(null);
        });
    };

    fetchPost();
  }, [id]);

  const vote = (vote: "UPVOTE" | "DOWNVOTE") => {
    setNumberOfVotes(vote === "UPVOTE" ? numberOfVotes + 1 : numberOfVotes - 1);
    fetchWrapper("/api/postvote", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        postID: post!.postID,
        userID: loggedInUser.userID,
        voteType: vote,
      }),
    }).then((response) =>
      setVotes([
        ...votes!,
        {
          postID: post!.postID,
          userID: loggedInUser.userID,
          voteID: response?.headers.get("Location")!.split("/").pop()!,
          voteType: vote,
        },
      ])
    );
    setVoted(vote);
  };

  const cancelVote = () => {
    setVoted(null);
    setVotes(
      votes!.filter(
        (vote) =>
          !(vote.userID === loggedInUser.userID && vote.postID === post!.postID)
      )
    );
    const voteID = votes!.find(
      (vote) =>
        vote.postID === post!.postID && vote.userID === loggedInUser.userID
    )?.voteID;
    fetchWrapper(`/api/postvote/${voteID}`, {
      method: "DELETE",
    });
  };

  useEffect(() => {
    const fetchVotes = () => {
      fetchWrapper(`/api/postvote/bypost/${id}`, {
        method: "GET",
      })
        .then((response) => {
          if (!response?.ok) throw new Error("Error loading votes");
          else return response?.json();
        })
        .then((votes: PostVote[]) => {
          setVotes(votes);
        })
        .catch(() => {
          console.log("Error loading votes");
          setVotes(null);
        });
    };

    const fetchComments = () => {
      fetchWrapper(`/api/comment/bypost/${id}`, {
        method: "GET",
      })
        .then((response) => {
          if (!response.ok) throw new Error("Post not found");
          else return response.json();
        })
        .then((comments: Comment[]) => {
          setComments(comments);
        })
        .catch(() => {
          console.log("Cannot load comments");
          setComments(null);
        });
    };

    fetchVotes();
    fetchComments();
  }, [post, id]);

  useEffect(() => {
    console.log(votes);

    const vote: PostVote | undefined = votes?.find(
      (vote) =>
        vote.postID === post?.postID && vote.userID === loggedInUser.userID
    );
    setVoted(vote ? vote.voteType : null);
    setNumberOfVotes(
      votes
        ? votes?.filter(
            (vote) => vote.postID === post?.postID && vote.voteType === "UPVOTE"
          ).length -
            votes?.filter(
              (vote) =>
                vote.postID === post?.postID && vote.voteType === "DOWNVOTE"
            ).length
        : 0
    );
  }, [votes, post, loggedInUser]);

  switch (post) {
    case undefined:
      return <Loading />;
    case null:
      return <NotFound />;
    default:
      return votes && comments ? (
        <PageWrapperSingle>
          <div>
            <PageHeader
              className="site-page-header"
              onBack={() => history.push(`/sub/${sub?.subTalkID}`)}
              title={sub?.subTalkName}
              subTitle="Go to the SubTalk homepage"
            />
            <Card
              style={{
                width: "60em",
                marginBottom: "2em",
                padding: "1em",
                boxShadow:
                  "0px 5px 6px -5px rgba(151,163,184,.5), 0px 15px 12px -15px rgba(151,163,184,.5), 0px 25px 18px -25px rgba(151,163,184,.5)",
              }}
            >
              <FlatPostRowWrapper>
                <PostDeleteButtonContainer>
                  <DeleteButton
                    aria-label="Close"
                    onClick={() => setDeleteModal(true)}
                    disabled={post.userID !== loggedInUser.userID}
                  >
                    &times;
                  </DeleteButton>
                </PostDeleteButtonContainer>
                <Modal
                  open={deleteModal}
                  onClose={() => setDeleteModal(false)}
                  disableBackdropClick
                  disableEscapeKeyDown
                >
                  <DeleteModal
                    screen="post"
                    sub={sub!}
                    setModalOpen={setDeleteModal}
                    id={post.postID}
                    toDelete="post"
                  />
                </Modal>
                <PostRowVotes>
                  <VoteButton
                    onClick={
                      voted === "DOWNVOTE" ? cancelVote : () => vote("UPVOTE")
                    }
                    disabled={voted === "UPVOTE"}
                    aria-label="Upvote"
                  >
                    <StyledArrowUpward />
                  </VoteButton>
                  {numberOfVotes}
                  <VoteButton
                    onClick={
                      voted === "UPVOTE" ? cancelVote : () => vote("DOWNVOTE")
                    }
                    disabled={voted === "DOWNVOTE"}
                    aria-label="Downvote"
                  >
                    <StyledArrowDownward />
                  </VoteButton>
                </PostRowVotes>
                <PostRowMain>
                  <PostRowContentWrapper>
                    <PostRowMainContentLarge>
                      <PostRowTitle>
                        <strong>{post.postTitle}</strong>
                      </PostRowTitle>
                      <div style={{ overflowWrap: "break-word" }}>
                        {post.post}
                      </div>
                    </PostRowMainContentLarge>
                    <PostRowMetadata>
                      <p>
                        u/
                        <SubLink
                          to={`/user/${
                            users?.find((user) => user.userID === post.userID)
                              ?.userID
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
                    <Divider
                      style={{
                        margin: "0 0 1em 0",
                      }}
                    />
                    <Comments>
                      {comments ? (
                        <>
                          <CommentList>
                            {comments.map((comment) => {
                              return <CommentRow comment={comment} />;
                            })}
                          </CommentList>
                        </>
                      ) : (
                        <Loading />
                      )}
                    </Comments>
                    <AddCommentSection
                      comments={comments}
                      setComments={setComments}
                      post={post}
                    />
                  </PostRowContentWrapper>
                </PostRowMain>
              </FlatPostRowWrapper>
            </Card>
          </div>
        </PageWrapperSingle>
      ) : votes === null || comments === null ? (
        <NotFound />
      ) : (
        <Loading />
      );
  }
};
