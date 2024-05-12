import { localAxios } from "@/util/http-commons.js";

const local = localAxios();

export function name(id, success, fail) {
  local.get(`/user/${id}`).then(success).then(fail);
}
