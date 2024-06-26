import { localAxios } from "@/util/http-commons.js";

const local = localAxios();

export function addTripPlan(loginId, data) {
  return new Promise((resolve, reject) => {
    local
      .post(`/course/${loginId}`, data)
      .then((response) => {
        resolve(response);
      })
      .catch((error) => reject(error));
  });
}

export function getTripCourseDetail(loginId, courseId) {
  return new Promise((resolve, reject) => {
    local
      .get(`/course/${loginId}/${courseId}`)
      .then((response) => {
        resolve(response);
      })
      .catch((error) => reject(error));
  });
}

export function deleteTripCourse(loginId, courseId) {
  return new Promise((resolve, reject) => {
    local
      .delete(`/course/${loginId}/${courseId}`)
      .then((response) => {
        resolve(response);
      })
      .catch((error) => reject(error));
  });
}

export function updateTripCourse(loginId, courseId, data) {
  return new Promise((resolve, reject) => {
    local
      .put(`/course/${loginId}/${courseId}`, data)
      .then((response) => {
        resolve(response);
      })
      .catch((error) => reject(error));
  });
}

export function getOtherTripCourse(loginId, courseId) {
  return new Promise((resolve, reject) => {
    local
      .post(`/board/${courseId}/${loginId}`)
      .then((response) => resolve(response))
      .catch((error) => reject(error));
  });
}
