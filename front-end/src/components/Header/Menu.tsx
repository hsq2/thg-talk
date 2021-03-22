import {
  Button as MenuButton,
  Drawer,
  List,
  ListItem,
} from "@material-ui/core";
import React, { MouseEvent, useContext, useState } from "react";
import { context } from "../../App";
import { Button, FocusLink, StyledUserIcon } from "../../App.styles";
import UserService from "../../resources/UserService";
export const SimpleMenu: React.FC = () => {
  const [isOpen, setIsOpen] = useState<boolean>(false);

  const toggleDrawer = (e: MouseEvent<HTMLButtonElement>) => {
    setIsOpen(!isOpen);
  };

  const handleLogin = () => {
    UserService.login();
  };

  const onClickUserLogout = () => {
    UserService.logout();
  };

  const { loggedInUser } = useContext(context)!;

  return (
    <>
      <Button onClick={toggleDrawer} aria-label="Account">
        <StyledUserIcon
          style={{ margin: "0", color: "inherit", fontSize: "1.5rem" }}
        />
      </Button>
      <Drawer anchor={"right"} open={isOpen} onClose={toggleDrawer}>
        <List>
          {UserService.authenticated() ? (
            <>
              <ListItem>
                <FocusLink to={`/user/${loggedInUser.userID}`}>
                  <MenuButton onClick={toggleDrawer}>My Account</MenuButton>
                </FocusLink>
              </ListItem>
              <ListItem>
                <FocusLink to={"/help"}>
                  <MenuButton onClick={toggleDrawer}>Help Centre</MenuButton>
                </FocusLink>
              </ListItem>
              <ListItem>
                <MenuButton onClick={onClickUserLogout}>Log Out</MenuButton>
              </ListItem>
            </>
          ) : (
            <>
              <ListItem>
                <MenuButton>Help Centre</MenuButton>
              </ListItem>
              <ListItem>
                <MenuButton onClick={handleLogin}>Sign In</MenuButton>
              </ListItem>
            </>
          )}
        </List>
      </Drawer>
    </>
  );
};
