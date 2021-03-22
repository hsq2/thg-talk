import { Button } from "antd";
import { Dispatch, SetStateAction, useContext, useState } from "react";
import { useHistory } from "react-router-dom";
import { context } from "../../App";
import { ButtonContainer, RedButtonContainer } from "../../App.styles";
import { fetchWrapper } from "../../resources/FetchWrapper";
import { DescriptionSection } from "./DescriptionSection";
import {
  ModalBody,
  ModalContainer,
  ModalFooter,
  ModalHeader,
} from "./Modals.styles";
import { TitleSection } from "./TitleSection";

interface Props {
  setModalOpen: Dispatch<SetStateAction<boolean>>;
}

export const CreateSubModal: React.FC<Props> = ({ setModalOpen }) => {
  const [title, setTitle] = useState<string>("");
  const [description, setDescription] = useState<string>("");
  const [thumbnail] = useState<string>("");

  const { loggedInUser, subs, setSubs } = useContext(context)!;
  const history = useHistory();

  const addSubTalk = () => {
    const dateCreated = Date.now();
    fetchWrapper("/api/subtalk", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        dateCreated,
        imageURL: thumbnail,
        subTalkDescription: description,
        subTalkName: title,
        userID: loggedInUser.userID,
      }),
    })
      .then((response) => {
        if (response?.status === 500) {
          throw new Error("SubTalk could not be created");
        } else {
          const id = response?.headers.get("Location")!.split("/").pop()!;
          setSubs([
            ...subs,
            {
              subTalkID: id,
              userID: loggedInUser.userID,
              subTalkName: title,
              subTalkDescription: description,
              imageURL: thumbnail,
              dateCreated,
            },
          ]);
          setModalOpen(false);
          return id;
        }
      })
      .then((id) => history.push(`/sub/${id}`))
      .catch(() =>
        alert("Your SubTalk could not be created, please try again.")
      );
  };

  const cancel = () => {
    setModalOpen(false);
  };

  return (
    <ModalContainer>
      <ModalHeader>
        <h1>Create a SubTalk</h1>
      </ModalHeader>
      <ModalBody>
        <TitleSection title={title} setTitle={setTitle} aria-label="Title" />
        <DescriptionSection
          creating="sub"
          description={description}
          setDescription={setDescription}
        />
      </ModalBody>
      <ModalFooter>
        <RedButtonContainer>
          <Button
            type="primary"
            danger
            onClick={cancel}
            data-testid="cancel-button"
          >
            Cancel
          </Button>
        </RedButtonContainer>
        <ButtonContainer>
          <Button
            type="primary"
            onClick={addSubTalk}
            data-testid="submit-button"
          >
            Create
          </Button>
        </ButtonContainer>
      </ModalFooter>
    </ModalContainer>
  );
};
