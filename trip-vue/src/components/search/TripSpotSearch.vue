<script setup>
import VCheckbox from "@/components/common/item/VCheckbox.vue";
import SearchDropdown from "@/components/search/item/SearchDropdown.vue";
import SearchedSpotListItem from "@/components/search/item/SearchedSpotListItem.vue";
import SearchedSpotInfo from "@/components/search/item/SearchedSpotInfo.vue";
import TripSpotAddModal from "@/components/search/spot-add-modal/TripSpotAddModal.vue";
import SelectedSpotList from "@/components/search/selected/SelectedSpotList.vue";
import VHorizontalLine from "@/components/common/item/VHorizontalLine.vue";

import { onMounted, ref, watch } from "vue";
import { KakaoMap, KakaoMapMarker, KakaoMapCustomOverlay } from "vue3-kakao-maps";
import { toast } from "vue3-toastify";
import "vue3-toastify/dist/index.css";

import { getSido, getGugun, getSpot } from "@/api/search";
import { useSpotListStore } from "@/stores/spot-list";
import { useUserStore } from "@/stores/login";

const store = useSpotListStore();
const userStore = useUserStore();

const mapWidth = ref(0);
const mapHeight = ref(0);
const isLoaded = ref(false);

const sido = ref([]);
const gugun = ref([]);

onMounted(async () => {
  const container = document.querySelector("#container");
  mapWidth.value = container.offsetWidth * 0.95;
  mapHeight.value = container.offsetHeight * 0.95;
  isLoaded.value = true;

  const sidos = await getSido();
  sido.value = sidos.data;
});

// change event
let selectedSidoCode = 0;

const sidoChanged = async (sido) => {
  selectedSidoCode = sido;
  const guguns = await getGugun(sido);
  gugun.value = guguns.data;
};

let selectedGugunCode = 0;

const gugunChanged = (gugun) => {
  selectedGugunCode = gugun;
};

const typeChanged = (id) => {
  let type = store.radioDatas.find((item) => item.id === id);
  type.checked = !type.checked;
};

// 검색
const keyword = ref("");
const spots = ref([]);

const getList = async () => {
  let checkedTypes = [];
  store.radioDatas.forEach((x) => {
    if (x.checked) checkedTypes.push(x.name);
  });

  if (checkedTypes.length <= 0 || !selectedSidoCode || !selectedGugunCode) {
    toast.error("검색 옵션을 선택해주세요");
  } else {
    const spot = await getSpot(selectedSidoCode, selectedGugunCode, keyword.value, checkedTypes);
    spots.value = spot.data;
  }
};

const coordinate = ref({
  lat: 37.566826,
  lng: 126.9786567,
});

watch(
  () => spots.value,
  (newValue) => {
    if (newValue.length > 0) {
      coordinate.value.lat = newValue[0]?.latitude;
      coordinate.value.lng = newValue[0]?.longitude;
    }
  }
);

const showSpotInfo = ref(false);
const spotDetail = ref({});

const showInfo = (spot) => {
  coordinate.value.lat = spot.latitude;
  coordinate.value.lng = spot.longitude;
  showSpotInfo.value = true;
  spotDetail.value = spot;
};

const spotlist = ref(null);

const addSpotList = (spot) => {
  const result = spotlist?.value.addSpot(spot);
  if (result === -1) {
    toast.error("여행지 추가 갯수는 10개를 넘을 수 없습니다");
  } else if (result === 0) {
    toast.error("이미 추가된 여행지 입니다");
  } else {
    toast.success("여행지를 목록에 추가했습니다");
  }
};

const showModal = ref(false);

const beforeShowModal = () => {
  if (!userStore.isLogined) {
    toast.error("로그인이 필요한 서비스입니다");
  } else {
    showModal.value = true;
  }
};

// custom overlay
const showOverlay = ref(false);
const overlayLat = ref(0.0);
const overlayLng = ref(0.0);
const overlaySpot = ref({});
const showCustomOverlay = (spot) => {
  coordinate.value.lat = spot.latitude + 0.001;
  coordinate.value.lng = spot.longitude;

  // 값 세팅
  overlayLat.value = spot.latitude;
  overlayLng.value = spot.longitude;
  overlaySpot.value = spot;
  showOverlay.value = true;
};

const addSpotFromModal = (spot) => {
  addSpotList(spot);
};
</script>

<template>
  <div class="flex flex-row border-none w-full h-full">
    <div class="absolute w-[18rem] h-full z-10">
      <SelectedSpotList ref="spotlist" />
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
          <KakaoMapCustomOverlay :lat="overlayLat + 0.002" :lng="overlayLng" :yAnchor="1.4">
            <div v-show="showOverlay">
              <SearchedSpotInfo
                :spot="overlaySpot"
                @close="showOverlay = false"
                @addSpot="addSpotFromModal"
              />
            </div>
          </KakaoMapCustomOverlay>

          <!-- 마커 -->
          <template v-for="spot in spots" :key="spot.id">
            <KakaoMapMarker
              :lat="spot.latitude"
              :lng="spot.longitude"
              :clickable="true"
              @onClickKakaoMapMarker="showCustomOverlay(spot)"
            />
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
        <SearchDropdown title="시/도 선택" :items="sido" @changed="sidoChanged" :width="10" />
        <SearchDropdown title="구/군 선택" :items="gugun" @changed="gugunChanged" :width="10" />
      </div>
      <div
        class="w-[26rem] h-[6rem] flex flex-row flex-wrap gap-4 justify-center items-center mt-4"
      >
        <!-- 라디오 버튼 -->
        <VCheckbox
          v-for="radioData in store.radioDatas"
          :key="radioData.id"
          :radioData="radioData"
          @typeChanged="typeChanged"
        />
      </div>
      <div class="w-[14rem]">
        <VHorizontalLine />
      </div>
      <div class="w-[20rem] h-[30rem] flex flex-col justify-center items-center mt-5">
        <div
          v-show="spots.length == 0"
          class="w-[20rem] h-[30rem] flex flex-col items-center justify-start"
        >
          <!-- 검색 결과가 없을 때 -->
          <p class="font-kor text-gray-700 text-xl mt-2">검색 결과가 없어요 :(</p>
          <p class="font-kor text-gray-700 text-sm mt-5">새로운 여행지 등록이 필요한가요?</p>
          <p class="font-kor text-trip-color text-md mb-5 cursor-pointer" @click="beforeShowModal">
            여행지 등록하기
          </p>
          <img class="opacity-60 w-[15rem]" src="/src/assets/no-content.png" />
        </div>
        <div
          v-show="spots.length > 0"
          class="prose w-[20rem] h-[30rem] rounded-md shadow-md overflow-y-auto align-middle"
        >
          <!-- 검색 결과 -->
          <SearchedSpotListItem
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
    <TripSpotAddModal v-if="showModal" @closeModal="showModal = false" />
  </div>
</template>

<style scoped></style>
