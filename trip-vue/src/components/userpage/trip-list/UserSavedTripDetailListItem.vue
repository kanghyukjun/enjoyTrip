<script setup>
import { ref, onMounted } from "vue";

const props = defineProps({
  spot: Object,
});

const maxTitleLength = 10;
const title = ref("");

onMounted(() => {
  title.value = props.spot.title;
  if (title.value.length > maxTitleLength) {
    title.value = title.value.substring(0, maxTitleLength);
    title.value += "...";
  }
});
</script>

<template>
  <div
    class="w-full h-1/5 flex flex-row items-center justify-center rounded-md border-2 border-gray-300 hover:border-4 transition-all font-kor text-gray-700"
  >
    <div class="w-1/2 h-full flex flex-row items-start justify-start">{{ title }}</div>
    <div class="w-1/2 h-full flex flex-col items-center justify-center rounded-md">
      <img
        :src="spot?.img ? spot.img : '/src/assets/no-image.png'"
        class="w-full h-full rounded-md"
      />
    </div>
  </div>
</template>

<style scoped></style>
