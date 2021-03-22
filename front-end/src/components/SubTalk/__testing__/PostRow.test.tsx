import { RenderResult } from "@testing-library/react";
import { act } from "react-dom/test-utils";
import { Post } from "../../../App";
import { renderWrapper } from "../../../resources/testing/RenderWrapper";
import { PostRow } from "../PostRow";

describe("post row", () => {
  const post: Post = {
    postID: "1",
    postTitle: "",
    post: "",
    userID: "",
    subID: "1",
    dateCreated: "1",
  };

  it("renders correctly", () => {
    let component: RenderResult;
    act(() => {component = renderWrapper(<PostRow post={post} />)})
    const { container } = component!;
    expect(container).toMatchSnapshot();
  });

});
