<script setup>
import { ref } from "vue";

defineProps({
  name: String,
  imgSrc: String,
});

const isHovered = ref(false);

const mouseOver = () => {
  isHovered.value = true;
};

const mouseOut = () => {
  isHovered.value = false;
};
</script>

<template>
  <li
    @mouseover="mouseOver"
    @mouseout="mouseOut"
    class="mb-2 px-6 py-4 font-kor flex flex-row border-none rounded rounded-lg"
    :class="{
      'text-zinc-100': !isHovered,
      'text-zinc-600': isHovered,
      'bg-gray-100': isHovered,
      'font-bold': isHovered,
    }"
  >
    <span class="flex justify-center items-center">
      <img :src="imgSrc" alt="noimg" :class="{ 'brightness-0': isHovered }" />
    </span>
    <a href="#">
      <span class="ml-2">{{ name }}</span>
    </a>
  </li>
</template>

<style scoped></style>
