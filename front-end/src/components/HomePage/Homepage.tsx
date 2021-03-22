import {
  AppstoreAddOutlined,
  FireOutlined,
  PlusCircleOutlined,
} from "@ant-design/icons";
import { Modal } from "@material-ui/core";
import { Button } from "antd";
import React, { useState } from "react";
import { CreateSubModal } from "../Modals/CreateSubModal";
import {
  MainContentWrapper,
  MenuItem,
  MenuItemContent,
  MenuItemsWrapper,
  MenuWrapper,
  MenuWrapperHeader,
  PageWrapper,
  RightSectionWrapper,
} from "./Homepage.styles";
import { ButtonContainer } from "../../App.styles";
import { SubTalksGrid } from "./SubTalksGrid";
import { TopPosts } from "./TopPosts";

export const Homepage: React.FC = () => {
  const [modal, setModal] = useState<boolean>(false);

  const [displayPosts, setDisplayPosts] = useState<boolean>(true);

  return (
    <>
      <PageWrapper>
        <MenuWrapper>
          <MenuWrapperHeader>Menu</MenuWrapperHeader>
          <MenuItemsWrapper>
            <MenuItem
              selected={displayPosts}
              onClick={() => {
                setDisplayPosts(true);
              }}
            >
              <MenuItemContent data-testid="menu-item-content">
                <FireOutlined /> Top Posts
              </MenuItemContent>
            </MenuItem>

            <MenuItem
              selected={!displayPosts}
              onClick={() => {
                setDisplayPosts(false);
              }}
            >
              <MenuItemContent>
                <AppstoreAddOutlined /> SubTalks
              </MenuItemContent>
            </MenuItem>
          </MenuItemsWrapper>
        </MenuWrapper>
        <MainContentWrapper>
          {displayPosts ? <TopPosts /> : <SubTalksGrid />}
        </MainContentWrapper>
        <RightSectionWrapper>
          <ButtonContainer>
            <Button type="primary" onClick={() => setModal(true)}>
              <PlusCircleOutlined /> Start a New SubTalk
            </Button>
          </ButtonContainer>
        </RightSectionWrapper>
        <Modal
          open={modal}
          onClose={() => setModal(false)}
          disableBackdropClick
          disableEscapeKeyDown
        >
          <div>
            <CreateSubModal setModalOpen={setModal} />
          </div>
        </Modal>
      </PageWrapper>
    </>
  );
};
