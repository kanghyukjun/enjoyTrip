import { getSido, getGugun, getSpot } from "@/api/search";

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

export const getSpots = (sido, gugun, keyword, type) => {
  return new Promise((resolve, reject) => {
    getSpot(
      sido,
      gugun,
      keyword,
      type,
      (response) => {
        resolve(response.data);
      },
      (error) => {
        reject(error);
      }
    );
  });
};
