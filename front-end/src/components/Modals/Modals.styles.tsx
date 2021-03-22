import styled from "styled-components";
import { Button } from "../../App.styles";

export const ModalContainer = styled.div`
  height: 38rem; //90vh
  width: 45rem; //50vh
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  border-radius: 5px;
  margin: auto;
  background-color: white;
  box-sizing: border-box;
  font-family: ${(props) => props.theme.fontFamily};
  @media only screen and (max-width: ${(props) =>
      props.theme.mobileThreshold}) {
    width: 90vw;
  }
`;

export const SmallModalContainer = styled.div`
  height: 15rem; //35vh
  width: 35rem; //50vw
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  border-radius: 5px;
  margin: auto;
  background-color: white;
  box-sizing: border-box;
  font-family: ${(props) => props.theme.fontFamily};
`;

export const ModalHeader = styled.div`
  width: 100%;
  background-color: ${(props) => props.theme.primaryColour};
  text-align: center;
  margin: 0;
  padding: 1rem;
  box-sizing: border-box;
  border-top-left-radius: 5px;
  border-top-right-radius: 5px;
  color: white;
  > h1 {
    color: white;
    margin-bottom: 0;
  }
`;

export const ModalBody = styled.div`
  padding: 1rem 4rem;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  /* overflow-y: scroll; */
`;

export const SmallModalBody = styled.div`
  padding: 1rem 4rem 2rem;
  height: 40%;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  text-align: center;
  font-size: 1.2rem;
  justify-content: center;
`;

export const SmallModalFooter = styled.div`
  width: 100%;
  box-sizing: border-box;
  display: inline-flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  gap: 1em;
  padding: 0rem 2rem 2rem;
`;

export const ModalFooter = styled.div`
  width: 100%;
  box-sizing: border-box;
  display: inline-flex;
  flex-wrap: wrap;
  justify-content: flex-end;
  align-items: center;
  gap: 1em;
  padding: 1rem 4rem 2rem; //0rem 4rem 2rem
`;

export const TitleSectionContainer = styled.div``;

export const DescriptionSectionContainer = styled.div`
  padding-top:0.5rem;
`;

export const ThumbnailSectionContainer = styled.div`
padding-top:0.5rem;`;

export const SubmitButton = styled(Button)`
  margin: 0;
  align-items: center;
  background-color: ${(props) => props.theme.primaryColour};
`;

export const CancelButton = styled(Button)`
  background-color: inherit;
  color: black;
`;
