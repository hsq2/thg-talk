import React, {
  createContext,
  Dispatch,
  SetStateAction,
  useEffect,
  useState,
} from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import { ThemeProvider } from "styled-components";
import "./App.css";
import { Body } from "./App.styles";
import { Header } from "./components/Header/Header";
import { Homepage } from "./components/HomePage/Homepage";
import { OpenPost } from "./components/Post/OpenPost";
import { MyAccount } from "./components/SubTalk/MyAccount";
import { SubTalk } from "./components/SubTalk/SubTalk";
import { UserPage } from "./components/UserPage/UserPage";
import { defaultTheme } from "./themes/Theme";
import "antd/dist/antd.css";
import Secured from "./components/Keycloak/Secured.js";
import UserService from "./resources/UserService";
import { fetchWrapper } from "./resources/FetchWrapper";
import { Help } from "./Help";

export interface Sub {
  subTalkID: string;
  userID: string;
  subTalkName: string;
  subTalkDescription: string;
  imageURL: string;
  dateCreated: number;
}

export interface Post {
  votes?: PostVote[] | undefined | null;
  postID: string;
  userID: string;
  subID: string;
  postTitle: string;
  post: string;
  dateCreated: string;
}

export interface Comment {
  commentID: string;
  userID: string;
  postID: string;
  comment: string;
  date: number;
}

export interface PostVote {
  postID: string;
  userID: string;
  voteID: string;
  voteType: "UPVOTE" | "DOWNVOTE";
}
export interface CommentVote {
  commentID: string;
  userID: string;
  voteID: string;
  voteType: "UPVOTE" | "DOWNVOTE";
}

export interface User {
  userID: string;
  userName: string;
  dateCreated: string;
}

export interface Context {
  subs: Sub[];
  setSubs: Dispatch<SetStateAction<Sub[]>>;
  posts: Post[];
  setPosts: Dispatch<SetStateAction<Post[]>>;
  users: User[] | null;
  setUsers: Dispatch<SetStateAction<User[] | null>>;
  loggedInUser: User;
}

export const context = createContext<Context | null>(null);

function App() {
  const [posts, setPosts] = useState<Post[]>([]);
  const [users, setUsers] = useState<User[] | null>(null);
  const [subs, setSubs] = useState<Sub[]>([]);
  const [loggedInUser, setLoggedInUser] = useState<User>({
    userID: "61614667-d279-11e7-a5ac-f941ac8dfc39",
    userName: "user1",
    dateCreated: Date().toString(),
  });

  const fetchPosts = () => {
    fetchWrapper(`/api/post`, {
      method: "GET",
    })
      .then((response) => response?.json())
      .then((data) => setPosts(data));
  };

  const fetchUsers = () => {
    fetchWrapper(`/api/user`, {
      method: "GET",
    })
      .then((response) => response?.json())
      .then((data) => setUsers(data))
      .catch((err) => UserService.logout());
  };

  const fetchSubs = () => {
    fetchWrapper(`/api/subtalk`, {
      method: "GET",
    })
      .then((response) => response?.json())
      .then((data) => setSubs(data));
  };

  useEffect(() => {
    if (users) {
      console.log(users);
      const user = users.find((user) => user.userID === UserService.userID());
      if (user) {
        setLoggedInUser(user);
      } else {
        UserService.userProfile().success((kcUser) => {
          fetchWrapper(`/api/user`, {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              userID: kcUser.id ? kcUser.id : UserService.userID(),
              userName: kcUser.username,
              dateCreated: kcUser.createdTimestamp
                ? new Date(kcUser.createdTimestamp!).toISOString()
                : new Date().toISOString(),
            }),
          })
            .then((response) => response?.json())
            .then((data) => setLoggedInUser(data))
            .catch((err) => UserService.login());
        });
      }
    }
  }, [users]);

  useEffect(() => {
    fetchPosts();
    fetchUsers();
    fetchSubs();
  }, []);

  return (
    <context.Provider
      value={{
        subs,
        setSubs,
        posts,
        setPosts,
        users,
        setUsers,
        loggedInUser,
      }}
    >
      <ThemeProvider theme={defaultTheme}>
        <Router>
          <Header />
          <Body>
            <Switch>
              <Route path="/secured" component={Secured} />
              <Route exact path="/" component={Homepage} />
              <Route exact path="/sub/:id" component={SubTalk} />
              <Route exact path="/post/:id" component={OpenPost} />
              <Route exact path="/account" component={MyAccount} />
              <Route exact path="/user/:id" component={UserPage} />
              <Route exact path="/help" component={Help} />
            </Switch>
          </Body>
        </Router>
      </ThemeProvider>
    </context.Provider>
  );
}

export default App;
