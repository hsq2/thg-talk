import { TextField } from "@material-ui/core";
import { Send } from "@material-ui/icons";
import styled from "styled-components";
import { Button } from "../../App.styles";
import { DeleteButtonContainer } from "../SubTalk/PostRow.styles";

export const UserPhoto = styled.img`
  height: 1.5em;
  width: 1.5em;
  border-radius: 50%;
`;

export const CommentList = styled.div`
  display: grid;
  grid-row-gap: 1em;
`;

export const CommentVotesSection = styled.div`
  display: flex;
  align-items: center;
  flex-direction: column;
  justify-content: end;
`;

export const CommentContentWrapper = styled.div`
  overflow-wrap: break-word;
  overflow: auto;
`;
export const UserProfileWrapper = styled.div``;
export const CommentMetadata = styled.div`
  display: flex;
  justify-content: space-between;
  margin-bottom: 0.5em;
`;

export const CommentDate = styled.div`
  justify-self: flex-end;
`;

export const CommentWrapper = styled.div``;
export const CommentContainer = styled.div`
  display: grid;
  grid-template-columns: 0.05fr 1fr;
  background: #f8f8f8;
  padding: 1em;
`;

export const StyledForm = styled.div`
  padding-left: 1rem;
  margin: 1rem 0 0.5rem 0;
  width: 100%;
  height: 7vh;
  box-sizing: border-box;
  background-color: white;
  display: flex;
  flex-direction: row;
  align-items: center;
`;

export const StyledTextField = styled(TextField)`
  border-radius: 10px;
  padding: 1rem;
  width: 80%;
`;

export const StyledSubmitButton = styled(Button)`
  border-radius: 10px;
  background-color: ${(props) => props.theme.primaryColour};
  border: none;
  width: 5%;
  height: 100%;
  position: relative;
`;

export const Username = styled.div`
  margin: 1rem;
  font-weight: bold;
`;

export const CommentBody = styled.div`
  margin: 1rem;
`;
export const CommentTime = styled.div`
  position: absolute;
  right: 20px;
  bottom: 10px;
  display: flex;
  align-items: center;
  color: #676767;
  > div {
    margin: 0 0 0 1rem;
    display: flex;
    align-items: center;
  }
`;

export const StyledSend = styled(Send)`
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  margin: auto;
  color: ${(props) => props.theme.secondaryColour};
`;

export const CommentDeleteButtonContainer = styled(DeleteButtonContainer)``;

export const CommentDeleteButton = styled.button`
  float: right;
  background-color: transparent;
  border: none;
  outline: none;
  font-size: 1.2rem;
  padding: 0;
  /* padding-top:0.3rem;
  padding-right:0.3rem; */
  padding-left: 0.8rem;
  /* border:black solid; */
  line-height: 0.8rem;
  box-sizing: border-box;
  :hover {
    cursor: pointer;
  }
  :active {
    color: gray;
  }
  :disabled {
    color: transparent;
    cursor: auto;
  }
  :focus-visible {
    outline: 5px auto -webkit-focus-ring-color;
  }
`;
