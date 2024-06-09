<script setup lang="ts">
import VButton from "@/components/common/item/VButton.vue";
import VSpotListItem from "@/components/common/item/VSpotListItem.vue";

import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import {
  KakaoMap,
  KakaoMapMarker,
  KakaoMapMarkerPolyline,
  KakaoMapCustomOverlay,
} from "vue3-kakao-maps";

import { getArticleDetail } from "@/api/board";
import { deleteUserCourse } from "@/api/user";
import { getOtherTripCourse } from "@/api/trip";
import { useUserStore } from "@/stores/login";

const store = useUserStore();
const route = useRoute();
const router = useRouter();

// modal
const showTripList = ref(true);

// kakao map
const isLoaded = ref(false);
const tripPlan = ref([]);
const latLngList = ref([]);

const article = ref({
  title: "",
  content: "",
  author: "",
  authorLoginId: "",
  createTime: "",
  hit: 0,
  thumbnail: "",
});

const spots = ref([]);
const courseDescription = ref({
  id: 0,
  title: "",
  startDate: "",
  endDate: "",
});

const boardId = route.params.article;
const mapWidth = ref(0);
const mapHeight = ref(0);

onMounted(async () => {
  // 카카오 지도 띄우기
  const container = document.querySelector("#container");
  mapWidth.value = container.offsetWidth * 0.95;
  mapHeight.value = container.offsetHeight * 0.95;

  getArticleDetail(boardId).then((response) => {
    article.value = response.data.article;
    courseDescription.value = response.data.course;
    spots.value = response.data.spots;
    latLngList.value = getLatLngList(response.data.spots);
    setMiddleCoord(spots.value);

    isLoaded.value = true;

    // 카카오 공유
    console.log(article.value.title);
    Kakao.Share.createCustomButton({
      container: "#kakaoButton",
      templateId: 108211,
      templateArgs: {
        title: article.value.title,
        desc: article.value.content,
        THU: "",
        url: `board/detail/${boardId}`,
      },
    });
  });
});

const lat = ref(0.0);
const lng = ref(0.0);

const getLatLngList = (arrays) => {
  const tmp = [];
  arrays.forEach((x) => {
    tmp.push({
      lat: x.latitude,
      lng: x.longitude,
    });
  });
  return tmp;
};

const setMiddleCoord = (arrays) => {
  lat.value = 0.0;
  lng.value = 0.0;
  arrays.forEach((x) => {
    lat.value += x.latitude;
    lng.value += x.longitude;
  });
  lat.value /= arrays.length;
  lng.value /= arrays.length;
};

const selectedSpot = ref({});
const isSelected = ref(false);

const selectSpot = (spot) => {
  isSelected.value = true;
  selectedSpot.value = spot;
};

// button
const deleteArticle = () => {
  const isDelete = window.confirm("정말 글을 삭제하시겠습니까?");
  if (isDelete) {
    // 삭제
    deleteUserCourse(store.loginId, boardId)
      .then(() => {
        window.alert("삭제 되었습니다");
        moveList();
      })
      .catch((error) => console.log(error));
  } else {
    return false;
  }
};

const getTrip = () => {
  // 다른 사람의 여행지를 내 코스로 가져오기
  if (!store.isLogined) {
    window.alert("로그인이 필요합니다");
    router.push({ name: "login" });
  } else {
    getOtherTripCourse(store.loginId, courseDescription.value.id).then(() => {
      const confirm = window.confirm("추가 되었습니다. 추가된 코스를 확인하시겠습니까?");
      if (confirm) {
        router.push({ name: "userTripList" });
      }
    });
  }
};

const moveUpdate = () => {
  // 이동
  router.push({
    name: "boardUpdate",
    params: {
      article: boardId,
    },
  });
};

const moveList = () => {
  router.push({
    name: "boardList",
    query: {
      pgno: 1,
      word: "",
    },
  });
};
</script>

