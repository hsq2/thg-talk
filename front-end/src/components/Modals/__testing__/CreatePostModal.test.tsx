import { LaptopWindowsOutlined } from "@material-ui/icons";
import {
  fireEvent,
  getByTestId,
  queryByLabelText,
  RenderResult,
} from "@testing-library/react";
import fetchMock, { mock } from "fetch-mock";
import React from "react";
import { act } from "react-dom/test-utils";
import { renderWrapper } from "../../../resources/testing/RenderWrapper";
import { CreatePostModal } from "../CreatePostModal";

describe("createPostModal", () => {
  let component: RenderResult;
  it("renders correctly", () => {
    act(() => {
      component = renderWrapper(
        <CreatePostModal
          sub={{
            subTalkID: "1",
            subTalkDescription: "",
            subTalkName: "",
            creatorID: "1",
            dateCreated: 0,
            imageURL: "",
          }}
          posts={[]}
          setModalOpen={jest.fn()}
          setPosts={jest.fn()}
        />
      );
    });
    const { container } = component!;
    expect(container).toMatchSnapshot();
  });

  it("alerts when no title or description given", () => {
    const alert = jest.spyOn(global, "alert").mockImplementation();
    act(() => {
      component = renderWrapper(
        <CreatePostModal
          sub={{
            subTalkID: "1",
            subTalkDescription: "",
            subTalkName: "",
            imageURL: "",
            creatorID: "1",
            dateCreated: 0,
          }}
          posts={[]}
          setModalOpen={jest.fn()}
          setPosts={jest.fn()}
        />
      );
    });
    const { getByTestId, getByPlaceholderText } = component!;
    const submitButton = getByTestId("submit-button");
    act(() => {
      fireEvent.click(submitButton);
    });
    expect(alert).toHaveBeenCalledWith("Your post must have a title!");
    const titleField = getByPlaceholderText("Title");
    act(() => {
      fireEvent.change(titleField, { target: { value: "title" } });
    });

    act(() => {
      fireEvent.click(submitButton);
    });
    expect(alert).toHaveBeenCalledWith("Your post must have content!");
  });

  it("sends post request on submit", () => {
    const mockFetch = jest.spyOn(global, "fetch");
    act(() => {
      component = renderWrapper(
        <CreatePostModal
          sub={{
            subTalkID: "1",
            subTalkDescription: "",
            subTalkName: "",
            creatorID: "1",
            dateCreated: 0,
            imageURL: "",
          }}
          posts={[]}
          setModalOpen={jest.fn()}
          setPosts={jest.fn()}
        />
      );
    });
    const { getByTestId, getByPlaceholderText } = component!;

    const titleField = getByPlaceholderText("Title");
    const postField = getByPlaceholderText("Post");
    const submitButton = getByTestId("submit-button");

    act(() => {
      fireEvent.change(titleField, { target: { value: "title" } });
    });
    act(() => {
      fireEvent.change(postField, { target: { value: "post" } });
    });
    act(() => {
      fireEvent.click(submitButton);
    });

    expect(mockFetch).toHaveBeenCalledWith(
      "/api/post",
      expect.objectContaining({
        method: "POST",
      })
    );
  });
});
