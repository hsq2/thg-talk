import { fireEvent } from "@testing-library/react";
import React from "react";
import { Comment, CommentVote } from "../../../App";
import { renderWrapper } from "../../../resources/testing/RenderWrapper";
import { CommentRow } from "../CommentRow";

describe("Comment row", () => {
  const comment: Comment = {
    commentID: "1",
    comment: "",
    userID: "1",
    date: 1,
    postID: "1",
  };

  const vote: CommentVote = {
    commentID: "1",
    userID: "1",
    voteType: "UPVOTE",
    voteID: "1",
  };

  it("renders correctly", () => {
    const { container } = renderWrapper(<CommentRow comment={comment} />);
    expect(container).toMatchSnapshot();
  });

  it("makes get requests on load", () => {
    const mockFetch = jest.spyOn(global, "fetch").mockReturnValue(
      new Promise((resolve, reject) => {
        resolve(
          new Response(new Blob(), {
            status: 200,
          })
        ),
          reject();
      })
    );
    renderWrapper(<CommentRow comment={comment} />);
    expect(mockFetch).toHaveBeenCalledWith(
      "/api/commentvote/bycomment/1",
      expect.objectContaining({ method: "GET" })
    );
  });

  it("makes post request on vote", () => {
    const mockFetch = jest.spyOn(global, "fetch").mockReturnValue(
      new Promise((resolve, reject) => {
        resolve(
          new Response(new Blob(), {
            status: 200,
            headers: { Location: "/1" },
          })
        ),
          reject();
      })
    );
    const { getByTestId } = renderWrapper(<CommentRow comment={comment} />);
    const upvoteButton = getByTestId("upvote-button");

    fireEvent.click(upvoteButton);

    expect(mockFetch).toHaveBeenCalledWith(
      "/api/commentvote",
      expect.objectContaining({ method: "POST" })
    );
  });
});
