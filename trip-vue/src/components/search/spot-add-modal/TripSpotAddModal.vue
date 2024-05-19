<script setup>
import "//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js";

import { getSido, getGugun } from "@/api/search";
import { ref, computed } from "vue";

import { useSpotListStore } from "@/stores/spot-list";

import TripSpotAddModalFormItem from "@/components/search/spot-add-modal/TripSpotAddModalFormItem.vue";
import VDropdown from "@/components/common/item/VDropdown.vue";
import VButton from "@/components/common/item/VButton.vue";
import TripSpotAddModalFileForm from "@/components/search/spot-add-modal/TripSpotAddModalFileForm.vue";

const emit = defineEmits(["closeModal"]);

const store = useSpotListStore();
const geoCoder = new kakao.maps.services.Geocoder();

const spotInfo = ref({
  title: "",
  address: "",
  zipcode: "",
  image: "",
  type: 0,
  sidoId: 0,
  gugunId: 0,
  latitude: 0.0,
  longitude: 0.0,
});

const isValid = computed(() => {
  return (
    spotInfo.value.title &&
    spotInfo.value.address &&
    spotInfo.value.zipcode &&
    spotInfo.value.type &&
    spotInfo.value.sidoId &&
    spotInfo.value.gugunId &&
    spotInfo.value.latitude &&
    spotInfo.value.longitude
  );
});

const typeChanged = (value) => {
  spotInfo.value.type = value;
};

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
        spotInfo.value.latitude = coords.latitude;
        spotInfo.value.longitude = coords.longitude;

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
        const coords = { latitude: result[0].x, longitude: result[0].y };
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

const encodeImageToBase64 = (file) => {
  return new Promise((resolve, reject) => {
    let reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = (event) => {
      resolve(event.target.result);
    };
    reader.onerror = (error) => {
      reject(error);
    };
  });
};

const fileUpload = (file) => {
  encodeImageToBase64(file).then((response) => (spotInfo.value.image = response));
};

const close = () => {
  emit("closeModal");
};

const register = () => {
  if (isValid.value) {
    // json에 데이터를 담아 axios 비동기 처리를 이용해 서버에 여행지 post 요청을 하면 된다
    console.log(spotInfo.value);
    window.alert("등록이 완료되었습니다");
    close();
  } else {
    window.alert("작성하지 않은 값이 존재합니다");
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
        <p class="ml-2 font-bold text-2xl">신규 여행지 등록</p>
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
        <div class="flex-grow border-t border-gray-300"></div>
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
                <input
                  class="peer h-full w-full rounded-md border border-blue-gray-200 bg-transparent px-3 py-3 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all placeholder-shown:border placeholder-shown:border-blue-gray-200 placeholder-shown:border-t-blue-gray-200 focus:border-2 focus:border-trip-color focus:border-t-transparent focus:outline-0 disabled:border-0 disabled:bg-blue-gray-50"
                  placeHolder=" "
                  v-model="spotInfo.title"
                />
                <label
                  class="before:content[' '] after:content[' '] pointer-events-none absolute left-0 -top-1.5 flex h-full w-full select-none text-[11px] font-normal leading-tight text-blue-gray-400 transition-all before:pointer-events-none before:mt-[6.5px] before:mr-1 before:box-border before:block before:h-1.5 before:w-2.5 before:rounded-tl-md before:border-t before:border-l before:border-blue-gray-200 before:transition-all after:pointer-events-none after:mt-[6.5px] after:ml-1 after:box-border after:block after:h-1.5 after:w-2.5 after:flex-grow after:rounded-tr-md after:border-t after:border-r after:border-blue-gray-200 after:transition-all peer-placeholder-shown:text-sm peer-placeholder-shown:leading-[4.1] peer-placeholder-shown:text-blue-gray-500 peer-placeholder-shown:before:border-transparent peer-placeholder-shown:after:border-transparent peer-focus:text-[11px] peer-focus:leading-tight peer-focus:text-trip-color peer-focus:before:border-t-2 peer-focus:before:border-l-2 peer-focus:before:!border-trip-color peer-focus:after:border-t-2 peer-focus:after:border-r-2 peer-focus:after:!border-trip-color peer-disabled:text-transparent peer-disabled:before:border-transparent peer-disabled:after:border-transparent peer-disabled:peer-placeholder-shown:text-blue-gray-500"
                >
                  장소 이름
                </label>
              </div>
              <VDropdown
                class="mb-5"
                title="관광지 타입"
                :items="store.radioDatas"
                @changed="typeChanged"
                :width="15"
              />
            </div>
            <div class="w-[15rem] h-[16rem] flex flex-col items-start justify-center">
              <p class="w-[14rem] h-[1.5rem]">위치 정보</p>
              <TripSpotAddModalFormItem
                class="mb-3 mt-3"
                label="상세 주소"
                :value="spotInfo?.address"
                readonly
              />
              <TripSpotAddModalFormItem label="우편 번호" :value="spotInfo?.zipcode" readonly />
              <button
                class="mt-6 block w-full select-none rounded-lg bg-trip-color py-3 px-6 text-center align-middle font-sans text-xs font-bold uppercase text-white shadow-md shadow-trip-color transition-all hover:shadow-lg hover:shadow-trip-color focus:opacity-[0.85] focus:shadow-none active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                type="button"
                data-ripple-light="true"
                @click="execDaumPostcode"
              >
                우편번호 찾기
              </button>
            </div>
            <div class="w-[15rem] h-[3rem] flex flex-row items-end justify-end gap-2">
              <VButton title="취소" color="gray" @click="close" />
              <VButton title="등록" color="red" @click="register" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
