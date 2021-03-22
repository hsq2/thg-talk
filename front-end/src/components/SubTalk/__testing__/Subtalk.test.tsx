import { RenderResult } from "@testing-library/react";
import { act } from "react-dom/test-utils";
import { renderWrapper } from "../../../resources/testing/RenderWrapper";
import { SubTalk } from "../SubTalk";

describe("subtalk", () => {
  it("renders correctly", () => {
    let component: RenderResult;
    act(() => {
      component = renderWrapper(<SubTalk />);
    });
    const { container } = component!;
    expect(container).toMatchSnapshot();
  });

  it("fetches subtalk and posts on load", () => {
    let mockFetch;
    act(() => {mockFetch = jest.spyOn(global, "fetch").mockReturnValue(
      new Promise((resolve, reject) => {
        resolve(new Response("", { status: 200 })), reject();
      })
    )});
    act(() => {
      renderWrapper(<SubTalk />);
    });
    expect(mockFetch).toHaveBeenCalledTimes(2);
  });
});
