import { Empty } from "antd";
import { useState } from "react";
import { Post, PostVote } from "../../App";
import { Loading } from "../../Loading";
import { PostFilters, SortType, TimeFrame } from "./PostFilters";
import { PostRow } from "./PostRow";
import {
  EmptyListWrapper,
  NoPostsYet,
  PostListContainer,
  PostListWrapper,
} from "./SubTalk.styles";

interface Props {
  posts: Post[] | null | undefined;
}

export const calculateNumberOfVotes = (
  votes: PostVote[] | undefined | null
): number => {
  if (votes === undefined || votes === null) {
    return 0;
  }
  return votes.reduce((acc, v) => {
    if (v.voteType === "UPVOTE") {
      acc += 1;
    } else {
      acc -= 1;
    }
    return acc;
  }, 0);
};

export const PostList: React.FC<Props> = ({ posts }) => {
  const changeSort = (sortType: SortType): ((a: Post, b: Post) => number) => {
    let sortFun;
    switch (sortType) {
      case SortType.MOSTPOPULAR:
        sortFun = (a: Post, b: Post) => {
          return -(
            calculateNumberOfVotes(a?.votes) - calculateNumberOfVotes(b?.votes)
          );
        };
        break;
      case SortType.LEASTPOPULAR:
        sortFun = (a: Post, b: Post) => {
          return (
            calculateNumberOfVotes(a?.votes) - calculateNumberOfVotes(b?.votes)
          );
        };
        break;

      case SortType.MOSTRECENT:
        sortFun = (a: Post, b: Post) => {
          return Date.parse(b.dateCreated) - Date.parse(a.dateCreated);
        };
        break;

      case SortType.LEASTRECENT:
        sortFun = (a: Post, b: Post) => {
          return Date.parse(a.dateCreated) - Date.parse(b.dateCreated);
        };
        break;

      default:
        sortFun = (a: Post, b: Post) => {
          return 1;
        };
        break;
    }
    return sortFun;
  };

  const changeTime = (timeFrame: TimeFrame): ((a: Post) => boolean) => {
    if (timeFrame === TimeFrame.ALLTIME) {
      return (p: Post) => true;
    }
    let timeDelta: number;
    switch (timeFrame) {
      case TimeFrame.DAY:
        timeDelta = 60 * 60 * 1000 * 24;
        break;
      case TimeFrame.WEEK:
        timeDelta = 60 * 60 * 1000 * 24 * 7;
        break;
      case TimeFrame.MONTH:
        timeDelta = 60 * 60 * 1000 * 24 * 7 * 4;
        break;
      case TimeFrame.YEAR:
        timeDelta = 60 * 60 * 1000 * 24 * 365;
        break;
      default:
        break;
    }

    return (a: Post) => a && Date.parse(a.dateCreated) + timeDelta > Date.now();
  };

  const handleChangeTime = (time: TimeFrame) => {
    setTimeFilter(() => changeTime(time));
  };

  const handleChangeSort = (sort: SortType) => {
    setSortFunc(() => changeSort(sort));
  };
  const [timeFilter, setTimeFilter] = useState<(p: Post) => boolean>(() =>
    changeTime(TimeFrame.ALLTIME)
  );
  const [sortFunc, setSortFunc] = useState<(a: Post, b: Post) => number>(() =>
    changeSort(SortType.MOSTPOPULAR)
  );

  const sortedList = () => {
    if (posts) {
      console.log(
        posts.filter((p) => timeFilter(p)).sort((a, b) => sortFunc(a, b))
      );
      return posts.filter((p) => timeFilter(p)).sort((a, b) => sortFunc(a, b));
    }
    console.log([]);
    return [];
  };

  switch (posts) {
    case undefined:
      return <Loading />;
    case null:
      return <>Error loading posts</>;
    default:
      return posts.length ? (
        <PostListWrapper>
          <PostFilters
            changeSort={handleChangeSort}
            changeTime={handleChangeTime}
          />
          <PostListContainer data-testid="post-list-container">
            {sortedList().length !== 0 &&
              sortedList().map((post: Post) => {
                return <PostRow post={post} key={post.postID} />;
              })}

            {sortedList().length === 0 && (
              <EmptyListWrapper>
                <Empty />
              </EmptyListWrapper>
            )}
          </PostListContainer>
        </PostListWrapper>
      ) : (
        <NoPostsYet data-testid="no-posts-yet">No posts yet!</NoPostsYet>
      );
  }
};
