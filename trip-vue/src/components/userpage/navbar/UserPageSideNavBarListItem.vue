<script setup>
import { ref } from "vue";
import { RouterLink } from "vue-router";

defineProps({
  title: String,
  name: String,
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
  <router-link :to="{ name }">
    <li
      @mouseover="mouseOver"
      @mouseout="mouseOut"
      class="w-36 h-12 font-kor flex flex-row items-center justify-start border-none rounded rounded-lg font-bold"
      :class="{
        'text-zinc-600': !isHovered,
        'text-zinc-600': isHovered,
        'bg-gray-200': isHovered,
      }"
    >
      <span class="ml-2" :class="{ 'font-bold': isHovered }">{{ title }}</span>
    </li>
  </router-link>
</template>

<style scoped></style>
