import { localAxios } from "@/util/http-commons.js";

const local = localAxios();

export function getUserIdExists(loginId, success, fail) {
  local.get(`/users/join/${loginId}`).then(success).then(fail);
}
