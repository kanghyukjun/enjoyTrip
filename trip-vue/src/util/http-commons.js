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

        console.log(`[http-commons] : accessToken이 만료 되었습니다`);
        // 토큰 재발급 후 다시 요청
        const originalRequest = config;
        const refreshToken = getRefreshToken();

        console.log(`[http-commons] : accessToken을 재발급 합니다`);
        const response = await axios
          .post(
            `/user/refresh/${getLoginId()}`,
            {},
            {
              baseURL: VITE_PJT_API_URL,
              headers: { Authorization: refreshToken },
            }
          )
          .then((response) => response)
          .catch((error) => {
            console.log(`[http-commons] : refreshToken이 만료 되었습니다`);
            console.log(`[http-commons] : 다시 로그인을 해서 Token을 발급받아 주세요`);
            sessionStorage.removeItem("user");
            return Promise.reject(error);
          });

        if (response.status === HttpStatusCode.InternalServerError) {
          console.log(`[http-commons] : 내부 서버 오류`);
          return Promise.reject(error);
        } else {
          console.log(`[http-commons] : accessToken을 재발급 완료`);
          setAccessToken(`Bearer ${response.data["access-token"]}`);
          originalRequest.headers.Authorization = getAccessToken();

          console.log(`[http-commons] : 기존 요청을 다시 보냅니다`);
          return axios(originalRequest);
        }
      } else {
        return Promise.reject(error);
      }
    }
  );

  return instance;
};

const getUserSession = () => {
  return JSON.parse(sessionStorage?.getItem("user"));
};

const getAccessToken = () => {
  return getUserSession()?.accessToken;
};

const getRefreshToken = () => {
  return getUserSession()?.refreshToken;
};

const getLoginId = () => {
  return getUserSession()?.loginId;
};

const setAccessToken = (accessToken) => {
  const user = getUserSession();
  user.accessToken = accessToken;
  sessionStorage.setItem("user", JSON.stringify(user));
};

export { localAxios };
