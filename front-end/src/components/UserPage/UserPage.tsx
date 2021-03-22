import { Divider } from "antd";
import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Comment, Post, User } from "../../App";
import { formatDate, formatTime } from "../../resources/DateFunctions";
import { fetchWrapper } from "../../resources/FetchWrapper";
import { MenuItem, MenuItemContent } from "../HomePage/Homepage.styles";
import { PostList } from "../SubTalk/PostList";
import { UserComments } from "./UserComments";
import {
  ContentContainer,
  Selection,
  UserNameWrapper,
  UserSectionWrapper,
} from "./UserPage.styles";

interface Params {
  id: string;
}
export const UserPage: React.FC = () => {
  const [user, setUser] = useState<User | null>(null);
  const [posts, setPosts] = useState<Post[] | undefined>(undefined);
  const [comments, setComments] = useState<Comment[] | undefined>(undefined);
  const [displayPosts, setDisplayPosts] = useState<boolean>(true);

  const { id } = useParams<Params>();
  useEffect(() => {
    fetchWrapper(`/api/user/${id}`)
      .then((res) => res.json())
      .then((resUser) => setUser(resUser));

    fetchWrapper(`/api/post/byuser/${id}`)
      .then((res) => res.json())
      .then((resPosts) => {
        resPosts.forEach((post: Post) => {
          fetchWrapper(`/api/postvote/bypost/${post.postID}`)
            .then((res) => {
              if (res.status === 500) throw new Error("Error loading votes");
              else return res.json();
            })
            .then((votes) => {
              post.votes = votes;
            })
            .catch(() => {});
        });
        return resPosts;
      })
      .then((posts) => {
        setPosts(posts || []);
      });

    fetch(`/api/comment/byuser/${id}`)
      .then((response) => response.json())
      .then((comments) => {
        setComments(comments);
      })
      .catch();
  }, [id]);
  return (
    <>
      <UserNameWrapper>
        <h1>{user?.userName}</h1>
      </UserNameWrapper>
      <h3>
        {`User since ${user && formatDate(new Date(user?.dateCreated))} ${
          user && formatTime(new Date(user?.dateCreated))
        }`}
      </h3>
      <Selection>
        <MenuItem
          selected={displayPosts}
          onClick={() => {
            setDisplayPosts(true);
          }}
        >
          <MenuItemContent data-testid="menu-item-content">
            Posts
          </MenuItemContent>
        </MenuItem>

        <MenuItem
          selected={!displayPosts}
          onClick={() => {
            setDisplayPosts(false);
          }}
        >
          <MenuItemContent>Comments</MenuItemContent>
        </MenuItem>
      </Selection>
      <ContentContainer>
        <Divider />

        <UserSectionWrapper>
          {displayPosts ? (
            <>
              <PostList posts={posts} />
            </>
          ) : (
            <>
              <UserComments comments={comments} />
            </>
          )}
        </UserSectionWrapper>
      </ContentContainer>
    </>
  );
};
