import UserService from "./UserService";

export const fetchWrapper = async (
  url: RequestInfo,
  options?: RequestInit | undefined
) => {
  // const update = new Promise((resolve, reject) => {
  //   UserService.updateToken(5).success(resolve).error(reject);
  // });
  // await update;
  if (options?.headers) {
    options.headers = {
      ...options.headers,
      Authorization: `Bearer ${UserService.getToken()}`,
    };
  } else if (options) {
    options.headers = { Authorization: `Bearer ${UserService.getToken()}` };
  } else {
    options = {
      headers: { Authorization: `Bearer ${UserService.getToken()}` },
    };
  }
  return fetch(url, options).then((res) => {
    if (res.status === 401 || res.status === 403) {
      alert("Cannot authenticate/authorize user");
      UserService.logout();
      throw new Error("Cannot authenticate/authorize user");
    } else {
      return res;
    }
  });
};
