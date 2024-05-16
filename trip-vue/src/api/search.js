import { localAxios } from "@/util/http-commons.js";

const local = localAxios();

export function getSido(success, fail) {
  local.get(`/trip/sido`).then(success).then(fail);
}
