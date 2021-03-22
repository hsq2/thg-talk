import styled from "styled-components";

export const FlatPostRowWrapper = styled.div`
  display: grid;
  grid-template-columns: 0.2fr 1fr 0.01fr;
  a {
    color: black;
  } /* Unvisited link  */
  a:visited {
    color: black;
  } /* Visited link    */
  a:hover {
    color: black;
  } /* Mouse over link */
  a:active {
    color: black;
  }
`;

export const PostRowContentWrapper = styled.div`
  margin: 0px;
`;

export const PostRowTitle = styled.h2`
  text-transform: capitalize;
`;

export const PostRowVotes = styled.div`
  align-items: center;
  display: flex;
  flex-direction: column;
  justify-content: center;
  display: flex;
  justify-content: end;
`;

export const PostRowMain = styled.div`
  text-align: left;
  margin: 0;
  overflow: hidden;
`;

export const PostRowMetadata = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr;

  @media only screen and (min-width: 768px) {
    grid-template-columns: 1fr 1fr 1fr;
  }
`;

export const PostRowMetadataComment = styled.div`
  display: flex;
  justify-content: flex-end;
`;

export const PostRowMetadataCommentIcon = styled.div`
  margin-right: 0.25em;
`;

export const PostRowMainContent = styled.div`
  overflow-wrap: break-word;
  height: 10em;
`;

export const PostRowMainContentLarge = styled.div`
  margin-bottom: 1em;
`;

export const PostRowMetadataDate = styled.p`
  text-align: center;
  display: none;

  @media only screen and (min-width: 768px) {
    display: block;
  }
`;
export const HighlightedUserName = styled.span`
  color: ${(props) => props.theme.secondaryColour};
`;

export const PostRowInfo = styled.div`
  align-items: center;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  align-items: flex-start;
  box-sizing: border-box;
  color: #676767;
  margin-right: 0.5rem;

  > div {
    display: flex;
    align-items: center;

    > div {
      color: #676767;

      display: flex;
      align-items: center;
    }
  }
`;

export const PostRowContainer = styled.div`
  display: flex;
  color: black;
  background-color: ${(props) => props.theme.rowBackground};
  :nth-of-type(even) {
    background-color: ${(props) => props.theme.alternateRowBackground};
  }
`;

export const VoteButton = styled.button`
  margin: 0.2rem;
  background-color: transparent;
  border: none;
  outline: none;
  border-radius: 20px;
  opacity: 0.2;
  :active {
    outline: none;
    box-shadow: inset 0 0 5px black;
  }
  :disabled {
    :active {
      box-shadow: none;
    }
    :hover {
      cursor: auto;
    }
    opacity: 1;
  }
  :hover {
    cursor: pointer;
  }
  :focus-visible {
    outline: 5px auto -webkit-focus-ring-color;
  }
`;

export const H3 = styled.h3`
  margin: 0px;
`;

export const DeleteButton = styled.button`
  float: right;
  background-color: transparent;
  border: none;
  outline: none;
  font-size: 1.5rem;
  padding: 0;
  padding-right: 0.2rem;
  :hover {
    cursor: pointer;
  }
  :active {
    color: gray;
    outline: none;
  }
  :disabled {
    color: transparent;
    cursor: auto;
  }
  :focus-visible {
    outline: 5px auto -webkit-focus-ring-color;
  }
`;

export const DeleteButtonContainer = styled.div`
  line-height: 1rem;
  box-sizing: border-box;
`;

export const PostRowTitleContainer = styled.div``;
