import styled from "styled-components";
import { ButtonContainer, RedButtonContainer } from "../../App.styles";

export const SubContainer = styled.main`
  margin: 1px;
  //box-shadow: 0px 0px 10px black;
  border-radius: 5px;
  width: 100%;
`;

export const SubHeader = styled.div`
  box-sizing: border-box;
  background-color: ${(props) => props.theme.alternateRowBackground};
  display: flex;
  justify-content: space-between;

  > div {
    display: flex;
    flex-grow: 1;
    justify-content: space-between;
    color: black;
  }
`;

export const SubBody = styled.div`
  padding-bottom: 1rem;
  text-align: center;
`;

export const SubInfo = styled.div`
  flex-direction: column;
  margin: 0 0 0 2rem;
`;

export const SubImage = styled.img`
  margin: auto;
  margin-right: 2rem;
`;

export const PostListContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
`;

export const NoPostsYet = styled.div`
  text-align: center;
  padding: 3rem;
  font-size: 1.5rem;
`;

export const EmptyListWrapper = styled.div`
  height: 50vh;
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const PostListWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export const SubButtonContainer = styled(ButtonContainer)`
  padding-bottom: 0.5rem;
  width: 100%;
  .ant-btn-primary {
    width: 100%;
  }
`;

export const SubRedButtonContainer = styled(RedButtonContainer)`
  width: 100%;
  .ant-btn-primary {
    width: 100%;
  }
`;
