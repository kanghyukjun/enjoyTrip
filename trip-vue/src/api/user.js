import { localAxios } from "@/util/http-commons.js";
// import { HttpStatusCode } from "axios";

const local = localAxios();

export function getUserInfo(loginId) {
  return new Promise((resolve, reject) => {
    local
      .get(`/user/${loginId}`)
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
      .put(`/user/${loginId}`, userInfo)
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

export function deleteUserCourse(loginId, boardId) {
  return new Promise((resolve, reject) => {
    local
      .delete(`/board/${boardId}/${loginId}`)
      .then((response) => resolve(response))
      .catch((error) => reject(error));
  });
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

export function getUserCourse(loginId) {
  return new Promise((resolve, reject) => {
    local
      .get(`/course/${loginId}`)
      .then((response) => resolve(response))
      .catch((error) => reject(error));
  });
}

export function getUserArticle(loginId) {
  return new Promise((resolve, reject) => {
    local
      .get(`/board/user/${loginId}`)
      .then((response) => resolve(response))
      .catch((error) => reject(error));
  });
}

export function getPassword(data) {
  return new Promise((resolve, reject) => {
    local
      .post(`/user/pwd`, data)
      .then((response) => resolve(response))
      .catch((error) => reject(error));
  });
}
