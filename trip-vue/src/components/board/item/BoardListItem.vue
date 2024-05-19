<script setup>
import { useRouter } from "vue-router";

const router = useRouter();

defineProps({
  article: Object,
});

const moveDetail = (articleno) => {
  router.push({
    name: "boardDetail",
    params: {
      article: articleno,
    },
  });
};
</script>

<template>
  <tbody>
    <tr class="h-[3rem] bg-white border-b hover:bg-gray-50 text-gray-700 cursor-default text-kor">
      <td class="text-center">{{ article.articleno }}</td>
      <td class="cursor-pointer" @click="moveDetail(article.articleno)">
        {{ article.title }}
      </td>
      <td>{{ article.user }}</td>
      <td class="text-center">{{ article.hit }}</td>
      <td class="text-center">{{ article.liked }}</td>
    </tr>
  </tbody>
</template>

<style scoped></style>
