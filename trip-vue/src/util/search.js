import { getSido, getGugun } from "@/api/search";

export const getSidos = () => {
  return new Promise((resolve, reject) => {
    getSido(
      (response) => {
        resolve(response.data);
      },
      (error) => {
        reject(error);
      }
    );
  });
};

export const getGuguns = (sidoCode) => {
  return new Promise((resolve, reject) => {
    getGugun(
      sidoCode,
      (response) => {
        resolve(response.data);
      },
      (error) => {
        reject(error);
      }
    );
  });
};
