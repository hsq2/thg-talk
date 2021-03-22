import { FireOutlined } from "@ant-design/icons";
import { FC, useEffect, useState } from "react";
import { Post } from "../../App";
import { fetchWrapper } from "../../resources/FetchWrapper";
import { PostList } from "../SubTalk/PostList";
import { PostWrapper, PostWrapperHeader } from "./Homepage.styles";

export const TopPosts: FC = () => {
  const [posts, setPosts] = useState<Post[] | null | undefined>(undefined);

  useEffect(() => {
    fetchWrapper("/api/post/top?number=10")
      .then((res) => res?.json())
      .then((posts: Post[]) => {
        posts.forEach((post) => {
          fetchWrapper(`/api/postvote/bypost/${post.postID}`)
            .then((res) => {
              if (res?.status === 500) throw new Error("Error loading votes");
              else return res?.json();
            })
            .then((votes) => {
              post.votes = votes;
            })
            .catch(() => {});
        });
        return posts;
      })
      .then((posts) => setPosts(posts || []))
      .catch(() => {
        console.log("Error fetching posts");
        setPosts(null);
      });
  }, []);
  return (
    <>
      <PostWrapperHeader>
        <FireOutlined />
        <strong>Top Posts</strong>
      </PostWrapperHeader>
      <PostWrapper style={{ width: "100%" }}>
        <PostList posts={posts} />
      </PostWrapper>
    </>
  );
};
