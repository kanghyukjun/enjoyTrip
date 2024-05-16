<script setup>
import { RouterLink } from "vue-router";
import { onMounted, ref } from "vue";
import { KakaoMap, KakaoMapMarker } from "vue3-kakao-maps";

import { getSido, getGugun, getSpot } from "@/api/search";

import VCheckbox from "./item/VCheckbox.vue";
import VDropdown from "./item/VDropdown.vue";
import SpotSearchItem from "./item/SpotSearchItem.vue";

const coordinate = {
  lat: 37.566826,
  lng: 126.9786567,
};

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

const mapWidth = ref(0);
const mapHeight = ref(0);
const isLoaded = ref(false);

const sido = ref([]);
const gugun = ref([]);
const keyword = ref("");
const spots = ref([]);

let selectedSidoCode = 0;
let selectedGugunCode = 0;

onMounted(() => {
  const container = document.querySelector("#container");
  mapWidth.value = container.offsetWidth * 0.95;
  mapHeight.value = container.offsetHeight * 0.95;
  isLoaded.value = true;

  getSido(
    (response) => {
      sido.value = response.data;
    },
    (error) => {
      console.log(error);
    }
  );
});

const sidoChanged = (sido) => {
  selectedSidoCode = sido;
  getGugun(
    sido,
    (response) => {
      gugun.value = response.data;
    },
    (error) => {
      console.log(error);
    }
  );
};

const gugunChanged = (gugun) => {
  selectedGugunCode = gugun;
};

const typeChange = (id) => {
  let type = radioDatas.value.find((item) => item.id === id);
  type.checked = !type.checked;
};

const getList = () => {
  let checkedTypes = [];
  radioDatas.value.forEach((x) => {
    if (x.checked) checkedTypes.push(x.name);
  });
  getSpot(
    selectedSidoCode,
    selectedGugunCode,
    keyword.value,
    checkedTypes,
    (response) => {
      spots.value = response.data;
      console.log(spots.value);
    },
    (error) => {
      console.log(error);
    }
  );
};
</script>

<template>
  <div class="flex flex-row border-none w-full h-full">
    <div id="container" class="w-full h-full flex justify-center items-center">
      <template v-if="isLoaded">
        <KakaoMap
          :width="mapWidth + 'px'"
          :height="mapHeight + 'px'"
          :lat="coordinate.lat"
          :lng="coordinate.lng"
          :draggable="true"
        >
          <KakaoMapMarker :lat="coordinate.lat" :lng="coordinate.lng"></KakaoMapMarker>
        </KakaoMap>
      </template>
    </div>
    <!-- 지도 검색 Nav Bar -->
    <div
      class="w-[25rem] bg-zinc-100 shadow-md rounded-xl flex flex-col items-center justify-center"
    >
      <div class="w-[25rem] h-[4rem] flex justify-center items-center mt-10">
        <!-- 검색창 -->
        <div class="relative flex items-center mt-4 md:mt-0">
          <span class="absolute z-10 cursor-pointer" @click="getList">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke-width="1.5"
              stroke="currentColor"
              class="w-5 h-5 mx-3 text-gray-400 dark:text-gray-600"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M21 21l-5.197-5.197m0 0A7.5 7.5 0 105.196 5.196a7.5 7.5 0 0010.607 10.607z"
              />
            </svg>
          </span>

          <input
            type="text"
            placeholder="Search"
            class="block w-[20rem] py-1.5 pr-5 text-gray-700 bg-white border border-gray-200 rounded-lg placeholder-gray-400/70 pl-11 rtl:pr-11 rtl:pl-5 focus:border-trip-color focus:ring-trip-color focus:outline-none focus:ring focus:ring-opacity-40 transition-all font-kor"
            @keyup.enter="getList"
            v-model="keyword"
          />
        </div>
      </div>
      <div class="w-[20rem] h-[4rem] flex flex-row justify-center items-center mt-2 gap-2">
        <!-- 시도 구군 선택 -->
        <VDropdown title="시/도 선택" :items="sido" @changed="sidoChanged" />
        <VDropdown title="구/군 선택" :items="gugun" @changed="gugunChanged" />
      </div>
      <div
        class="w-[26rem] h-[6rem] flex flex-row flex-wrap gap-4 justify-center items-center mt-4"
      >
        <!-- 라디오 버튼 -->
        <VCheckbox
          v-for="radioData in radioDatas"
          :key="radioData.id"
          :radioData="radioData"
          @typeChanged="typeChange"
        />
      </div>
      <div class="w-[14rem]">
        <div class="relative flex py-5 items-center">
          <div class="flex-grow border-t border-gray-300"></div>
        </div>
      </div>
      <div class="w-[20rem] h-[40rem] flex flex-col justify-center items-center mt-5">
        <div
          v-show="spots.length == 0"
          class="w-[20rem] h-[40rem] flex flex-col items-center justify-start"
        >
          <!-- 검색 결과가 없을 때 -->
          <p class="font-kor text-gray-700 text-xl mt-2">검색 결과가 없어요 :(</p>
          <p class="font-kor text-gray-700 text-sm mt-5">새로운 여행지 등록이 필요한가요?</p>
          <RouterLink class="font-kor text-trip-color text-md">여행지 등록하기</RouterLink>
          <img class="opacity-60 w-[15rem]" src="/src/assets/no-content.png" />
        </div>
        <div
          v-show="spots.length > 0"
          class="prose w-[20rem] h-[40rem] rounded-md shadow-md overflow-y-auto align-middle"
        >
          <!-- 검색 결과 -->
          <SpotSearchItem />
          <SpotSearchItem />
          <SpotSearchItem />
          <SpotSearchItem />
          <SpotSearchItem />
          <SpotSearchItem />
          <SpotSearchItem />
          <SpotSearchItem />
          <SpotSearchItem />
          <SpotSearchItem />
          <SpotSearchItem />
          <SpotSearchItem />
          <SpotSearchItem />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