<template>
  <div
    class="w-[54rem] h-[50rem] bg-zinc-100 shadow-sm rounded-2xl flex flex-col justify-center items-center font-kor text-gray-800"
  >
    <div class="w-[54rem] h-[5rem] bg-trip-color flex flex-row items-center shadow-lg">
      <p class="font-extrabold text-3xl ml-5 text-gray-100 z-5">{{ article?.title }}</p>
    </div>
    <div
      class="w-[54rem] h-[3rem] flex flex-row shadow-lg bg-white items-center justify-between z-3"
    >
      <div class="ml-5 font-bold">
        <p>{{ article?.author }}</p>
      </div>
      <div class="mr-5 flex flex-row items-center gap-4">
        <p>조회수 {{ article.hit ? article.hit : 0 }}</p>
      </div>
    </div>
    <div class="w-[54rem] h-[25rem] flex flex-row bg-white items-center justify-center">
      <div class="w-[52rem] h-[23rem] items-center justify-center overflow-y-auto">
        <div class="w-full h-[1.5rem] flex flex-row items-center justify-end mr-3">
          <VButton v-show="showTripList" color="gray" label="접기" @click="showTripList = false" />
          <VButton
            v-show="!showTripList"
            color="gray"
            label="펼치기"
            @click="showTripList = true"
          />
        </div>
        <div
          v-show="showTripList"
          class="w-[52rem] h-[18rem] flex flex-row items-start justify-start mt-3"
        >
          <div
            class="w-1/4 h-full flex flex-col items-center justify-center rounded-md border-2 border-gray-300"
          >
            <div class="w-full h-full overflow-y-auto">
              <VSpotListItem
                v-for="spot in spots"
                :key="spot.id"
                :spot="spot"
                class="mt-1"
                :maxTitleLength="10"
                height="1/3"
                @click="selectSpot(spot)"
              />
            </div>
          </div>
          <div id="container" class="w-2/4 h-full flex flex-row items-center justify-center">
            <template v-if="isLoaded">
              <KakaoMap
                class="rounded-md"
                :width="mapWidth + 'px'"
                :height="mapHeight + 'px'"
                :draggable="true"
                :lat="lat"
                :lng="lng"
                level="7"
              >
                <KakaoMapMarkerPolyline
                  :markerList="latLngList"
                  :endArrow="true"
                  :strokeWeight="6"
                />

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
          <div
            class="w-1/4 h-full flex flex-col items-center justify-center rounded-md border-2 border-gray-300"
          >
            <!-- 정보 띄우는 화면 -->
            <div
              v-show="isSelected"
              class="w-full h-full items-center justify-center font-kor text-gray-700 overflow-y-auto"
            >
              <div class="w-full h-auto flex flex-col items-start justify-center">
                <p class="font-bold ml-2">장소</p>
                <p class="ml-2">{{ selectedSpot.title }}</p>
              </div>
              <div class="w-full h-auto flex flex-col items-center justify-center mt-3">
                <img
                  :src="selectedSpot.img ? selectedSpot.img : '/src/assets/no-image.png'"
                  class="rounded-md w-11/12"
                />
              </div>
              <div class="w-full h-auto flex flex-col items-start justify-center mt-3">
                <p class="font-bold ml-2">위치</p>
                <p class="ml-2">{{ selectedSpot.addr }}</p>
              </div>
              <div class="w-full h-auto flex flex-col items-start justify-center mt-3">
                <p class="font-bold ml-2">타입</p>
                <p class="ml-2">{{ selectedSpot.type }}</p>
              </div>
            </div>
            <div
              v-show="!isSelected"
              class="w-full h-full flex flex-col items-center justify-center font-kor text-zinc-200"
            >
              <p
                class="w-full h-full flex flex-col items-center justify-center scale-75 hover:scale-100 transition-all"
              >
                보고 싶은 장소를 선택해주세요
              </p>
            </div>
          </div>
        </div>
        <div class="relative flex items-center w-full mt-5">
          <div class="flex-grow border-t border-gray-300"></div>
        </div>
        <div class="w-[52rem] flex flex-row items-center justify-center mt-10">
          <img :src="article?.thumbnail" class="w-[24rem] mt-3 rounded-md" />
        </div>
        <p class="w-[52rem] h-1/2 flex flex-row items-start justify-start mt-3 whitespace-pre">
          {{ article?.content }}
        </p>
      </div>
    </div>
    <div class="w-[54rem] h-[3rem] bg-white flex flex-row items-center justify-end">
      <div class="mr-3 flex flex-row items-center justify-end gap-2">
        <VButton id="kakaoButton" color="yellow" label="공유" />
        <template v-if="article.authorLoginId === store.loginId">
          <VButton color="red" label="삭제" @click="deleteArticle" />
          <VButton color="sky" label="수정" @click="moveUpdate" />
        </template>
        <template v-else>
          <VButton color="sky" label="가져오기" @click="getTrip" />
        </template>
        <VButton color="gray" label="목록" @click="moveList" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
