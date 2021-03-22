import { fireEvent, RenderResult } from "@testing-library/react";
import { act } from "react-dom/test-utils";
import { Post } from "../../../App";
import { renderWrapper } from "../../../resources/testing/RenderWrapper";
import { PostList } from "../PostList";

describe("Post list", () => {
  const post: Post = {
    post: "",
    postID: "2",
    userID: "1",
    postTitle: "",
    subID: "1",
    dateCreated: "23 Feb 2021 00:00:00 GMT",
  };
  const post2: Post = {
    post: "",
    postID: "3",
    userID: "2",
    postTitle: "",
    subID: "1",
    dateCreated: "23 Feb 2021 00:00:00 GMT",
  };

  it("renders correctly", () => {
    let component: RenderResult;
    act(() => {
      component = renderWrapper(<PostList posts={[]} />);
    });
    const { container } = component!;
    expect(container).toMatchSnapshot();
  });

  it("renders container when there are posts", () => {
    let component: RenderResult;
    act(() => {
      component = renderWrapper(<PostList posts={[post]} />);
    });
    const { getByTestId } = component!;
    expect(getByTestId("post-list-container")).toBeInTheDocument();
  });
  it("renders no posts screen when there are no posts", () => {
    let component: RenderResult;
    act(() => {
      component = renderWrapper(<PostList posts={[]} />);
    });
    const { getByTestId } = component!;
    expect(getByTestId("no-posts-yet")).toBeInTheDocument();
  });

  it("displays the correct number of posts", () => {
    let component: RenderResult;
    act(() => {
      component = renderWrapper(<PostList posts={[post, post2]} />);
    });
    const { getByTestId } = component!;
    const timeSelect = getByTestId("time-select");
    act(() => {
      fireEvent.change(timeSelect, { target: { value: "month" } });
    });
    const sortSelect = getByTestId("sort-select");
    act(() => {
      fireEvent.change(sortSelect, { target: { value: "most recent" } });
    });
    expect(getByTestId("post-list-container").children.length).toEqual(2);
  });
});
