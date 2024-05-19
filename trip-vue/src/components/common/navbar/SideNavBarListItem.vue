<script setup>
import { ref } from "vue";
import { RouterLink } from "vue-router";

defineProps({
  name: String,
  title: String,
  imgSrc: String,
  params: Object,
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
  <router-link :to="{ name, params }">
    <li
      @mouseover="mouseOver"
      @mouseout="mouseOut"
      class="mb-2 px-6 py-4 font-kor flex flex-row border-none rounded rounded-lg"
      :class="{
        'text-zinc-100': !isHovered,
        'text-zinc-600': isHovered,
        'bg-gray-100': isHovered,
      }"
    >
      <span class="flex justify-center items-center">
        <img :src="imgSrc" alt="noimg" :class="{ 'brightness-50': isHovered }" />
      </span>

      <span class="ml-2" :class="{ 'font-bold': isHovered }">{{ title }}</span>
    </li>
  </router-link>
</template>

<style scoped></style>
