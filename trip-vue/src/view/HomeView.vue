<script setup>
import HomeViewSearchComponent from "@/components/home/HomeViewSearchComponent.vue";
import HomeViewArticleComponent from "@/components/home/HomeViewArticleComponent.vue";
import { getHit } from "@/api/home";
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";

const hitArticles = ref([]);
const router = useRouter();

onMounted(() => {
  getHit().then((response) => {
    hitArticles.value = response.data.articles;
  });
});

const moveSearch = (sidoCode) => {
  router.push({
    name: "search",
    query: {
      sido: sidoCode,
    },
  });
};

const moveArticle = (articleId) => {
  router.push({
    name: "boardDetail",
    params: {
      article: articleId,
    },
  });
};
</script>

<template>
  <div class="w-full h-full bg-zinc-100 font-kor overflow-y-auto">
    <div class="w-full h-[80rem] flex flex-col items-center justify-start">
      <!-- 메인창 -->
      <div class="w-1/2 h-auto flex flex-col items-center justify-center mt-20">
        <p class="font-extrabold text-4xl text-gray-700">어디로 여행을 떠나시나요?</p>
      </div>
      <!-- 검색 바로가기 -->
      <div class="w-full h-[20rem] flex flex-row items-center justify-center mt-20">
        <HomeViewSearchComponent
          src="/src/assets/main/jeju.jpg"
          title1="JEJU"
          title2="대한민국 제주"
          @click="moveSearch(39)"
        />
        <HomeViewSearchComponent
          src="/src/assets/main/busan.jpeg"
          title1="BUSAN"
          title2="대한민국 부산"
          @click="moveSearch(6)"
        />
        <HomeViewSearchComponent
          src="/src/assets/main/seoul.jpg"
          title1="SEOUL"
          title2="대한민국 서울"
          @click="moveSearch(1)"
        />
        <HomeViewSearchComponent
          src="/src/assets/main/gyeongju.jpg"
          title1="GYEONGJU"
          title2="대한민국 경주"
          @click="moveSearch(35)"
        />
      </div>
      <!-- 인기 게시물 -->
      <div class="w-full h-[30rem] flex flex-col items-center justify-start mt-20">
        <p class="font-bold text-3xl text-gray-700">현재 인기있는 게시물</p>
        <div class="w-full h-[25rem] flex flex-row items-center justify-center mt-10">
          <!-- 3개 조회수 순으로 받아와서 for문 돌려서 click 이벤트 먹여서 하시던가 하세요 -->
          <HomeViewArticleComponent
            v-for="article in hitArticles"
            :key="article.id"
            :article="article"
            @click="moveArticle(article.id)"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
