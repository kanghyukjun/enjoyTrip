import { ref } from "vue";
import { defineStore } from "pinia";
import { localAxios } from "@/util/http-commons.js";

const local = localAxios();

// 로그인 여부만 판단
export const useUserStore = defineStore(
  "user",
  () => {
    const isLogined = ref(false);

    const loginId = ref("");
    const accessToken = ref("");
    const refreshToken = ref("");

    const setAccessToken = (newToken) => {
      accessToken.value = newToken;
    };

    const login = (userInfo) => {
      return new Promise((resolve, reject) => {
        userLogin(userInfo)
          .then((response) => {
            loginId.value = userInfo.loginId;
            const { data } = response;
            accessToken.value = `Bearer ${data["access-token"]}`;
            refreshToken.value = `Bearer ${data["refresh-token"]}`;
            // sessionStorage.setItem("accessToken", `Bearer ${accessToken}`);
            // sessionStorage.setItem("refreshToken", `Bearer ${refreshToken}`);
            // sessionStorage.setItem("loginId", userInfo.loginId);
            isLogined.value = true;
            resolve();
          })
          .catch((error) => {
            console.log(error); // 디버깅을 위한 코드
            isLogined.value = false;
            reject();
          });
      });
    };

    const logout = async () => {
      if (!isLogined.value) {
        return false;
      } else {
        userLogout()
          .then(() => {
            isLogined.value = false;
            (loginId.value = ""), (accessToken.value = ""), (refreshToken.value = "");
            return true;
          })
          .catch((error) => {
            console.log(error); // 디버깅을 위한 코드
            return false;
          });
      }
    };

    return {
      isLogined,
      loginId,
      accessToken,
      refreshToken,
      setAccessToken,
      login,
      logout,
    };
  },
  { persist: { storage: sessionStorage } }
);

// axios 비동기 코드
function userLogin(data) {
  return new Promise((resolve, reject) => {
    local
      .post(`/user/login`, data)
      .then((success) => {
        resolve(success);
      })
      .catch((fail) => {
        reject(fail);
      });
  });
}

function userLogout() {
  return new Promise((resolve, reject) => {
    local
      .get(`/user/logout/${sessionStorage.getItem("loginId")}`)
      .then((response) => resolve(response))
      .catch((error) => reject(error));
  });
}
