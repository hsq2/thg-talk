import styled from "styled-components";

export const PostWrapper = styled.div`
  margin-bottom: 2rem;
  text-align: center;
`;

export const MainContentWrapper = styled.div`
  flex-grow: 1;
  padding: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 50em;
`;

export const PageWrapper = styled.div`
  /* display: grid;
  grid-template-columns: 0.5fr 1fr 0.5fr; */
  display: flex;
  margin-top: 1em;
  width: 100%;
  justify-content: space-around;

  @media only screen and (max-width: ${(props) =>
      props.theme.mobileThreshold}) {
    display: flex;
    flex-direction: column;
  }
`;

export const WikiWrapper = styled.div`
  background: #f8f8f8;
  width: 100%;
  padding: 1em;
  margin-bottom: 1em;
`;

export const WikiHeadline = styled.p`
  margin-bottom: 0;
`;

export const PageWrapperCentered = styled.div`
  display: flex;
  margin-top: 1em;
  width: 100%;
  justify-content: center;

  @media only screen and (max-width: ${(props) =>
      props.theme.mobileThreshold}) {
    display: flex;
    flex-direction: column;
  }
`;

export const PageWrapperSingle = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 1em;
`;

export const MenuWrapper = styled.aside`
`

export const MenuWrapperHeader = styled.h2`
  text-transform: uppercase;
  font-size: 1.5em;
  margin-bottom: 0.5em;
`;

export const MenuItemsWrapper = styled.div`
  display: grid;
  grid-row-gap: 0.5em;
`;

interface MenuItemProps {
  selected: boolean;
}

export const MenuItem = styled.button<MenuItemProps>`
  background: ${(p: MenuItemProps) =>
    p.selected ? "rgba(138,43,226, 0.5)" : "white"};
  color: white;
  padding: 0 1em;
  max-width: 15em;
  border: none;
  outline: none;

  :hover {
    background-color: rgba(138, 43, 226, 0.5);

  transition:  0.4s;
  cursor: pointer;
  }

  :focus-visible {
    outline: 5px auto -webkit-focus-ring-color;
  }
`;
export const MenuItemContent = styled.div`
  font-size: 1.5em;
  color: black;
`;

export const RightSectionWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  min-width: 15em;

  @media (min-width: 601px) {
    margin-top: 8em;
  }
`;
export const PostWrapperHeader = styled.h1`
  text-align: center;
`;

export const SubTalkGridContainer = styled.div`
  display: grid;
  width: 100%;
  grid-template-columns: 1fr 1fr;
  grid-gap: 1rem;
  margin: 1rem;
`;

export const SubtalkCardWrapper = styled.div`
  padding: 1rem;
`;

export const SubtalkTitle = styled.div`
  text-align: center;
  font-weight: bold;
  font-size: 1.3rem;
  color:black;
`;

export const SubtalkDescription = styled.div`
padding : 0 1rem;
text-align : center;
color:black;
`

export const LoadingWrapper = styled.div`
  width : 100%;
  text-align : center;
`