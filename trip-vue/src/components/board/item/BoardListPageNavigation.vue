<script setup>
const props = defineProps({
  maxPageLength: Number,
});

import { useRoute, useRouter } from "vue-router";
import { ref } from "vue";
import BoardListPageNavigationItem from "./BoardListPageNavigationItem.vue";

const router = useRouter();
const route = useRoute();

const currentPage = ref(parseInt(route.query?.pgno, props.maxPageLength) || 1);
const word = ref(route.query.word);

const move = (pgno) => {
  // 이동할 pgno에 대한 유효성 검사
  if (!isValid(pgno)) {
    return false;
  } else {
    router.push({
      name: "boardList",
      query: {
        pgno,
        word: word.value,
      },
    });
  }
};

const movePrev = () => {
  move(currentPage.value - 1);
};
const moveNext = () => {
  move(currentPage.value + 1);
};

const isValid = (pgno) => {
  return 1 <= pgno && pgno <= props.maxPageLength;
};
</script>

<template>
  <nav>
    <ul class="flex">
      <BoardListPageNavigationItem pageNo="<" @click="movePrev" />
      <BoardListPageNavigationItem
        :pageNo="(currentPage - 2).toString()"
        :hide="!isValid(currentPage - 2)"
        @click="move(currentPage - 2)"
      />
      <BoardListPageNavigationItem
        :pageNo="(currentPage - 1).toString()"
        :hide="!isValid(currentPage - 1)"
        @click="move(currentPage - 1)"
      />
      <BoardListPageNavigationItem :pageNo="currentPage.toString()" selected />
      <BoardListPageNavigationItem
        :pageNo="(currentPage + 1).toString()"
        :hide="!isValid(currentPage + 1)"
        @click="move(currentPage + 1)"
      />
      <BoardListPageNavigationItem
        :pageNo="(currentPage + 2).toString()"
        :hide="!isValid(currentPage + 2)"
        @click="move(currentPage + 2)"
      />
      <BoardListPageNavigationItem pageNo=">" @click="moveNext" />
    </ul>
  </nav>
</template>

<style scoped></style>
