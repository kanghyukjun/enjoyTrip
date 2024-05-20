import { localAxios } from "@/util/http-commons.js";

const local = localAxios();

export function getUserInfo() {
  return new Promise((resolve, reject) => {
    local
      .get(`/user/${sessionStorage.getItem("loginId")}`, {
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

export function userUpdate(userInfo) {
  return new Promise((resolve, reject) => {
    local
      .put(`/user/${sessionStorage.getItem("loginId")}`, userInfo, {
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

export function userRegister(data) {
  return new Promise((resolve, reject) => {
    local
      .post(`/user`, data)
      .then((response) => {
        resolve(response);
      })
      .catch((error) => {
        reject(error);
      });
  });
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
