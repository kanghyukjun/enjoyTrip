import { localAxios } from "@/util/http-commons.js";
// import { HttpStatusCode } from "axios";

const local = localAxios();

export function getUserInfo() {
  return new Promise((resolve, reject) => {
    local
      .get(`/user/${sessionStorage.getItem("loginId")}`)
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
      .put(`/user/${sessionStorage.getItem("loginId")}`, userInfo)
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

export function getUserCourse() {
  return new Promise((resolve, reject) => {
    local
      .get(`/course/${sessionStorage.getItem("loginId")}`)
      .then((response) => resolve(response))
      .catch((error) => reject(error));
  });
}
