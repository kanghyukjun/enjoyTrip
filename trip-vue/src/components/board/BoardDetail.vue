<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import BoardButton from "./BoardButton.vue";

// const route = useRoute();
const router = useRouter();
// const articleNo = route.params.article;

const object = ref({
  title: "안녕하세요 회원님들",
  user: "강태경",
  liked: 6,
  hit: 624,
  subject:
    "안녕하세요 이번에 제가 여행을 가려고 하는데 어디로 가야할지 모르겠습니다 혹시 좋은 곳 아시는 곳 있으면 추천좀 해주세요들레히요레히요",
});

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
    name: "board-update",
    params: {
      article: 1, // 수정 필요
    },
  });
};

const moveList = () => {
  router.push({
    name: "board-list",
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
    class="w-fit h-fit bg-zinc-100 shadow-sm rounded-2xl flex flex-col justify-center items-center font-kor text-gray-800"
  >
    <div class="w-[54rem] h-[5rem] bg-trip-color flex flex-row items-center shadow-lg">
      <p class="font-extrabold text-3xl ml-5 text-gray-100 z-5">{{ object.title }}</p>
    </div>
    <div
      class="w-[54rem] h-[3rem] flex flex-row shadow-lg bg-white items-center justify-between z-3"
    >
      <div class="ml-5 font-bold">{{ object.user }}</div>
      <div class="mr-5 flex flex-row items-center gap-4">
        <p>조회수 {{ object.hit }}</p>
        <p>좋아요 {{ object.liked }}</p>
      </div>
    </div>
    <div class="w-[54rem] h-[30rem] flex flex-row bg-white items-center justify-center">
      <div class="w-[52rem] h-[28rem] flex flex-row items-start justify-start">
        {{ object.subject }}
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