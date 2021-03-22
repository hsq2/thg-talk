import { TextField } from "@material-ui/core";
import { ChangeEvent, Dispatch, SetStateAction } from "react";
import { DescriptionSectionContainer } from "./Modals.styles";

interface Props {
  description: string;
  setDescription: Dispatch<SetStateAction<string>>;
  creating: "sub" | "post";
}

export const DescriptionSection: React.FC<Props> = ({
  description,
  setDescription,
  creating,
}) => {
  const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
    setDescription(event.target.value);
  };

  return (
    <DescriptionSectionContainer>
      <h2>{creating === "post" ? "Post" : "Description "}</h2>
      <TextField
        fullWidth
        placeholder={creating === "post" ? "Post" : "Description"}
        multiline
        rows={6}
        onChange={handleChange}
        value={description}
        variant="outlined"
        label={creating === "post" ? "Post" : "Description"}  
        name="Description"
        aria-label="Description"
      ></TextField>
    </DescriptionSectionContainer>
  );
};
