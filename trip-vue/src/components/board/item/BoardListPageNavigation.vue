<script setup>
import { useRoute, useRouter } from "vue-router";
// import { getArticleList } from "@/api/board";

import { onMounted, ref } from "vue";
import BoardListPageNavigationItem from "./BoardListPageNavigationItem.vue";

const emit = defineEmits(["loaded"]);

const router = useRouter();
const route = useRoute();

// 한 페이지에 띄울 수 있는 최대 페이지 수
// 총 페이지 갯수는 (articleListLength + maxPageLength - 1)/maxPageLength
const maxPageLength = Number(import.meta.env.VITE_BOARD_MAX_PER_PAGE);

const pgno = ref(0);
const word = ref("");
// const articles = ref([]);
const articleListLength = ref(20);
// const articleListLength = ref(0);

onMounted(() => {
  pgno.value = Number(route.query.pgno);
  word.value = route.query.word;

  if (!isValid(pgno.value)) {
    router.push({
      name: "boardList",
      query: {
        pgno: 1,
        word: "",
      },
    });
  } else {
    // article List 불러오기
    // getArticleList(pgno.value, word.value).then((response) => {
    //   articles.value = response.data.articles;
    //   articleListLength.value = response.data.count;
    // });
    emit("loaded", pgno.value, word.value); // emit 이벤트에 담아 리스트 변경 요청
  }
});

const isValid = (pgno) => {
  return 1 <= pgno && pgno <= (maxPageLength + articleListLength.value - 1) / maxPageLength;
};

const move = (pgno) => {
  if (!isValid(pgno)) {
    router.push({
      name: "boardList",
      query: {
        pgno: 1,
        word: "",
      },
    });
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
  move(pgno.value - 1);
};

const moveNext = () => {
  move(pgno.value + 1);
};
</script>

<template>
  <nav>
    <ul class="flex">
      <BoardListPageNavigationItem pgno="<" @click="movePrev" />
      <BoardListPageNavigationItem
        :pgno="(pgno - 2).toString()"
        :hide="!isValid(pgno - 2)"
        @click="move(pgno - 2)"
      />
      <BoardListPageNavigationItem
        :pgno="(pgno - 1).toString()"
        :hide="!isValid(pgno - 1)"
        @click="move(pgno - 1)"
      />
      <BoardListPageNavigationItem :pgno="pgno.toString()" selected />
      <BoardListPageNavigationItem
        :pgno="(pgno + 1).toString()"
        :hide="!isValid(pgno + 1)"
        @click="move(pgno + 1)"
      />
      <BoardListPageNavigationItem
        :pgno="(pgno + 2).toString()"
        :hide="!isValid(pgno + 2)"
        @click="move(pgno + 2)"
      />
      <BoardListPageNavigationItem pgno=">" @click="moveNext" />
    </ul>
  </nav>
</template>

<style scoped></style>
