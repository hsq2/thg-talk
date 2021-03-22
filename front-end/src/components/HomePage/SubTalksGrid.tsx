import { AppstoreAddOutlined } from "@ant-design/icons";
import { FC, useEffect, useState } from "react";
import { Sub } from "../../App";
import { Loading } from "../../Loading";
import {
  LoadingWrapper,
  PostWrapperHeader,
  SubTalkGridContainer,
} from "./Homepage.styles";
import {SubTalksCard} from "./SubTalksCard"
import { fetchWrapper } from "../../resources/FetchWrapper";

export const SubTalksGrid: FC = () => {
  const [subtalks, setSubtalks] = useState<Sub[] | null | undefined>(undefined);

  useEffect(() => {
    fetchWrapper("/api/subtalk", {
      method: "GET",
    })
      .then((response) => {
        if (response?.ok) return response?.json();
        else throw new Error("Error fetching SubTalks");
      })
      .then((subs: Sub[]) => {
        setSubtalks(subs);
      })
      .catch(() => {});
  }, []);

  return (
    <>
      <PostWrapperHeader>
        <AppstoreAddOutlined />
        <strong> SubTalks</strong>
      </PostWrapperHeader>
      {subtalks ? (
        <SubTalkGridContainer>
          {subtalks.map((subtalk) => {
            return (
              <SubTalksCard subtalk={subtalk}/>
            );
          })}
        </SubTalkGridContainer>
      ) : (
        <LoadingWrapper>
          <Loading />
        </LoadingWrapper>
      )}
    </>
  );
};
