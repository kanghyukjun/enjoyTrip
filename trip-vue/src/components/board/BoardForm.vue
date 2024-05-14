<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import BoardButton from "./BoardButton.vue";

const route = useRoute();
const router = useRouter();
const articleNo = route.params?.article;

onMounted(() => {
  if (articleNo) {
    // 값을 세팅
  }
});

const object = ref({
  title: "",
  user: "강태경",
  subject:
    "안녕하세요 이번에 제가 여행을 가려고 하는데 어디로 가야할지 모르겠습니다 혹시 좋은 곳 아시는 곳 있으면 추천좀 해주세요들레히요레히요",
});

const cancel = () => {
  router.push({ name: "board-list" });
};
</script>

<template>
  <div
    class="w-fit h-fit bg-zinc-100 shadow-sm rounded-2xl flex flex-col justify-center items-center font-kor text-gray-800"
  >
    <div class="w-[54rem] h-[5rem] bg-trip-color flex flex-row items-center shadow-lg">
      <p class="font-extrabold text-3xl ml-5 text-gray-100 z-5">
        <!-- subject -->
      </p>
    </div>
    <div class="w-[54rem] h-[3rem] flex flex-row bg-white items-center justify-between z-3"></div>
    <div class="w-[54rem] h-[30rem] flex flex-row bg-white items-center justify-center">
      <div class="w-[52rem] h-[28rem] flex flex-row items-start justify-start">
        <!-- content -->
      </div>
    </div>
    <div class="w-[54rem] h-[2rem] flex flex-row bg-white items-center justify-end"></div>
    <div class="w-[54rem] h-[3rem] bg-white flex flex-row items-center justify-end">
      <div class="mr-3 flex flex-row items-center justify-end gap-2">
        <BoardButton color="red" title="취소" @click="cancel" />
        <BoardButton color="sky" title="등록" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
