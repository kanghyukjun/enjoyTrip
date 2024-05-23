import { localAxios } from "@/util/http-commons.js";

const local = localAxios();

export function getHit() {
  return new Promise((resolve, reject) => {
    local
      .get(`/board/hit`)
      .then((response) => resolve(response))
      .catch((error) => reject(error));
  });
}
