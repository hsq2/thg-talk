import { TextField } from "@material-ui/core";
import { ChangeEvent, Dispatch, SetStateAction } from "react";
import { TitleSectionContainer } from "./Modals.styles";

interface Props {
  title: string;
  setTitle: Dispatch<SetStateAction<string>>;
}

export const TitleSection: React.FC<Props> = ({ title, setTitle }) => {
  const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
    setTitle(event.target.value);
  };

  return (
    <TitleSectionContainer>
      <h2>Title</h2>
      <TextField
        fullWidth
        placeholder="Title"
        onChange={handleChange}
        value={title}
        variant="outlined"
        label="Title"
        name="Title"
        aria-label="Title"
        data-testid="title-field"
      ></TextField>
    </TitleSectionContainer>
  );
};
