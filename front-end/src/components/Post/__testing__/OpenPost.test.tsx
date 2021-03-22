import { RenderResult } from "@testing-library/react";
import { mock } from "fetch-mock";
import { act } from "react-dom/test-utils";
import { renderWrapper } from "../../../resources/testing/RenderWrapper";
import { OpenPost } from "../OpenPost";

describe("Open post", () => {
  it("renders correctly", () => {
    let component: RenderResult;
    act(() => {
      component = renderWrapper(<OpenPost />);
    });
    const { container } = component!;
    expect(container).toMatchSnapshot();
  });

  it("makes get request on load", () => {
    const mockFetch = jest.spyOn(global, "fetch").mockReturnValue(
      new Promise((resolve, reject) => {
        resolve(new Response(new Blob(), { status: 200 })), reject(() => {});
      })
    );

    act(() => {
      renderWrapper(<OpenPost />);
    });
    expect(mockFetch).toHaveBeenCalledWith(
      "/api/post/0",
      expect.objectContaining({ method: "GET" })
    );
  });
});
