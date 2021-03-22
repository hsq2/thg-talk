import { Button } from "antd";
import React, { Dispatch, SetStateAction } from "react";
import {
  SmallModalFooter,
  ModalHeader,
  SmallModalBody,
  SmallModalContainer,
} from "./Modals.styles";
import { ButtonContainer, RedButtonContainer } from "../../App.styles";
import { useHistory } from "react-router-dom";
import { Sub } from "../../App";
import { fetchWrapper } from "../../resources/FetchWrapper";

type toDelete = "post" | "comment" | "SubTalk";
type screen = "home" | "post" | "comment" | "sub";

interface Props {
  setModalOpen: Dispatch<SetStateAction<boolean>>;
  id: string;
  toDelete: toDelete;
  screen : screen;
  sub?: Sub;
}

export const DeleteModal: React.FC<Props> = ({
  setModalOpen,
  id,
  toDelete,
  sub,
  screen,
}) => {
  const history = useHistory();

  const cancel = () => {
    setModalOpen(false);
  };

  const deletePost = () => {
    let path: string;
    if (toDelete !== "SubTalk") {
      path = `/api/${toDelete}/${id}`;
      console.log(path)
    } else {
      path = `/api/subtalk/${id}`;
      console.log(path)
    }
    fetchWrapper(path, {
      method: "DELETE",
    });
    if (screen === "post") {
      history.push(`/sub/${sub!.subTalkID}`);
      setModalOpen(false);
    } else if (screen === "sub") {
      history.push(`/`);
      setModalOpen(false);
    } else window.location.reload();
  };

  return (
    <SmallModalContainer>
      <ModalHeader>
        <h1>Delete {toDelete.charAt(0).toUpperCase() + toDelete.slice(1)} </h1>
      </ModalHeader>
      <SmallModalBody>
        {toDelete === "SubTalk" ? `Are you sure you want to delete r/${sub?.subTalkName}?` : `Are you sure you want to delete this ${toDelete}?`}
      </SmallModalBody>
      <SmallModalFooter>
        <ButtonContainer>
          <Button type="primary" onClick={cancel}>
            Cancel
          </Button>
        </ButtonContainer>
        <RedButtonContainer>
          <Button type="primary" danger onClick={deletePost}>
            Delete
          </Button>
        </RedButtonContainer>
      </SmallModalFooter>
    </SmallModalContainer>
  );
};
