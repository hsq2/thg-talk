// import fetchMock from "fetch-mock";
import { RenderResult } from "@testing-library/react";
import { act } from "react-dom/test-utils";
import { Post } from "../../../App";
import { renderWrapper } from "../../../resources/testing/RenderWrapper";
import { Homepage } from "../Homepage";
let fetchMock = require("fetch-mock");

describe("homepage", () => {
  it("renders correctly", () => {
    let component: RenderResult;
    act(() => {
      component = renderWrapper(<Homepage />);
    });
    const { container } = component!;
    expect(container).toMatchSnapshot();
  });

  it("fetches posts", () => {
    const mockPost: Post = {
      postID: "postID",
      post: "post",
      userID: "userID",
      postTitle: "title",
      subID: "subID",
      dateCreated: "1",
    };
    let mockFetch = jest
      .spyOn(global, "fetch")
      .mockReturnValue(
        new Promise(
          () => new Response(JSON.stringify(mockPost), { status: 200 })
        )
      );

    act(() => {
      renderWrapper(<Homepage />);
    });
    expect(mockFetch).toHaveBeenCalledWith("/api/post/top?number=10", {
      headers: { Authorization: "Bearer undefined" },
    });
  });
});
