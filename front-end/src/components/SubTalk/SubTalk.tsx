import { AppstoreAddOutlined, PlusCircleOutlined, MinusCircleOutlined } from "@ant-design/icons";
import { Modal } from "@material-ui/core";
import { Button } from "antd";
import { default as React, useEffect, useState, useContext } from "react";
import { useParams } from "react-router";
import { Post, Sub, context } from "../../App";
import { Loading } from "../../Loading";
import { NotFound } from "../../NotFound";
import {
  MainContentWrapper,
  PageWrapperCentered,
  PostWrapper,
  PostWrapperHeader,
  RightSectionWrapper,
  WikiHeadline,
  WikiWrapper,
} from "../HomePage/Homepage.styles";
import { CreatePostModal } from "../Modals/CreatePostModal";
import { PostList } from "./PostList";
import {SubButtonContainer, SubRedButtonContainer} from "./SubTalk.styles"
import {DeleteModal} from "../Modals/DeleteModal"
import { fetchWrapper } from "../../resources/FetchWrapper";

type Params = {
  id: string;
};

export const SubTalk: React.FC = () => {
  const id = useParams<Params>().id || "0";
  const [sub, setSub] = useState<Sub | null | undefined>(undefined);
  const [posts, setPosts] = useState<Post[] | null | undefined>(undefined);
  const { loggedInUser } = useContext(context)!;
  const [deleteModal, setDeleteModal] = useState<boolean>(false);

  useEffect(() => {
    const fetchSubTalk = () => {
      fetchWrapper(`/api/subtalk/${id}`, {
        method: "GET",
      })
        .then((response) => {
          if (!response.ok) throw new Error("Sub not found");
          else return response.json();
        })
        .then((data) => setSub(data))
        .catch(() => {
          console.log("Sub not found");
          setSub(null);
        });
    };

    const fetchPosts = () => {
      fetchWrapper(`/api/post/bysubtalk/${id}`, {
        method: "GET",
      })
        .then((response) => {
          if (response.status === 500) throw new Error("Error loading posts");
          else return response.json();
        })
        .then((posts: Post[]) => {
          posts.forEach((post) => {
            fetchWrapper(`/api/postvote/bypost/${post.postID}`)
              .then((res) => {
                if (res.status === 500) throw new Error("Error loading votes");
                else return res.json();
              })
              .then((votes) => {
                post.votes = votes;
              });
          });
          return posts;
        })
        .then((posts) => setPosts(posts || []))
        .catch();
    };
    fetchPosts();
    fetchSubTalk();
  }, [id]);

  const [createPostModal, setCreatePostModal] = useState<boolean>(false);

  console.log(posts);

  switch (sub) {
    case undefined:
      return <Loading />;
    case null:
      return <NotFound />;
    default:
      return (
        <>
          <PageWrapperCentered>
            <MainContentWrapper>
              <PostWrapperHeader>
                <AppstoreAddOutlined />
                <strong> r/{sub.subTalkName}</strong>
              </PostWrapperHeader>
              <PostWrapper style={{ width: "100%" }}>
                <PostList posts={posts} />
              </PostWrapper>
            </MainContentWrapper>
            <RightSectionWrapper>
              <WikiWrapper>
                <WikiHeadline>
                  <strong>Description</strong>
                </WikiHeadline>
                {sub.subTalkDescription}
              </WikiWrapper>
              <SubButtonContainer>
              <Button type="primary" onClick={() => setCreatePostModal(true)}>
                <PlusCircleOutlined /> Create a new post
              </Button>
              </SubButtonContainer>
              <SubRedButtonContainer>
              <Button type="primary" danger 
              aria-label="Delete"
              onClick={() => setDeleteModal(true)}
              hidden={sub.userID !== loggedInUser.userID}>
                <MinusCircleOutlined /> Delete SubTalk
              </Button>
              </SubRedButtonContainer>
            </RightSectionWrapper>
            <Modal
              open={createPostModal}
              onClose={() => setCreatePostModal(false)}
              disableBackdropClick
              disableEscapeKeyDown
            >
              <CreatePostModal
                sub={sub}
                posts={posts || []}
                setPosts={setPosts}
                setModalOpen={setCreatePostModal}
              />
            </Modal>
            <Modal
          open={deleteModal}
          onClose={() => setDeleteModal(false)}
          disableBackdropClick
          disableEscapeKeyDown
        >
          <DeleteModal
          screen="sub"
          sub = {sub!}
            setModalOpen={setDeleteModal}
            id={sub.subTalkID}
            toDelete="SubTalk"
          />
        </Modal>
          </PageWrapperCentered>
        </>
      );
  }
};