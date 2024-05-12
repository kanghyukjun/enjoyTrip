import axios from "axios";

const { VITE_PJT_API_URL } = import.meta.env;

const localAxios = () => {
  const instance = axios.create({
    baseURL: VITE_PJT_API_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });
  return instance;
};

export { localAxios };
