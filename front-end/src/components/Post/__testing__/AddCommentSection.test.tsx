import { fireEvent, RenderResult } from "@testing-library/react";
import { act } from "react-dom/test-utils";
import { renderWrapper } from "../../../resources/testing/RenderWrapper";
import { AddCommentSection } from "../AddCommentSection";

describe("add comment section", () => {
  const [comments, setComments, post] = [
    [],
    jest.fn(),
    {
      postID: "1",
      post: "",
      subID: "1",
      userID: "1",
      postTitle: "",
      dateCreated: "1",
    },
  ];
  it("renders correctly", () => {
    let component: RenderResult;
    act(() => {
      component = renderWrapper(
        <AddCommentSection
          comments={comments}
          setComments={setComments}
          post={post}
        />
      );
    });
    const { container } = component!;
    expect(container).toMatchSnapshot();
  });

  it("handles post requests correctly", () => {
    let component: RenderResult;
    act(() => {
      component = renderWrapper(
        <AddCommentSection
          comments={comments}
          setComments={setComments}
          post={post}
        />
      );
    });
    const { getByRole } = component!;
    const mockFetch = jest.spyOn(global, "fetch").mockReturnValue(
      new Promise((resolve, reject) => {
        resolve(
          new Response(new Blob(), {
            status: 500,
            headers: {
              Location: "/1",
            },
          })
        ),
          reject();
      })
    );

    act(() => {
      fireEvent.change(getByRole("textbox"), {
        target: { value: "comment" },
      });
    });
    act(() => {
      fireEvent.click(getByRole("button"));
    });

    expect(mockFetch).toHaveBeenCalledWith(
      "/api/comment",
      expect.objectContaining({ method: "POST" })
    );
  });
});
