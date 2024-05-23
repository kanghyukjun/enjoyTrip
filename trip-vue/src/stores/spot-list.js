import { ref } from "vue";
import { defineStore } from "pinia";

export const useSpotListStore = defineStore("spot-list", () => {
  const radioDatas = ref([
    {
      id: 12,
      name: "관광지",
      checked: false,
    },
    {
      id: 14,
      name: "문화시설",
      checked: false,
    },
    {
      id: 15,
      name: "축제공연행사",
      checked: false,
    },
    {
      id: 25,
      name: "여행코스",
      checked: false,
    },
    {
      id: 28,
      name: "레포츠",
      checked: false,
    },
    {
      id: 32,
      name: "숙박",
      checked: false,
    },
    {
      id: 38,
      name: "쇼핑",
      checked: false,
    },
    {
      id: 39,
      name: "음식점",
      checked: false,
    },
  ]);

  const selectedSpotList = ref([]);

  const isAlreadySelected = (spot) => {
    for (let i = 0; i < selectedSpotList.value.length; i++) {
      if (selectedSpotList.value[i].id === spot.id) return true;
    }
    return false;
  };

  return { radioDatas, selectedSpotList, isAlreadySelected };
});
