import { localAxios } from "@/util/http-commons.js";

const local = localAxios();

export function registArticle(loginId, data) {
  return new Promise((resolve, reject) => {
    local
      .post(`/board/${loginId}`, data)
      .then((response) => resolve(response))
      .catch((error) => reject(error));
  });
}

export function updateArticle(boardId, loginId, data) {
  return new Promise((resolve, reject) => {
    local
      .put(`/board/${boardId}/${loginId}`, data)
      .then((response) => resolve(response))
      .catch((error) => reject(error));
  });
}

export function getArticleList(pgno, word) {
  return new Promise((resolve, reject) => {
    local
      .get(`/board?pgno=${pgno}&word=${word}`)
      .then((response) => resolve(response))
      .catch((error) => reject(error));
  });
}

export function getArticleDetail(boardId) {
  return new Promise((resolve, reject) => {
    local
      .get(`/board/${boardId}`)
      .then((response) => resolve(response))
      .catch((error) => reject(error));
  });
}
