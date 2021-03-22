import {
  ArrowDownward,
  ArrowUpward,
  CalendarToday,
  Person,
  Schedule,
} from "@material-ui/icons";
import { Link } from "react-router-dom";
import styled from "styled-components";
import { defaultTheme } from "./themes/Theme";

export const Button = styled.button`
  cursor: pointer;
  padding: 10px;
  margin: 1rem;
  border-radius: 5px;
  font-size: 1.25rem;
  color: white; //${(props) => props.theme.secondaryColour};
  background-color: ${(props) => props.theme.primaryColour};
  border: 2px solid white;
  font-weight: bold;
  outline: none;
  font-family: ${(props) => props.theme.fontFamily};
  display : flex;
  :active {
    box-shadow: inset 0 0 3px white;
  }
  :hover {
    color: ${(props) => props.theme.primaryColour};
    background-color: ${(props) => props.theme.secondaryColour};
  }
  :focus-visible {
    color: ${(props) => props.theme.primaryColour};
    background-color: ${(props) => props.theme.secondaryColour};
    outline: 5px auto -webkit-focus-ring-color;
    }
`;

export const Body = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  font-family: ${defaultTheme.fontFamily};

  > h1 {
    text-align: center;
  }
`;

export const StyledArrowUpward = styled(ArrowUpward)`
  color: ${(props) => props.theme.secondaryColour};
`;
export const StyledArrowDownward = styled(ArrowDownward)`
  color: ${(props) => props.theme.secondaryColour};
`;

export const StyledUserIcon = styled(Person)`
  color: ${(props) => props.theme.secondaryColour};
  margin: 0.4rem 0.4rem 0.4rem 0;
`;

export const StyledCalendarIcon = styled(CalendarToday)`
  color: ${(props) => props.theme.secondaryColour};
  margin: 0.4rem 0.4rem 0.4rem 0;
`;

export const StyledClockIcon = styled(Schedule)`
  margin: 0.4rem 0.4rem 0.4rem 0;

  color: ${(props) => props.theme.secondaryColour};
`;

export const SubLink = styled(Link)`
  color: inherit;
  text-decoration: none;

  :hover, :focus {
    text-decoration: underline;
    text-decoration-color: ${(props) => props.theme.secondaryColour};
  }
`;

export const NotFoundPage = styled.div`
  color: ${(props) => props.theme.primaryColour};
  font-size: 2rem;
  font-weight: bold;

  .oops{ font-size: 5rem;}
`;

export const ImageSad = styled.img`
  width: 35rem;
`;

export const FocusLink = styled(Link)`
  :focus-visible {
    /* outline: 2px solid ${(props) => props.theme.secondaryColour}; */
    outline: 5px auto -webkit-focus-ring-color;
  }
`;

export const ButtonContainer = styled.div`
  .ant-btn-primary {
    background-color: ${(props) => props.theme.primaryColour};
    border: none transparent;
    :hover {
      opacity:0.9;
    }
    :active {
      background-color:${(props) => props.theme.secondaryColour}
    }
    :focus-visible {
      outline: 5px auto -webkit-focus-ring-color;
    }
  }
`;

export const RedButtonContainer = styled.div`
  .ant-btn-primary {
    background-color: #C9201D;
    border: none transparent;
    :hover {
      /* opacity:0.9; */
    }
    :active {
      background-color:${(props) => props.theme.secondaryColour}
    }
    :focus-visible {
      outline: 5px auto -webkit-focus-ring-color;
    }
  }
`;