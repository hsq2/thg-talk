import styled from "styled-components";

export const ContentContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 50%;
`;

export const UserSectionWrapper = styled.div`
  width: 100%;
  display : flex;
  flex-direction : column;
  align-items : center;
  > h2 {
    text-align: center;
  }
  margin : 1rem 0;
`;

export const UserCommentsWrapper = styled.div`
  width: 100%;
`;
export const UserCommentRow = styled.div`
  margin: 1rem 0;
`;
export const LinkWrapper = styled.div`
  display: flex;
  justify-content: flex-end;
`;

export const Selection = styled.div`
  display: flex;
  justify-content: center;
`;

export const UserNameWrapper = styled.div`
    margin : 2rem 0 0 0;
`