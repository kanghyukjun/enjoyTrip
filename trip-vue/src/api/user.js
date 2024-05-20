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

export function getUserIdExists(loginId, success, fail) {
  local.get(`/user/join/${loginId}`).then(success).catch(fail);
}

export function userRegister(data, success, fail) {
  local.post(`/user`, data).then(success).catch(fail);
}

export function userLogin(data) {
  return new Promise((resolve, reject) => {
    local
      .post(`/user/login`, data)
      .then((success) => {
        resolve(success);
      })
      .catch((fail) => {
        reject(fail);
      });
  });
}
