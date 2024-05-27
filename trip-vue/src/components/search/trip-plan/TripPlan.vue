<script setup>
import { ref, onMounted, watch } from "vue";
import { useRouter } from "vue-router";
import {
  KakaoMap,
  KakaoMapMarker,
  KakaoMapMarkerPolyline,
  KakaoMapCustomOverlay,
} from "vue3-kakao-maps";
import draggable from "vuedraggable";

import { addTripPlan } from "@/api/trip";

import { toast } from "vue3-toastify";
import "vue3-toastify/dist/index.css";

import { useUserStore } from "@/stores/login";

import VSpotListItem from "@/components/common/item/VSpotListItem.vue";
import VInputForm from "@/components/common/item/VInputForm.vue";
import VDatePicker from "@/components/common/item/VDatePicker.vue";
import VButton from "@/components/common/item/VButton.vue";
import { HttpStatusCode } from "axios";

const router = useRouter();
const store = useUserStore();

const mapWidth = ref(0);
const mapHeight = ref(0);
const lat = ref(0.0);
const lng = ref(0.0);

const isLoaded = ref(false);
const tripPlan = ref([]);
const latLngList = ref([]);

const setLatLngList = (arrays) => {
  latLngList.value = [];
  arrays.forEach((x) => {
    latLngList.value.push({
      lat: x.latitude,
      lng: x.longitude,
    });
  });
};

onMounted(() => {
  tripPlan.value = JSON.parse(sessionStorage.getItem("tripPlan"));
  const container = document.querySelector("#container");
  mapWidth.value = container.offsetWidth * 0.95;
  mapHeight.value = container.offsetHeight * 0.95;
  let latSum = 0.0;
  let lngSum = 0.0;
  tripPlan.value.forEach((x) => {
    latSum += x.latitude;
    lngSum += x.longitude;
  });
  lat.value = latSum / tripPlan.value.length;
  lng.value = lngSum / tripPlan.value.length;

  loginId.value = store.loginId;

  isLoaded.value = true;
});

watch(
  () => tripPlan.value,
  (newValue) => {
    setLatLngList(newValue);
    setSpotIds(newValue);
  },
  {
    deep: true,
  }
);

const loginId = ref("");

const registData = ref({
  title: "",
  startDate: "",
  endDate: "",
  desc: "",
  spotIds: [],
});

const setTitle = (value) => {
  registData.value.title = value;
};
const setStartDate = (value) => {
  registData.value.startDate = value;
};
const setEndDate = (value) => {
  registData.value.endDate = value;
};
const setDesc = (event) => {
  registData.value.desc = event.target.value;
};
const setSpotIds = (arrays) => {
  registData.value.spotIds = [];
  arrays.forEach((x) => registData.value.spotIds.push(x.id));
};

const cancel = () => {
  router.go(-1);
};

const regist = async () => {
  if (
    registData.value.spotIds.length <= 0 ||
    !registData.value.title ||
    !registData.value.desc ||
    !registData.value.startDate ||
    !registData.value.endDate
  ) {
    toast.error("정보를 모두 입력해주세요");
  } else if (registData.value.startDate > registData.value.endDate) {
    toast.error("올바른 날짜 정보를 입력해주세요");
  } else {
    const response = await addTripPlan(store.loginId, registData.value);
    if (response.status == HttpStatusCode.Created) {
      window.alert("여행지 등록 완료!");
      router.push({ name: "mapSearch" });
    } else {
      toast.error("에러 발생");
    }
  }
};
</script>

<template>
  <div class="w-full h-full flex flex-col justify-center items-center wrap">
    <div class="w-4/5 h-4/5 flex flex-row items-center justify-center rounded-md shadow-lg">
      <div class="w-1/4 h-5/6 flex flex-col items-center justify-center">
        <div
          class="w-5/6 h-full flex flex-col items-center justify-start overflow-y-auto rounded-md border border-gray-300"
        >
          <draggable
            class="w-full h-full"
            :list="tripPlan"
            tag="VSpotListItem"
            group="planList"
            @start="drag = true"
            @end="drag = false"
            item-key="id"
            animation="150"
          >
            <template #item="{ element }">
              <VSpotListItem height="1/6" :maxTitleLength="27" class="mt-3" :spot="element" />
            </template>
          </draggable>
        </div>
      </div>
      <div id="container" class="w-2/4 h-5/6 flex flex-row items-center rounded-md justify-center">
        <template v-if="isLoaded">
          <KakaoMap
            :width="mapWidth + 'px'"
            :height="mapHeight + 'px'"
            :draggable="true"
            :lat="lat"
            :lng="lng"
            level="7"
          >
            <KakaoMapMarkerPolyline :markerList="latLngList" :endArrow="true" :strokeWeight="6" />

            <template v-for="spot in tripPlan" :key="spot.id">
              <KakaoMapMarker :lat="spot.latitude" :lng="spot.longitude" />
              <KakaoMapCustomOverlay :lat="spot.latitude + 0.008" :lng="spot.longitude">
                <div
                  class="w-auto h-[1.5rem] flex flex-row items-center justify-center bg-zinc-100 rounded-md border-2"
                >
                  <p class="text-gray-700">{{ spot.title }}</p>
                </div>
              </KakaoMapCustomOverlay>
            </template>
          </KakaoMap>
        </template>
      </div>
      <div class="w-1/4 h-5/6 flex flex-row items-center justify-center">
        <div
          class="w-11/12 h-full flex flex-col items-center justify-center border border-gray-300 rounded-md gap-4"
        >
          <div class="font-kor w-11/12 h-[1rem] text-gray-700 mb-10">
            <p class="font-bold text-xl ml-2">여행 계획 등록</p>
          </div>
          <VInputForm label="아이디" :value="loginId" readonly />
          <VInputForm label="여행 계획 제목" @input="setTitle" />

          <!-- 날짜 -->
          <div class="w-5/6 h-auto flex flex-row items-center justify-center gap-3">
            <VDatePicker label="시작 날짜" @input="setStartDate" />
            <VDatePicker label="종료 날짜" @input="setEndDate" />
          </div>
          <textarea
            class="description bg-gray-100 mt-10 sec p-3 w-11/12 h-[10rem] bg-zinc-100 text-gray-700 border border-gray-300 outline-none rounded-md transition-all focus:border-2 focus:border-trip-color focus:outline-0"
            spellcheck="false"
            placeholder="본문"
            @input="setDesc"
          ></textarea>
          <div class="w-11/12 flex flex-row items-center justify-end gap-3 mb-3">
            <VButton color="gray" label="취소" @click="cancel" />
            <VButton color="sky" label="등록" @click="regist" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
