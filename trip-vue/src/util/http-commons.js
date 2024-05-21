import axios, { HttpStatusCode } from "axios";

const { VITE_PJT_API_URL } = import.meta.env;

const localAxios = () => {
  const instance = axios.create({
    baseURL: VITE_PJT_API_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });

  instance.interceptors.request.use(
    (config) => {
      // 헤더에 accessToken 담기
      console.log("[http-commons] : accessToken 담기");
      const token = getAccessToken();

      if (!token) {
        console.log("[http-commons] : 생성된 accessToken이 없습니다");
        config.headers.Authorization = null;
        return config;
      }

      if (config.headers && token) {
        console.log("[http-commons] : accessToken을 헤더에 담습니다");
        config.headers.Authorization = token;
        return config;
      }

      return config;
    },
    (error) => {
      // 에러 처리
      console.log(`[http-commons] : ${error}`);
      return Promise.reject(error);
    }
  );

  instance.interceptors.response.use(
    (response) => {
      // 정상적으로 응답을 받았을 때 리턴하기
      console.log(`[http-commons] : 정상 처리 완료`);
      console.log(response);
      return response;
    },
    async (error) => {
      // 만약 Unauthorized Error를 받았을 때 accessToken 발급 후 다시 전송
      console.log(`[http-commons] : ${error}`);
      const {
        config,
        response: { status },
      } = error;

      if (status === HttpStatusCode.Unauthorized) {
        if (!getAccessToken()) {
          console.log(`[http-commons] : accessToken이 존재하지 않습니다`);
          return Promise.reject(error);
        }

        console.log(`[http-commons] : accessToken을 재발급 합니다`);

        // 토큰 재발급 후 다시 요청
        const originalRequest = config;
        const refreshToken = getRefreshToken();

        const data = await axios.post(
          `/user/refresh/${getLoginId()}`,
          {},
          {
            baseURL: VITE_PJT_API_URL,
            headers: { Authorization: refreshToken },
          }
        );

        console.log(`[http-commons] : accessToken을 재발급 완료`);
        sessionStorage.setItem("accessToken", `Bearer ${data.data["access-token"]}`);
        originalRequest.headers.Authorization = getAccessToken();

        console.log(`[http-commons] : 기존 요청을 다시 보냅니다`);
        return axios(originalRequest);
      } else {
        return Promise.reject(error);
      }
    }
  );

  return instance;
};

const getAccessToken = () => {
  return sessionStorage.getItem("accessToken");
};

const getRefreshToken = () => {
  return sessionStorage.getItem("refreshToken");
};

const getLoginId = () => {
  return sessionStorage.getItem("loginId");
};

export { localAxios };
