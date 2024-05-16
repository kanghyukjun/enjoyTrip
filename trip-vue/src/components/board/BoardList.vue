<script setup>
import { useRouter } from "vue-router";
import { articleStore } from "@/stores/article";
import BoardListItem from "@/components/board/item/BoardListItem.vue";
import BoardListPageNavigation from "@/components/board/item/BoardListPageNavigation.vue";
import BoardButton from "@/components/common/item/VButton.vue";

const router = useRouter();
const store = articleStore();

const post = () => {
  router.push({ name: "board-write" });
};
</script>

<template>
  <div class="w-[54rem] mt-10 mb-5 flex flex-col justify-start">
    <p class="font-bold text-3xl font-kor text-gray-600">게시판</p>
    <p class="font-bold text-lg font-kor text-gray-600">
      여행지에 대한 의견을 자유롭게 나누어보세요
    </p>
  </div>
  <div class="flex flex-row w-[54rem] justify-end mb-3 mr-3">
    <div class="relative flex items-center md:mt-0">
      <span class="absolute">
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
        class="block w-full py-1.5 pr-5 text-gray-700 bg-white border border-gray-200 rounded-lg md:w-80 placeholder-gray-400/70 pl-11 rtl:pr-11 rtl:pl-5 focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40 transition-all"
      />
    </div>
  </div>
  <table class="table-fixed rounded-2xl">
    <thead class="text-sm text-gray-100 uppercase bg-trip-color font-kor">
      <tr class="h-[3rem]">
        <th class="w-[3rem] text-center">no</th>
        <th class="w-[27rem]">제목</th>
        <th class="w-[14rem]">작성자</th>
        <th class="w-[5rem] text-center">조회수</th>
        <th class="w-[5rem] text-center">좋아요</th>
      </tr>
    </thead>
    <BoardListItem v-for="article in store.articles" :key="article.articleno" :article="article" />
  </table>
  <div class="w-[54rem] flex flex-row items-center justify-end mt-3">
    <BoardButton title="글 등록" color="red" @click="post" />
  </div>
  <div class="mt-10">
    <BoardListPageNavigation />
  </div>
</template>

<style scoped></style>
