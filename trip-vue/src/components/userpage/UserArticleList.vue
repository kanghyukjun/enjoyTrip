<script setup>
import UserArticleListItem from "./UserArticleListItem.vue";
import { useUserStore } from '@/stores/user-counter'
import { storeToRefs } from "pinia";
const userStore = useUserStore()
const { userBoardList } = storeToRefs(userStore);
console.log(userBoardList);
</script>

<template>
  <div class="flex flex-col items-center h-full w-full">
    <h1 class="text-3xl font-bold mb-12">유저가 작성하거나 찜한 글 리스트</h1>
    <!-- 글 리스트 시작 -->
    <div class="flex flex-row justify-center flex-wrap gap-5">
      <UserArticleListItem v-for="userBoard in userBoardList" :key="userBoard.boardId" :userBoard="userBoard"/>
    </div>
  </div>
</template>

<style scoped></style>
