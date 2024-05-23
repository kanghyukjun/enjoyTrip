<script setup>
defineProps({
  article: Object,
});
</script>

<template>
  <div
    class="w-full h-[7rem] flex flex-col items-center justify-center rounded-md border-2 border-gray-300 hover:border-4 transition-all"
  >
    <div class="w-full h-5/6 flex flex-row items-start justify-center font-kor text-gray-700">
      <div class="w-8/12 h-full flex flex-col items-start justify-center">
        <!-- <p class="font-bold text-xl ml-3">제목</p> -->
        <p class="font-bold text-xl ml-3">{{ article?.title }}</p>
        <p class="mt-2 ml-3 text-sm">{{ article.createTime }}</p>
      </div>
      <div class="w-4/12 h-full flex flex-col items-end justify-center">
        <div class="w-[5.5rem] h-[5rem] flex flex-col items-center justify-center rounded-md mr-3">
          <!-- div 대신 이미지를 (만약 넣는다면) -->
          <img
            :src="article.thumbnail ? article.thumbnail : '/src/assets/no-image.png'"
            class="w-[5rem] h-[4.5rem] rounded-md"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
