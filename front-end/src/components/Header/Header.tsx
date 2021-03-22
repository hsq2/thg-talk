import { HeaderBar, Logo } from "./Header.styles";
import { FocusLink } from "../../App.styles";
import { SimpleMenu } from "./Menu";
import { SearchBar } from "./SearchBar";

export const Header: React.FC = () => {
  return (
    <>
      <HeaderBar>
        <div data-testid="logo">
          <FocusLink to="/" aria-label="home">
            <Logo src="/MicrosoftTeams-image.png" alt="home" />
          </FocusLink>
        </div>
        <SearchBar />
        <div>
          <SimpleMenu />
        </div>
      </HeaderBar>
    </>
  );
};
