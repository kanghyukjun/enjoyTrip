<script setup>
import VButton from "@/components/common/item/VButton.vue";
import VHorizontalLine from "@/components/common/item/VHorizontalLine.vue";

const props = defineProps({
  spot: Object,
});

const emit = defineEmits(["close", "addSpot"]);

// button
const close = () => {
  emit("close");
};

const addSpot = () => {
  emit("addSpot", props.spot);
};
</script>

<template>
  <div
    class="absolute w-[40rem] h-[24rem] bg-zinc-50 rounded-md shadow-2xl top-1/2 opacity-50 left-1/2 transform -translate-x-1/2 -translate-y-1/2 z-30 transition-all hover:opacity-100"
  >
    <div class="w-[40rem] h-[24rem] flex flex-col items-center justify-start">
      <div class="w-[40rem] h-[4rem] flex flex-row justify-between items-center">
        <p class="font-kor ml-3 text-2xl font-extrabold text-zinc-700">{{ spot?.title }}</p>
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
      <div class="w-[37rem] h-[17rem]">
        <!-- <div class="relative flex items-center">
          <div class="flex-grow border-t border-zinc-300"></div>
        </div> -->
        <VHorizontalLine class="py-0 px-0" />
        <div class="w-[37rem] h-[16rem] flex flex-row items-center justify-between mt-2">
          <!-- 사진 -->
          <img
            class="w-[17rem] h-[16rem] rounded-md"
            :src="spot?.img ? spot.img : '/src/assets/no-image.png'"
          />
          <div class="w-[17rem] h-[16rem] flex flex-row justify-center items-center">
            <!-- 설명 -->
            <div class="w-[17rem] h-[14rem] flex flex-col flex-wrap justify-start items-start m-2">
              <p class="font-kor text-2xl font-extrabold text-zinc-700">주소</p>
              <p class="font-kor text-md font-bold text-zinc-700">
                {{ spot?.addr }}
              </p>
              <p class="font-kor text-xl font-extrabold text-zinc-700 mt-3">관광지 타입</p>
              <p class="font-kor text-md font-bold text-zinc-700">
                {{ spot?.type }}
              </p>
            </div>
          </div>
        </div>
      </div>
      <div class="w-[37rem] h-[2rem] flex flex-row items-center justify-end">
        <VButton label="여행지에 추가" color="sky" @click="addSpot" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
