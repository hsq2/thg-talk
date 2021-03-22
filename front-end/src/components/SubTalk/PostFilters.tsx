import { FormControl, MenuItem, Select } from "@material-ui/core";
import { useState } from "react";
import { Wrapper } from "./PostFilters.styles";

interface Props {
  changeSort: (value: SortType) => void;
  changeTime: (value: TimeFrame) => void;
}

export enum SortType {
  MOSTPOPULAR = "most popular",
  LEASTPOPULAR = "least popular",
  MOSTRECENT = "newest",
  LEASTRECENT = "oldest",
}

export enum TimeFrame {
  DAY = "day",
  WEEK = "week",
  MONTH = "month",
  YEAR = "year",
  ALLTIME = "all time",
}
export const PostFilters: React.FC<Props> = ({ changeTime, changeSort }) => {
  const [sortType, setSortType] = useState<SortType>(SortType.MOSTPOPULAR);
  const [timeFrame, setTimeFrame] = useState<TimeFrame>(TimeFrame.ALLTIME);

  const handleSortChange = (event: React.ChangeEvent<{ value: unknown }>) => {
    setSortType(event.target.value as SortType);
    changeSort(event.target.value as SortType);
  };

  const handleTimeChange = (event: React.ChangeEvent<{ value: unknown }>) => {
    setTimeFrame(event.target.value as TimeFrame);
    changeTime(event.target.value as TimeFrame);
  };

  return (
    <Wrapper>
      Sort by&nbsp;
      <FormControl>
        <Select
          inputProps={{
            "data-testid": "sort-select",
          }}
          value={sortType}
          onChange={handleSortChange}
          MenuProps={{ disableScrollLock: true }}
          aria-label="Dropdown"
        >
          {Object.values(SortType).map((value) => (
            <MenuItem aria-label={value} value={value}>{value}</MenuItem>
          ))}
        </Select>
      </FormControl>
      &nbsp; from the last&nbsp;
      <FormControl>
        <Select
          inputProps={{
            "data-testid": "time-select",
          }}
          value={timeFrame}
          onChange={handleTimeChange}
          MenuProps={{ disableScrollLock: true }}
          aria-label="Dropdown"
        >
          {Object.values(TimeFrame).map((value) => (
            <MenuItem aria-label={value} value={value}>{value}</MenuItem>
          ))}
        </Select>
      </FormControl>
    </Wrapper>
  );
};
