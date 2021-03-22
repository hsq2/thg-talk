import styled from "styled-components";

export const HeaderBar = styled.header`
  display: grid;
  grid-template-columns: 2fr 5fr 2fr;
  align-items: center;
  justify-items: center;
  padding: 0.1rem;
  background-color: ${(props) => props.theme.primaryColour};
  font-family: ${(props) => props.theme.fontFamily};
`;

export const Logo = styled.img`
  height: 3rem;
  margin-left: auto;
  margin-right: auto;

  @media only screen and (max-width: ${(props) =>
      props.theme.mobileThreshold}) {
    margin-left: 0;
  }
`;

export const SearchContainer = styled.div`
  height: 3rem;
  width: 100%;
  display: flex;
  flex-direction: column;
  @media only screen and (max-width: ${(props) =>
      props.theme.mobileThreshold}) {
  }
`;

interface SearchProps {
  search: string;
}

export const SearchBox = styled.input<SearchProps>`
  border:2px solid ${(props) => props.theme.primaryColour};
  height: 3rem;
  width: 100%;
  box-sizing: border-box;
  margin-top: auto;
  margin-bottom: auto;
  border-radius: ${(props) => (props.search ? "22px 22px 0 0" : "50px")};
  padding: 1rem;
  background-color: white;
  outline: none;
  font-size: 1.4rem;

  /* :focus {
    outline: 5px auto -webkit-focus-ring-color;
  } */
`;

export const SearchDropDown = styled.div`
  background-color: white;
  border: 2px solid ${(props) => props.theme.primaryColour};
  border-top: none;
  box-sizing: border-box;
  z-index: 2;
  width: 100%;
  :focus {
    outline: 5px auto -webkit-focus-ring-color;
  }
`;

export const AccountLogo = styled.button`
  position: relative;
  /* width: 3rem; */
  /* height: 2rem; */
  background-color: transparent;
  border: none;

  cursor: pointer;

  &:focus {
    outline: none;
  }

  img {
    position: absolute;
    width: 2.8rem;
    height: auto;
    top: 0%;
    right: 0%;
  }
`;