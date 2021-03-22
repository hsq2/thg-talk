import { fireEvent, RenderResult } from "@testing-library/react";
import { act } from "react-dom/test-utils";
import { Post } from "../../../App";
import { renderWrapper } from "../../../resources/testing/RenderWrapper";
import { PostFilters } from "../PostFilters";

describe("post filters", () => {
  const [changeTime, changeSort] = [jest.fn(), jest.fn()];

  it("renders correctly", () => {
    let component: RenderResult;
    act(() => {
      component = renderWrapper(
        <PostFilters changeTime={changeTime} changeSort={changeSort} />
      );
    });
    const { container } = component!;
    expect(container).toMatchSnapshot();
  });

  it("calls correct functions on change", () => {
    let component: RenderResult;
    act(() => {
      component = renderWrapper(
        <PostFilters changeTime={changeTime} changeSort={changeSort} />
      );
    });
    const { getByTestId } = component!;
    fireEvent.change(getByTestId("sort-select"), {
      target: { value: "least popular" },
    });
    expect(changeSort).toHaveBeenCalledTimes(1);
    fireEvent.change(getByTestId("time-select"), { target: { value: "week" } });
    expect(changeTime).toHaveBeenCalledTimes(1);
  });
});
