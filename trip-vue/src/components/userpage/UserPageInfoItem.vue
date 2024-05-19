<script setup>
defineProps({
  title: String,
  content: String,
});
</script>

<template>
  <div
    class="w-[30rem] h-[4rem] bg-zinc-100 rounded-md shadow-sm shadow-zinc-300 hover:shadow-md transition-all"
  >
    <p class="ml-4 mt-2 font-kor text-zinc-400">{{ title }}</p>
    <p class="ml-4 font-kor text-bold text-zinc-700">{{ content }}</p>
  </div>
</template>

<style scoped></style>
