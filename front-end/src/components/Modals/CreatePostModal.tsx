import { Button } from "antd";
import { Dispatch, SetStateAction, useContext, useState } from "react";
import { useHistory } from "react-router-dom";
import { context, Post, Sub } from "../../App";
import { DescriptionSection } from "./DescriptionSection";
import {
  ModalBody,
  ModalContainer,
  ModalFooter,
  ModalHeader,
} from "./Modals.styles";
import { TitleSection } from "./TitleSection";
import { ButtonContainer, RedButtonContainer } from "../../App.styles";
import { fetchWrapper } from "../../resources/FetchWrapper";

interface Props {
  sub: Sub;
  posts: Post[];
  setPosts: Dispatch<SetStateAction<Post[] | null | undefined>>;
  setModalOpen: Dispatch<SetStateAction<boolean>>;
}

export const CreatePostModal: React.FC<Props> = ({
  sub,
  posts,
  setPosts,
  setModalOpen,
}) => {
  const [title, setTitle] = useState<string>("");
  const [content, setContent] = useState<string>("");
  const { loggedInUser } = useContext(context)!;
  const history = useHistory();

  const addPost = () => {
    if (!title) {
      alert("Your post must have a title!");
      return;
    } else if (!content) {
      alert("Your post must have content!");
      return;
    }

    fetchWrapper("/api/post", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        dateCreated: Date.now(),
        post: content,
        postTitle: title,
        subID: sub.subTalkID,
        userID: loggedInUser.userID,
      }),
    })
      .then((response) => {
        const id = response?.headers.get("Location")!.split("/").pop()!;
        if (response?.status === 201) {
          setPosts([
            ...posts,
            {
              postID: id,
              userID: loggedInUser.userID,
              subID: sub.subTalkID,
              postTitle: title,
              post: content,
              dateCreated: new Date().toString(),
            },
          ]);
        }
        return id;
      })
      .then((id) => history.push(`/post/${id}`))
      .then(() => setModalOpen(false));
  };

  const cancel = () => {
    setModalOpen(false);
  };

  return (
    <ModalContainer>
      <ModalHeader>
        <h1>Create a post {sub ? `in ${sub.subTalkName}` : ""}</h1>
      </ModalHeader>
      <ModalBody>
        <TitleSection title={title} setTitle={setTitle} />
        <DescriptionSection
          creating="post"
          description={content}
          setDescription={setContent}
        />
      </ModalBody>
      <ModalFooter>
        <RedButtonContainer>
          <Button type="primary" danger onClick={cancel}>
            Cancel
          </Button>
        </RedButtonContainer>
        <ButtonContainer>
          <Button type="primary" onClick={addPost} data-testid="submit-button">
            Create
          </Button>
        </ButtonContainer>
      </ModalFooter>
    </ModalContainer>
  );
};
