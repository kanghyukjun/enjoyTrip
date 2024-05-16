import { localAxios } from "@/util/http-commons.js";

const local = localAxios();

export function getSido(success, fail) {
  local.get(`/trip/sido`).then(success).catch(fail);
}

export function getGugun(sido, success, fail) {
  local.get(`/trip/gugun?sido=${sido}`).then(success).catch(fail);
}

export function getSpot(sido, gugun, keyword, type, success, fail) {
  let url = `/trip/spot?sido=${sido}&gugun=${gugun}&word=${keyword}&type=`;
  for (let i = 0; i < type.length - 1; i++) {
    url += type[i] + ",";
  }
  url += type[type.length - 1];
  local.get(url).then(success).catch(fail);
}
