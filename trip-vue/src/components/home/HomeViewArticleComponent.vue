<script setup>
defineProps({
  article: Object,
});
</script>

<template>
  <div
    class="w-[20rem] h-[25rem] flex flex-col items-center justify-center hover:scale-110 transition-all ease-out"
  >
    <div
      class="w-[16rem] h-[23rem] flex flex-col items-center rounded-md justify-start shadow-lg shadow-gray-300"
    >
      <img
        :src="article?.img ? article.img : '/src/assets/no-image.png'"
        class="w-[15rem] h-[14rem] rounded-md"
      />
      <div class="w-[15rem] h-[9rem] flex flex-col items-center justify-center font-kor">
        <div class="w-[15rem] h-[7rem] flex flex-col items-start justify-center font-kor">
          <p class="text-gray-700 text-xl">글 제목 랄랄라</p>
        </div>
        <div class="w-[15rem] h-[2rem] flex flex-row items-end justify-between font-kor mb-3">
          <p class="text-gray-700 text-sm">작성자 이름 랄랄라</p>
          <p class="text-gray-700 text-sm">조회수 랄랄라</p>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
