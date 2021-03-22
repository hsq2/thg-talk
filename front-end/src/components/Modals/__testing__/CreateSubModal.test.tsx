import { fireEvent } from "@testing-library/react";
import { renderWrapper } from "../../../resources/testing/RenderWrapper";
import { CreateSubModal } from "../CreateSubModal";

describe("createSubModal", () => {
  it("renders correctly", () => {
    const { container } = renderWrapper(
      <CreateSubModal setModalOpen={jest.fn()} />
    );
    expect(container).toBeInTheDocument();
  });

  it("closes modal when cancel clicked", () => {
    const setModalOpen = jest.fn();
    const { getByTestId } = renderWrapper(
      <CreateSubModal setModalOpen={setModalOpen} />
    );

    fireEvent.click(getByTestId("cancel-button"));
    expect(setModalOpen).toHaveBeenCalledWith(false);
  });

  it("handles post response correctly", async () => {
    const mockFetch = jest.spyOn(global, "fetch");
    const mockAlert = jest.spyOn(global, 'alert').mockImplementation()
    mockFetch.mockReturnValue(
      Promise.resolve(
        new Response(new Blob(), {
          status: 500,
        })
      )
    );
    const mockModalOpen = jest.fn();
    const { getByTestId, getByPlaceholderText } = renderWrapper(
      <CreateSubModal setModalOpen={mockModalOpen} />
    );
    fireEvent.change(getByPlaceholderText("Title"), {
      target: { value: "Title" },
    });
    fireEvent.change(getByPlaceholderText("Description"), {
      target: { value: "Description" },
    });

    const submitButton = getByTestId("submit-button");

    fireEvent.click(submitButton);

    setTimeout(() => expect(mockAlert).toHaveBeenCalledWith('Your subtalk could not be created, please try again.'), 0);

    mockFetch.mockReturnValue(
      Promise.resolve(
        new Response(new Blob(), {
          status: 201,
          headers: {
            Location: "/1",
          },
        })
      )
    );

    fireEvent.click(submitButton);

    setTimeout(() => expect(mockModalOpen).toHaveBeenCalledWith(false), 0);
  });
});
