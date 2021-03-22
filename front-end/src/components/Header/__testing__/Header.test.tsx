import { fireEvent, render, RenderResult } from "@testing-library/react";
import React from "react";
import { act } from "react-dom/test-utils";
import { renderWrapper } from "../../../resources/testing/RenderWrapper";
import { Header } from "../Header";
import { SearchBar } from "../SearchBar";

describe("header", () => {
  it("Renders correctly", () => {
    let component: RenderResult;
    act(() => {
      component = renderWrapper(<Header />);
    });
    const { container, getByTestId } = component!;
    expect(getByTestId("logo")).toBeInTheDocument();
    expect(container).toMatchSnapshot();
  });

  it("search dropdown appears when search is entered", () => {
    const mockFetch = jest.spyOn(global, "fetch");
    mockFetch.mockReturnValue(
      Promise.resolve(
        new Response(new Blob([JSON.stringify({})]), { status: 200 })
      )
    );
    let component: RenderResult;
    act(() => {
      component = renderWrapper(<SearchBar />);
    });
    const { queryByTestId } = component!;
    const searchField = queryByTestId("search-box")!;
    expect(queryByTestId("search-dropdown")).not.toBeInTheDocument();
    act(() => {
      fireEvent.change(searchField, { target: { value: "search" } });
    });

    expect(queryByTestId("search-dropdown")).toBeInTheDocument();
  });
});
