import React, {
  ChangeEvent,
  Dispatch,
  SetStateAction,
  useContext,
  useState,
} from "react";
import { Comment, context, Post } from "../../App";
import { Avatar, Button, Comment as CommentComponent, Form, Input } from "antd";
import { ButtonContainer } from "../../App.styles";
import { fetchWrapper } from "../../resources/FetchWrapper";

interface Props {
  comments: Comment[];
  setComments: Dispatch<SetStateAction<Comment[] | null | undefined>>;
  post: Post;
}

const { TextArea } = Input;

// @ts-ignore
const Editor = ({ onChange, onSubmit, submitting, value, disabled }) => (
  <>
    <Form.Item>
      <TextArea
        rows={4}
        onChange={onChange}
        value={value}
        aria-label="Add comment"
      />
    </Form.Item>
    <Form.Item>
      <ButtonContainer>
        <Button
          htmlType="submit"
          loading={submitting}
          onClick={onSubmit}
          type="primary"
          disabled={disabled}
          style={{ color: "white" }}
        >
          Add Comment
        </Button>
      </ButtonContainer>
    </Form.Item>
  </>
);

export const AddCommentSection: React.FC<Props> = ({
  comments,
  setComments,
  post,
}) => {
  const [commentBody, setCommentBody] = useState<string>("");
  const [submitStatus, setSubmitStatus] = useState<boolean>(false);
  const { loggedInUser } = useContext(context)!;

  const addComment = () => {
    setSubmitStatus(true);
    const date = Date.now();
    fetchWrapper("/api/comment", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        comment: commentBody,
        date,
        postID: post.postID,
        userID: loggedInUser.userID,
      }),
    })
      .then((response) =>
        setComments([
          ...comments,
          {
            commentID: response?.headers.get("Location")!.split("/").pop()!,
            userID: loggedInUser.userID,
            postID: post.postID,
            comment: commentBody,
            date: date,
          },
        ])
      )
      .then(() => {
        setSubmitStatus(false);
        setCommentBody("");
      });
  };

  return (
    <>
      <CommentComponent
        avatar={<Avatar src="/logo192.png" alt="React logo" />}
        data-testid="add-comment-field"
        content={
          <Editor
            disabled={!commentBody}
            onChange={(event: ChangeEvent<HTMLInputElement>) =>
              setCommentBody(event.target.value)
            }
            onSubmit={addComment}
            submitting={submitStatus}
            value={commentBody}
          />
        }
      />
    </>
  );
};
