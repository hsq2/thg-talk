import { RenderResult } from "@testing-library/react";
import { act } from "react-dom/test-utils";
import { Post } from "../../../App";
import { renderWrapper } from "../../../resources/testing/RenderWrapper";
import { UserPage } from "../UserPage";

describe("user page", () => {
  it("renders correctly", () => {
    let component: RenderResult;
    act(() => {
      component = renderWrapper(<UserPage />);
    });
    const { container } = component!;
    expect(container).toMatchSnapshot();
  });

  it("makes get requests", () => {
    const mockPost: Post = {
      post: "",
      postID: "1",
      postTitle: "",
      userID: "1",
      subID: "1",
      dateCreated: "23 Feb 2021 00:00:00 GMT",
    };
    const mockFetch = jest
      .spyOn(global, "fetch")
      .mockReturnValue(
        new Promise(
          () => new Response(JSON.stringify(mockPost), { status: 200 })
        )
      );

    act(() => {
      renderWrapper(<UserPage />);
    });

    expect(mockFetch).toHaveBeenCalledTimes(3);
  });
});
