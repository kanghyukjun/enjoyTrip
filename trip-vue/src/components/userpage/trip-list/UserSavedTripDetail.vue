<script setup>
import { onMounted, ref, watch } from "vue";
import { useRoute } from "vue-router";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/login";
import { getTripCourseDetail, deleteTripCourse, updateTripCourse } from "@/api/trip";

import {
  KakaoMap,
  KakaoMapMarker,
  KakaoMapMarkerPolyline,
  KakaoMapCustomOverlay,
} from "vue3-kakao-maps";

import draggable from "vuedraggable";

import VDatePicker from "@/components/common/item/VDatePicker.vue";
import VInputForm from "@/components/common/item/VInputForm.vue";
import VButton from "@/components/common/item/VButton.vue";
import UserSavedTripDetailListItem from "@/components/userpage/trip-list/UserSavedTripDetailListItem.vue";

const route = useRoute();
const router = useRouter();
const store = useUserStore();
const courseId = ref(route.params.courseId);
const loginId = ref(store.loginId);

const mapWidth = ref(0);
const mapHeight = ref(0);
const lat = ref(0.0);
const lng = ref(0.0);

const isLoaded = ref(false);
const tripPlan = ref([]);
const latLngList = ref([]);

const registData = ref({
  title: "",
  startDate: "",
  endDate: "",
  desc: "",
  spotIds: [],
});

const setLatLngList = (arrays) => {
  latLngList.value = [];
  arrays.forEach((x) => {
    latLngList.value.push({
      lat: x.latitude,
      lng: x.longitude,
    });
  });
};

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

onMounted(async () => {
  // 카카오 지도 크기 구하기
  const container = document.querySelector("#container");
  mapWidth.value = container.offsetWidth * 0.95;
  mapHeight.value = container.offsetHeight * 0.95;

  // loginId와 courseId를 이용한 axios 비동기 처리로 tripPlan 가져오기
  // latLngList에도 추가해줘서 경로 띄우기
  getTripCourseDetail(loginId.value, courseId.value)
    .then((response) => {
      registData.value.title = response.data.course.title;
      registData.value.desc = response.data.course.desc;
      registData.value.startDate = response.data.course.startDate;
      registData.value.endDate = response.data.course.endDate;

      setLatLngList(response.data.spots);

      tripPlan.value = response.data.spots;
      let latSum = 0.0;
      let lngSum = 0.0;
      tripPlan.value.forEach((x) => {
        latSum += x.latitude;
        lngSum += x.longitude;
      });
      lat.value = latSum / tripPlan.value.length;
      lng.value = lngSum / tripPlan.value.length;

      isLoaded.value = true;
    })
    .catch((error) => {
      console.log(error);
    });
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

const moveList = () => {
  router.push({ name: "userTripList" });
};

const deleteList = () => {
  const confirm = window.confirm(
    "정말 여행지를 삭제하시겠습니까?\n여행지를 삭제할 경우 여행지와 관련된 모든 게시판의 글이 함께 삭제됩니다."
  );
  if (confirm) {
    deleteTripCourse(loginId.value, courseId.value).then(() => {
      window.alert("삭제되었습니다");
      moveList();
    });
  }
};

const regist = () => {
  console.log(registData.value);
  updateTripCourse(loginId.value, courseId.value, registData.value).then(() => {
    window.alert("수정되었습니다");
    router.go(0);
  });
};
</script>

<template>
  <div class="w-full h-full flex flex-col justify-center items-center">
    <div class="w-4/5 h-4/5 flex flex-row items-center justify-center rounded-md shadow-lg">
      <div class="w-1/4 h-5/6 flex flex-col items-center justify-center">
        <div
          class="w-5/6 h-full flex flex-col items-center justify-start overflow-y-auto rounded-md border border-gray-300"
        >
          <draggable
            class="w-full h-full"
            :list="tripPlan"
            tag="TripPlanListItem"
            group="planList"
            @start="drag = true"
            @end="drag = false"
            item-key="id"
            animation="150"
          >
            <template #item="{ element }">
              <UserSavedTripDetailListItem class="mt-1" :spot="element" />
            </template>
          </draggable>
        </div>
      </div>
      <div id="container" class="w-2/4 h-5/6 flex flex-row items-center rounded-md justify-center">
        <!-- 카카오맵에 마커 뿌리기 -->
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
      <div class="w-1/4 h-full flex flex-col items-center justify-center">
        <div
          class="w-11/12 h-5/6 flex flex-col items-center justify-center border border-gray-300 rounded-md gap-1"
        >
          <div
            class="font-kor w-11/12 text-gray-700 mb-10 flex flex-col items-start justify-center"
          >
            <p class="font-bold text-lg ml-2">내 여행 계획</p>
          </div>
          <VInputForm label="제목" :value="registData.title" @input="setTitle" />

          <!-- 날짜 -->
          <div class="w-full h-auto flex flex-row items-center justify-center gap-3 mt-4">
            <VDatePicker label="시작 날짜" :value="registData.startDate" @input="setStartDate" />
            <VDatePicker label="종료 날짜" :value="registData.endDate" @input="setEndDate" />
          </div>
          <textarea
            class="description bg-gray-100 mt-10 sec p-3 w-11/12 h-[10rem] bg-zinc-100 text-gray-700 border border-gray-300 outline-none rounded-md transition-all focus:border-2 focus:border-trip-color focus:outline-0"
            spellcheck="false"
            :value="registData.desc"
            @input="setDesc"
          ></textarea>
        </div>
        <div class="w-11/12 h-[1.5rem] flex flex-row items-center justify-end gap-2 mt-2">
          <VButton color="red" title="삭제" @click="deleteList" />
          <VButton color="gray" title="목록" @click="moveList" />
          <VButton color="sky" title="수정" @click="regist" />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
