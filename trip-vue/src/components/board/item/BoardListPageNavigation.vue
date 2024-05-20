<script setup>
import { useRoute, useRouter, onBeforeRouteUpdate } from "vue-router";
import { onMounted, ref } from "vue";
import BoardListPageNavigationItem from "./BoardListPageNavigationItem.vue";

const maxPageLength = 10; // 임시로 지정한 최대 페이지 길이, Floor(boardList 길이 / pageSize)
const router = useRouter();
const route = useRoute();

const currentPage = ref(0);

const setList = () => {
  // page navigation bar에 대한 로직을 처리한다
  // page가 떴을 때 list를 불러온다
  currentPage.value = parseInt(route.query?.pgno, 10) || 1;
};

onMounted(() => {
  setList();
});

onBeforeRouteUpdate(() => {
  setList();
});

const move = (pgno) => {
  // 이동할 pgno에 대한 유효성 검사
  console.log(pgno);
  if (!isValid(pgno)) {
    return false;
  } else {
    router.push({
      name: "boardList",
      query: {
        pgno,
        keyword: "",
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
  return 1 <= pgno && pgno <= maxPageLength;
};

console.log();
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
