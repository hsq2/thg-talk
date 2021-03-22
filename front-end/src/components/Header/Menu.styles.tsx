import styled from "styled-components";

export const StyledMenu = styled.nav`
  border: 4px solid ${(props) => props.theme.secondaryColour};
  border-radius: 5px;
  display: flex;
  flex-direction: column;
  /* justify-content: left; */
  text-align: left;
  padding: 1rem;
  position: absolute;
  top: 4rem;
  right: 0;
  background-color: ${(props) => props.theme.alternateRowBackground};
  transition: transform 0.3s ease-in-out;
`;
