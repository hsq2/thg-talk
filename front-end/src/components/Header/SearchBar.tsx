import React, { ChangeEvent, useEffect, useState } from "react";
import { Sub } from "../../App";
import { SearchBox, SearchContainer, SearchDropDown } from "./Header.styles";
import Fuse from "fuse.js";
import { List, ListItem, ListItemText } from "@material-ui/core";
import { Link as RouterLink } from "react-router-dom";
import { fetchWrapper } from "../../resources/FetchWrapper";

export const SearchBar: React.FC = () => {
  const [search, setSearch] = useState<string>("");

  const [subTalks, setSubTalks] = useState<Sub[]>([]);

  const [results, setResults] = useState<Sub[]>([]);

  const handleSearchChange = (event: ChangeEvent<HTMLInputElement>) => {
    setSearch(event.target.value);
  };

  useEffect(() => {
    fetchWrapper("/api/subtalk/")
      .then((res) => res?.json())
      .then((data) => {
        setSubTalks(data);
      });
  }, [search]);

  useEffect(() => {
    const fuse = new Fuse(subTalks, { keys: ["subTalkName"] });

    setResults(fuse.search(search, { limit: 10 }).map((res) => res.item));
  }, [search, subTalks]);

  return (
    <SearchContainer>
      <SearchBox
        onChange={handleSearchChange}
        value={search}
        placeholder="Search for SubTalks..."
        search={search}
        onBlur={() => setTimeout(() => setSearch(""), 150)}
        aria-label="Search"
        data-testid="search-box"
      />
      {search && (
        <SearchDropDown data-testid="search-dropdown">
          {results.length > 0 ? (
            <List>
              {results.map((result) => (
                <ListItem
                  button
                  component={RouterLink}
                  to={`/sub/${result.subTalkID}`}
                >
                  <ListItemText primary={result.subTalkName} />
                </ListItem>
              ))}
            </List>
          ) : (
            <ListItem>
              <ListItemText primary={"No results found"} />
            </ListItem>
          )}
        </SearchDropDown>
      )}
    </SearchContainer>
  );
};
