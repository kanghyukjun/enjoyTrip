import { ref } from "vue";
import { defineStore } from "pinia";
import { userLogin } from "@/api/user";
import { HttpStatusCode } from "axios";

export const useUserStore = defineStore("user", () => {
  const isLogin = ref(false);
  const isLoginError = ref(false);
  const isValidToken = ref(false);

  const login = async (userInfo) => {
    return new Promise((resolve, reject) => {
      userLogin(userInfo)
        .then((response) => {
          if (response.status === HttpStatusCode.Created) {
            let { data } = response;
            let accessToken = data["access-token"];
            let refreshToken = data["refresh-token"];

            isLogin.value = true;
            isLoginError.value = false;
            isValidToken.value = true;
            sessionStorage.setItem("accessToken", "Bearer " + accessToken);
            sessionStorage.setItem("refreshToken", "Bearer " + refreshToken);
            sessionStorage.setItem("loginId", userInfo.loginId);
            resolve(true);
          } else {
            isLogin.value = false;
            isLoginError.value = true;
            isValidToken.value = false;
            reject(false);
          }
        })
        .catch((error) => {
          console.log(error);
          reject(false);
        });
    });
  };

  return { isLogin, isLoginError, isValidToken, login };
});
