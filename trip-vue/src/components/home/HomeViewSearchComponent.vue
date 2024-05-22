<script setup>
defineProps({
  src: String,
  title1: String,
  title2: String,
});
</script>

<template>
  <div
    class="w-[20rem] h-[20rem] flex flex-col items-center justify-center hover:scale-110 transition-all ease-out"
  >
    <div class="w-[15rem] h-[15rem] flex flex-col items-center justify-center">
      <img :src="src" class="rounded-lg shadow-lg shadow-gray-400" />
    </div>
    <div class="w-[15rem] h-[5rem] flex flex-col items-start justify-center text-gray-700">
      <p class="font-eng text-2xl ml-2">{{ title1 }}</p>
      <p class="font-kor text-md ml-2">{{ title2 }}</p>
    </div>
  </div>
</template>

<style scoped></style>
