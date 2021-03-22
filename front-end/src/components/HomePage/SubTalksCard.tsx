import { Card, Divider } from "antd";
import { FC, useState, useContext } from "react";
import { Sub, context } from "../../App";
import {
  SubtalkCardWrapper,
  SubtalkDescription,
  SubtalkTitle,
} from "./Homepage.styles";
import {FocusLink} from "../../App.styles";
import { DeleteModal } from "../Modals/DeleteModal";
import { Modal } from "@material-ui/core";
import {DeleteButton,DeleteButtonContainer,} from "../SubTalk/PostRow.styles";

interface Props {
    subtalk: Sub;
  }

export const SubTalksCard: FC<Props> = ({subtalk}) => {
  const { loggedInUser } = useContext(context)!;
  const [deleteModal, setDeleteModal] = useState<boolean>(false);

  return (
    <>
    <Card
        style={{
        width: "100%",
        marginBottom: "2em",
        boxShadow:
            "0px 5px 6px -5px rgba(151,163,184,.5), 0px 15px 12px -15px rgba(151,163,184,.5), 0px 25px 18px -25px rgba(151,163,184,.5)", color:"black",
        }}
    >
        <DeleteButtonContainer>
            <DeleteButton
            aria-label="Delete"
            onClick={() => setDeleteModal(true)}
            disabled={subtalk.userID !== loggedInUser.userID}
            >
            &times;
            </DeleteButton>
        </DeleteButtonContainer>
        <Modal
        open={deleteModal}
        onClose={() => setDeleteModal(false)}
        disableBackdropClick
        disableEscapeKeyDown
        >
        <DeleteModal
        screen="home"
        sub = {subtalk!}
        setModalOpen={setDeleteModal}
        id={subtalk.subTalkID}
        toDelete="SubTalk"
        />
        </Modal>
        <FocusLink to={`/sub/${subtalk.subTalkID}`}>
        <SubtalkCardWrapper>
        <SubtalkTitle>{subtalk.subTalkName}</SubtalkTitle>
        <Divider />
        <SubtalkDescription>
            {subtalk.subTalkDescription}
        </SubtalkDescription>
        </SubtalkCardWrapper>
        </FocusLink>
    </Card>
    </>
  );
};
