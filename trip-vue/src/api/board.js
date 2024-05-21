import { localAxios } from "@/util/http-commons.js";

const local = localAxios();

export function registArticle(data) {
  return new Promise((resolve, reject) => {
    local
      .post(`/board/${sessionStorage.getItem("loginId")}`, data)
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
