import { localAxios } from "@/util/http-commons.js";

const local = localAxios();

export function addTripPlan(data) {
  return new Promise((resolve, reject) => {
    console.log(data);
    local
      .post(`/course/${sessionStorage.getItem("loginId")}`, data, {
        headers: {
          Authorization: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        resolve(response);
      })
      .catch((error) => reject(error));
  });
}
