import { Component, createContext } from "react";
import { createMemoryHistory } from "history";
import { render } from "@testing-library/react";
import { Router } from "react-router-dom";
import { context, Context } from "../../App";

export const mockContextValue: Context = {
  subs: [],
  setSubs: jest.fn(),
  posts: [],
  setPosts: jest.fn(),
  users: [],
  setUsers: jest.fn(),
  loggedInUser: {
    userID: "1",
    userName: "mockUser",
    dateCreated: "1",
  },
};

export const renderWrapper = (
  component: JSX.Element,
  contextValue: Context = mockContextValue
) => {
  const history = createMemoryHistory();
  return render(
    <context.Provider value={contextValue}>
      <Router history={history}>{component}</Router>
    </context.Provider>
  );
};
