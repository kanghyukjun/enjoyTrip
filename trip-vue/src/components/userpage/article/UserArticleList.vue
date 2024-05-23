<script setup>
import { getUserArticle } from "@/api/user";
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/login";
import UserArticleListItem from "@/components/userpage/article/UserArticleListItem.vue";

const router = useRouter();
const store = useUserStore();
const articles = ref([]);

onMounted(() => {
  getUserArticle(store.loginId).then((response) => {
    articles.value = response.data.articles;
  });
});

const goDetail = (boardId) => {
  router.push({
    name: "boardDetail",
    params: {
      article: boardId,
    },
  });
};
</script>

<template>
  <div class="w-full h-full flex flex-col items-center justify-center">
    <div class="w-5/6 h-[3rem] flex flex-row items-center justify-start font-kor">
      <p class="ml-3 text-gray-700 font-bold text-2xl mt-10">내가 작성한 글</p>
    </div>
    <div class="w-5/6 h-full flex flex-col items-center justify-center font-kor transition-all">
      <div
        class="w-11/12 h-5/6 flex flex-col items-center justify-start rounded-md overflow-y-auto"
      >
        <!-- <UserSavedTripListItem
          class="mt-3"
          v-for="course in userCourses"
          :key="course.id"
          :course="course"
          @click="moveDetail(course.id)"
        /> -->
        <UserArticleListItem
          v-for="article in articles"
          :key="article.id"
          :article="article"
          class="mt-3"
          @click="goDetail(article.id)"
        />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
