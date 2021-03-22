import { TextField } from "@material-ui/core";
import { ChangeEvent, Dispatch, SetStateAction } from "react";
import { ThumbnailSectionContainer } from "./Modals.styles";

interface Props {
  thumbnail: string;
  setThumbnail: Dispatch<SetStateAction<string>>;
}

export const ThumbnailSection: React.FC<Props> = ({
  thumbnail,
  setThumbnail,
}) => {
  const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
    setThumbnail(event.target.value);
  };

  return (
    <ThumbnailSectionContainer>
      <h2>Thumbnail (Optional)</h2>
      <TextField
        fullWidth
        placeholder="Thumbnail URL"
        onChange={handleChange}
        value={thumbnail}
        variant="outlined"
        label="Thumbnail"
        name="Thumbnail"
        aria-label="Thumbnail"
      ></TextField>
    </ThumbnailSectionContainer>
  );
};
