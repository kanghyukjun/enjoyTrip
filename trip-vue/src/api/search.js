import { localAxios } from "@/util/http-commons.js";

const local = localAxios();

export function getSido() {
  return new Promise((resolve, reject) => {
    local
      .get(`/trip/sido`)
      .then((success) => resolve(success))
      .catch((error) => reject(error));
  });
}

export function getGugun(sido) {
  return new Promise((resolve, reject) => {
    local
      .get(`/trip/gugun?sido=${sido}`)
      .then((success) => resolve(success))
      .catch((error) => reject(error));
  });
}

export function getSpot(sido, gugun, keyword, type) {
  return new Promise((resolve, reject) => {
    let url = `/trip/spot?sido=${sido}&gugun=${gugun}&word=${keyword}&type=`;
    for (let i = 0; i < type.length - 1; i++) {
      url += type[i] + ",";
    }
    url += type[type.length - 1];
    local
      .get(url)
      .then((success) => {
        resolve(success);
      })
      .catch((error) => {
        reject(error);
      });
  });
}

export function addSpot(spotInfo) {
  return new Promise((resolve, reject) => {
    local
      .post(`/trip/${sessionStorage.getItem("loginId")}`, spotInfo, {
        headers: {
          Authorization: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => resolve(response))
      .catch((error) => reject(error));
  });
}
