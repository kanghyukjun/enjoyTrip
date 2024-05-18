<script setup>
// import { RouterLink } from "vue-router";
import { onMounted, ref, watch } from "vue";
import { KakaoMap, KakaoMapMarker } from "vue3-kakao-maps";

import { getSidos, getGuguns, getSpots } from "@/util/search";

import { useSpotListStore } from "@/stores/spot-list";

import VCheckbox from "@/components/common/item/VCheckbox.vue";
import VDropdown from "@/components/common/item/VDropdown.vue";
import SpotSearchItem from "@/components/search/item/SpotSearchItem.vue";
import SpotAddModal from "@/components/search/item/SpotAddModal.vue";
import SpotListBar from "@/components/search/item/SpotListBar.vue";

const store = useSpotListStore();

const mapWidth = ref(0);
const mapHeight = ref(0);
const isLoaded = ref(false);

const sido = ref([]);
const gugun = ref([]);
const keyword = ref("");
const spots = ref([]);
const spotDetail = ref({});

const showSpotInfo = ref(false);
const showModal = ref(false);

let selectedSidoCode = 0;
let selectedGugunCode = 0;

const coordinate = ref({
  lat: 37.566826,
  lng: 126.9786567,
});

const spotlist = ref(null);

onMounted(async () => {
  const container = document.querySelector("#container");
  mapWidth.value = container.offsetWidth * 0.95;
  mapHeight.value = container.offsetHeight * 0.95;
  isLoaded.value = true;

  sido.value = await getSidos();
});

const sidoChanged = async (sido) => {
  selectedSidoCode = sido;
  gugun.value = await getGuguns(sido);
};

const gugunChanged = (gugun) => {
  selectedGugunCode = gugun;
};

const typeChange = (id) => {
  let type = store.radioDatas.find((item) => item.id === id);
  type.checked = !type.checked;
};

const getList = async () => {
  let checkedTypes = [];
  store.radioDatas.forEach((x) => {
    if (x.checked) checkedTypes.push(x.name);
  });
  spots.value = await getSpots(selectedSidoCode, selectedGugunCode, keyword.value, checkedTypes);
};

const showInfo = (spot) => {
  coordinate.value.lat = spot.latitude;
  coordinate.value.lng = spot.longitude;
  showSpotInfo.value = true;
  spotDetail.value = spot;
};

watch(
  () => spots.value,
  (newValue) => {
    if (newValue.length > 0) {
      coordinate.value.lat = newValue[0]?.latitude;
      coordinate.value.lng = newValue[0]?.longitude;
    }
  }
);

const addSpotList = (spot) => {
  spotlist?.value.addSpot(spot);
};
</script>

<template>
  <div class="flex flex-row border-none w-full h-full">
    <div class="absolute w-[18rem] h-full z-10">
      <SpotListBar ref="spotlist" />
    </div>
    <div id="container" class="w-full h-full flex flex-col justify-center items-center">
      <template v-if="isLoaded">
        <KakaoMap
          :width="mapWidth + 'px'"
          :height="mapHeight + 'px'"
          :lat="coordinate.lat"
          :lng="coordinate.lng"
          :draggable="true"
        >
          <!-- 커스텀 오버레이 -->
          <!-- <KakaoMapCustomOverlay :lat="37.566826" :lng="126.9786567">
            <div class="w-[10rem] flex h-[5rem] bg-pink-300">커스텀 오버레이 화면</div>
          </KakaoMapCustomOverlay> -->

          <!-- 마커 -->
          <template v-for="spot in spots" :key="spot.id">
            <KakaoMapMarker :lat="spot.latitude" :lng="spot.longitude" />
          </template>
        </KakaoMap>
      </template>
    </div>
    <!-- 지도 검색 Nav Bar -->
    <div
      class="w-[25rem] h-full bg-zinc-100 shadow-md rounded-xl flex flex-col items-center justify-center"
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
        <VDropdown title="시/도 선택" :items="sido" @changed="sidoChanged" :width="10" />
        <VDropdown title="구/군 선택" :items="gugun" @changed="gugunChanged" :width="10" />
      </div>
      <div
        class="w-[26rem] h-[6rem] flex flex-row flex-wrap gap-4 justify-center items-center mt-4"
      >
        <!-- 라디오 버튼 -->
        <VCheckbox
          v-for="radioData in store.radioDatas"
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
      <div class="w-[20rem] h-[30rem] flex flex-col justify-center items-center mt-5">
        <div
          v-show="spots.length == 0"
          class="w-[20rem] h-[30rem] flex flex-col items-center justify-start"
        >
          <!-- 검색 결과가 없을 때 -->
          <p class="font-kor text-gray-700 text-xl mt-2">검색 결과가 없어요 :(</p>
          <p class="font-kor text-gray-700 text-sm mt-5">새로운 여행지 등록이 필요한가요?</p>
          <p class="font-kor text-trip-color text-md mb-5 cursor-pointer" @click="showModal = true">
            여행지 등록하기
          </p>
          <img class="opacity-60 w-[15rem]" src="/src/assets/no-content.png" />
        </div>
        <div
          v-show="spots.length > 0"
          class="prose w-[20rem] h-[30rem] rounded-md shadow-md overflow-y-auto align-middle"
        >
          <!-- 검색 결과 -->
          <SpotSearchItem
            v-for="spot in spots"
            :key="spot.id"
            :spot="spot"
            @click="showInfo(spot)"
            @addSpot="addSpotList(spot)"
          />
        </div>
      </div>
    </div>
    <!-- 마커 혹은 정보 클릭 시 정보 창 -->
    <!-- <SpotSearchInfo v-show="isSpotInfoShow" :spot="spotDetail" @close="hideInfo" /> -->
    <!-- 장소 추가 시 창 -->
    <SpotAddModal v-if="showModal" @closeModal="showModal = false" />
  </div>
</template>

<style scoped></style>
