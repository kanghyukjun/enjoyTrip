import { localAxios } from "@/util/http-commons.js";

const local = localAxios();

export function getUserInfo(loginId) {
  return new Promise((resolve, reject) => {
    local
      .get(`/user/${loginId}`, {
        headers: {
          Authorization: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        resolve(response);
      })
      .catch((error) => {
        reject(error);
      });
  });
}

export function userUpdate(loginId, userInfo) {
  console.log(loginId);
  console.log(userInfo);
  return new Promise((resolve, reject) => {
    local
      .put(`/user/${loginId}`, userInfo, {
        headers: {
          Authorization: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        resolve(response);
      })
      .catch((error) => {
        reject(error);
      });
  });
}
