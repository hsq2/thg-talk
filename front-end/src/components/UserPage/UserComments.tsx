import { RightOutlined } from "@ant-design/icons";
import { FC } from "react";
import { Comment } from "../../App";
import { FocusLink } from "../../App.styles";
import { CommentRow } from "../Post/CommentRow";
import {
  LinkWrapper,
  UserCommentRow,
  UserCommentsWrapper,
} from "./UserPage.styles";

interface Props {
  comments: Comment[] | undefined;
}

export const UserComments: FC<Props> = ({ comments }) => {
  return (
    <UserCommentsWrapper>
      {comments?.map((comment) => (
        <UserCommentRow>
          <CommentRow comment={comment} />
          <LinkWrapper>
            <FocusLink to={`/post/${comment.postID}`}>
              Go to post <RightOutlined />
            </FocusLink>
          </LinkWrapper>
        </UserCommentRow>
      ))}
    </UserCommentsWrapper>
  );
};
