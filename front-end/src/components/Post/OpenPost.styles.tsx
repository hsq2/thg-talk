import styled from "styled-components";
import { DeleteButtonContainer, PostRowVotes } from "../SubTalk/PostRow.styles";
import { SubContainer, SubImage, SubInfo } from "../SubTalk/SubTalk.styles";

export const PostMain = styled(SubContainer)``;

export const PostMainHeader = styled.div`
  display: flex;
  background-color: ${(props) => props.theme.primaryColour};
  color: white;
  /* align-items: center; */
`;

export const PostBody = styled.div`
  background-color: white;
  padding: 2rem;
  box-sizing: border-box;
  font-size: 1.5rem;
`;

export const Comments = styled(SubContainer)`
  padding: 0 0 1rem;

  > h1 {
    color: white;
    background-color: ${(props) => props.theme.primaryColour};
    padding: 0.5rem 1rem 0.5rem 1rem;
    margin-top: 0;
    font-size: 1.7rem;
  }

  > div {
    background-color: white;
    color: black;
  }
`;

export const PostInfo = styled(SubInfo)`
  > h1 {
    margin: 1.5rem 0 1rem;
    color: white;
  }
  > div {
    display: flex;
    align-items: center;
    > div {
      display: flex;
      align-items: center;
      margin: 0.4rem 1.5rem 0.4rem -0.3rem;
    }
  }
`;

export const PostImage = styled(SubImage)``;

export const PostVotes = styled(PostRowVotes)`
  margin-left: 0.5rem;
  justify-content: center;
`;

export const PostDeleteButtonContainer = styled(DeleteButtonContainer)`
  position: absolute;
  right: 0;
  top: 0;
  margin-top: 1rem;
  margin-right: 1rem;
`;
