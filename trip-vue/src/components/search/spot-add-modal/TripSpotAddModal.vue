<script setup>
import "//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js";

import { getSido, getGugun, addSpot } from "@/api/search";
import { ref, computed } from "vue";

import { useSpotListStore } from "@/stores/spot-list";
import { useUserStore } from "@/stores/login";

import VDropdown from "@/components/common/item/VDropdown.vue";
import VButton from "@/components/common/item/VButton.vue";
import VButtonLarge from "@/components/common/item/VButtonLarge.vue";
import TripSpotAddModalFileForm from "@/components/search/spot-add-modal/TripSpotAddModalFileForm.vue";
import VInputForm from "@/components/common/item/VInputForm.vue";
import VHorizontalLine from "@/components/common/item/VHorizontalLine.vue";

import { encodeImageToBase64 } from "@/util/image";

const emit = defineEmits(["closeModal"]);
const store = useSpotListStore();
const userStore = useUserStore();

// 여행지 등록 정보
const spotInfo = ref({
  title: "",
  address: "",
  zipcode: "",
  image: "",
  typeId: 0,
  sidoId: 0,
  gugunId: 0,
  latitude: 0.0,
  longitude: 0.0,
});

const fileUpload = async (file) => {
  encodeImageToBase64(file).then((response) => {
    spotInfo.value.image = response;
  });
};

const setTitle = (value) => {
  spotInfo.value.title = value;
};

const typeChanged = (value) => {
  spotInfo.value.typeId = parseInt(value);
};

// daum postcode
const geoCoder = new kakao.maps.services.Geocoder();

const execDaumPostcode = () => {
  daum.postcode.load(() => {
    new daum.Postcode({
      submitMode: false,
      oncomplete: async (data) => {
        // 주소 찾기
        spotInfo.value.address = data.roadAddress || data.jibunAddress;
        spotInfo.value.zipcode = data.zonecode;

        // 좌표계 찾기
        const coords = await getAddressCoords(spotInfo.value.address);
        spotInfo.value.latitude = parseFloat(coords.latitude);
        spotInfo.value.longitude = parseFloat(coords.longitude);

        // 시도, 구군 코드 찾기
        const sidoId = await getSidoId(spotInfo.value.address);
        const gugunId = await getGugunId(spotInfo.value.address, sidoId);
        spotInfo.value.sidoId = sidoId;
        spotInfo.value.gugunId = gugunId;
      },
    }).open();
  });
};

const getAddressCoords = (address) => {
  return new Promise((resolve, reject) => {
    geoCoder.addressSearch(address, (result, status) => {
      if (status === kakao.maps.services.Status.OK) {
        const coords = { longitude: result[0].x, latitude: result[0].y };
        resolve(coords);
      } else {
        reject(status);
      }
    });
  });
};

const getSidoId = async (address) => {
  const sidoName = address.split(" ", 1)[0];
  const sidos = await getSido();
  const result = sidos.data;

  let sidoCode = -1;
  result.forEach((x) => {
    if (x.name.includes(sidoName)) sidoCode = x.id;
  });
  return sidoCode;
};

const getGugunId = async (address, sidoCode) => {
  const gugunName = address.split(" ", 2)[1];
  const guguns = await getGugun(sidoCode);
  const result = guguns.data;

  let gugunCode = -1;
  result.forEach((x) => {
    if (x.name.includes(gugunName)) gugunCode = x.id;
  });
  return gugunCode;
};

// Validation
const isValid = computed(() => {
  return (
    spotInfo.value.title &&
    spotInfo.value.address &&
    spotInfo.value.zipcode &&
    spotInfo.value.typeId &&
    spotInfo.value.sidoId &&
    spotInfo.value.gugunId &&
    spotInfo.value.latitude &&
    spotInfo.value.longitude
  );
});

// 버튼
const close = () => {
  emit("closeModal");
};

const register = async () => {
  if (isValid.value) {
    addSpot(userStore.loginId, spotInfo.value)
      .then(() => {
        window.alert("등록이 완료되었습니다");
      })
      .catch(() => {
        window.alert("서버 에러");
      })
      .finally(() => {
        close();
      });
  }
};
</script>

<template>
  <div
    class="absolute flex flex-row items-center justify-center w-screen h-screen top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 z-20 transition-all"
  >
    <div
      class="absolute flex flex-row items-center justify-center w-screen h-screen bg-black top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 z-20 opacity-50"
      @click="close"
    ></div>
    <div
      class="w-[40rem] h-[35rem] flex flex-col items-center justify-center rounded-lg opacity-100 bg-zinc-100 z-30 font-kor"
    >
      <div class="w-[40rem] h-[3rem] flex items-center justify-between">
        <p class="ml-2 font-bold text-2xl text-gray-700">신규 여행지 등록</p>
        <button
          class="middle none center mr-4 h-8 max-h-[32px] w-8 max-w-[32px] rounded-lg bg-zinc-500 font-sans text-xs font-bold uppercase text-white shadow-sm shadow-zinc-500/20 transition-all hover:shadow-md hover:shadow-zinc-500/40 focus:opacity-[0.85] focus:shadow-none active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
          @click.prevent="close"
        >
          <svg
            class="ml-1"
            xmlns="http://www.w3.org/2000/svg"
            height="24px"
            viewBox="0 -960 960 960"
            width="24px"
            fill="#e8eaed"
          >
            <path
              d="m256-200-56-56 224-224-224-224 56-56 224 224 224-224 56 56-224 224 224 224-56 56-224-224-224 224Z"
            />
          </svg>
        </button>
      </div>
      <div class="relative flex items-center w-[36rem]">
        <VHorizontalLine />
      </div>
      <div class="w-[40rem] h-[32rem] flex flex-col items-center justify-center">
        <div class="w-[38rem] h-[30rem] flex flex-row items-center justify-center gap-10">
          <div class="w-[17rem] h-[30rem] flex flex-col items-center justify-center">
            <!-- 이미지 받기 -->
            <TripSpotAddModalFileForm @fileUploaded="fileUpload" />
          </div>
          <div class="w-[15rem] h-[30rem] flex flex-col items-center justify-center">
            <div class="w-[15rem] h-[9rem] flex flex-col items-center justify-center">
              <p class="w-[14rem] h-[1.5rem]">여행지 정보</p>
              <div class="relative h-11 w-full mt-2 mb-3 min-w-[200px]">
                <VInputForm label="장소 이름" @input="setTitle" />
              </div>
              <VDropdown
                class="mb-5"
                title="관광지 타입"
                :items="store.radioDatas"
                @changed="typeChanged"
                :width="15"
              />
            </div>
            <div class="w-[15rem] h-[16rem] flex flex-col items-start justify-center gap-4">
              <p class="w-[14rem] h-[1.5rem]">위치 정보</p>
              <VInputForm label="상세 주소" :value="spotInfo?.address" readonly />
              <VInputForm label="우편 번호" :value="spotInfo?.zipcode" readonly />
              <VButtonLarge label="우편번호 찾기" color="trip" @click="execDaumPostcode" />
            </div>
            <div class="w-[15rem] h-[3rem] flex flex-row items-end justify-end gap-2">
              <VButton label="취소" color="gray" @click="close" />
              <VButton label="등록" color="red" @click="register" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
