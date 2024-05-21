<script setup>
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import BoardButton from "@/components/common/item/VButton.vue";
import { articleStore } from "@/stores/article";
import { getTripCourseDetail } from "@/api/trip";
import BoardDetailTripListItem from "@/components/board/BoardDetailTripListItem.vue";

import {
  KakaoMap,
  KakaoMapMarker,
  KakaoMapMarkerPolyline,
  KakaoMapCustomOverlay,
} from "vue3-kakao-maps";

import VInputForm from "@/components/common/item/VInputForm.vue";
import VButton from "@/components/common/item/VButton.vue";

const store = articleStore();
const route = useRoute();
const router = useRouter();

const mapWidth = ref(0);
const mapHeight = ref(0);
const lat = ref(37.500725285);
const lng = ref(127.036600396);

const isLoaded = ref(false);
// const tripPlan = ref([]);
// const latLngList = ref([]);

const articleNo = route.params.article;
const article = store.getArticle(articleNo);

onMounted(async () => {
  // 카카오 지도 크기 구하기
  const container = document.querySelector("#container");
  mapWidth.value = container.offsetWidth * 0.95;
  mapHeight.value = container.offsetHeight * 0.95;

  // loginId와 courseId를 이용한 axios 비동기 처리로 tripPlan 가져오기
  // latLngList에도 추가해줘서 경로 띄우기
  // getTripCourseDetail(loginId.value, courseId.value)
  //   .then((response) => {
  //     setLatLngList(response.data.spots);

  //     tripPlan.value = response.data.spots;
  //     let latSum = 0.0;
  //     let lngSum = 0.0;
  //     tripPlan.value.forEach((x) => {
  //       latSum += x.latitude;
  //       lngSum += x.longitude;
  //     });
  //     lat.value = latSum / tripPlan.value.length;
  //     lng.value = lngSum / tripPlan.value.length;

  //     isLoaded.value = true;
  //   })
  //   .catch((error) => {
  //     console.log(error);
  //   });
  isLoaded.value = true;
});

// const setLatLngList = (arrays) => {
//   latLngList.value = [];
//   arrays.forEach((x) => {
//     latLngList.value.push({
//       lat: x.latitude,
//       lng: x.longitude,
//     });
//   });
// };

const clickLike = () => {
  isLiked.value = !isLiked.value;
};

const deleteArticle = () => {
  const isDelete = window.confirm("정말 글을 삭제하시겠습니까?");
  if (isDelete) {
    // 삭제
    window.alert("삭제 되었습니다");
    moveList();
  } else {
    return false;
  }
};

const moveUpdate = () => {
  // 이동
  router.push({
    name: "boardUpdate",
    params: {
      article: articleNo, // 수정 필요
    },
  });
};

const moveList = () => {
  router.push({
    name: "boardList",
    query: {
      pgno: 1,
      keyword: "",
    },
  });
};

const isLiked = ref(false);
</script>

<template>
  <div
    class="w-[54rem] h-[35rem] bg-zinc-100 shadow-sm rounded-2xl flex flex-col justify-center items-center font-kor text-gray-800"
  >
    <div class="w-[54rem] h-[5rem] bg-trip-color flex flex-row items-center shadow-lg">
      <p class="font-extrabold text-3xl ml-5 text-gray-100 z-5">{{ article.title }}</p>
    </div>
    <div
      class="w-[54rem] h-[3rem] flex flex-row shadow-lg bg-white items-center justify-between z-3"
    >
      <div class="ml-5 font-bold">
        <p>{{ article.user }}</p>
      </div>
      <div class="mr-5 flex flex-row items-center gap-4">
        <p>조회수 {{ article.hit }}</p>
        <p>좋아요 {{ article.liked }}</p>
      </div>
    </div>
    <div class="w-[54rem] h-[25rem] flex flex-row bg-white items-center justify-center">
      <div class="w-[52rem] h-[23rem] items-center justify-center overflow-y-auto">
        <!-- <template> -->
        <div class="w-[52rem] h-[18rem] flex flex-row items-start justify-start mt-3">
          <div
            class="w-1/4 h-full flex flex-col items-center justify-center rounded-md border-2 border-gray-300"
          >
            <div class="w-full h-full overflow-y-auto">
              <BoardDetailTripListItem class="mt-1" />
            </div>
          </div>
          <div id="container" class="w-2/4 h-full flex flex-row items-center justify-center">
            <KakaoMap
              class="rounded-md"
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
          </div>
          <div class="w-1/4 h-full bg-green-300"></div>
        </div>
        <!-- </template> -->
        <p class="w-[52rem] h-1/2 flex flex-row items-start justify-start mt-3">
          {{ article.content }}
        </p>
      </div>
    </div>
    <div class="w-[54rem] h-[2rem] flex flex-row bg-white items-center justify-end">
      <svg
        xmlns="http://www.w3.org/2000/svg"
        height="30px"
        viewBox="0 -960 960 960"
        width="30px"
        :fill="isLiked ? '#ff8e99' : '#e8eaed'"
        class="mr-2 cursor-pointer"
        @click="clickLike"
      >
        <path
          d="m480-120-58-52q-101-91-167-157T150-447.5Q111-500 95.5-544T80-634q0-94 63-157t157-63q52 0 99 22t81 62q34-40 81-62t99-22q94 0 157 63t63 157q0 46-15.5 90T810-447.5Q771-395 705-329T538-172l-58 52Z"
        />
      </svg>
    </div>
    <div class="w-[54rem] h-[3rem] bg-white flex flex-row items-center justify-end">
      <div class="mr-3 flex flex-row items-center justify-end gap-2">
        <BoardButton color="red" title="삭제" @click="deleteArticle" />
        <BoardButton color="sky" title="수정" @click="moveUpdate" />
        <BoardButton color="gray" title="목록" @click="moveList" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
