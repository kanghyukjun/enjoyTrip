<script setup>
import { onMounted, ref } from "vue";

const props = defineProps({
  spot: Object,
  height: String,
  maxTitleLength: Number,
});

const title = ref("");

onMounted(() => {
  title.value = props.spot.title;
  if (title.value.length > props.maxTitleLength) {
    title.value = title.value.substring(0, props.maxTitleLength);
    title.value += "...";
  }
});
</script>

<template>
  <div
    class="w-full h-1/3 flex flex-row items-center justify-center rounded-md bg-zinc-100 border-zinc-300 border-2 hover:border-4 transition-all"
    :class="{ 'h-1/3': height === '1/3', 'h-1/4': height === '1/4', 'h-1/6': height === '1/6' }"
  >
    <div class="w-5/12 h-full flex flex-col items-start justify-start font-kor">
      <p class="text-gray-700 font-bold">장소</p>
      <p class="text-gray-700">{{ title }}</p>
    </div>
    <div class="w-5/12 h-full flex flex-col items-center justify-center">
      <div class="w-full h-5/6 flex flex-col items-center justify-center">
        <img
          :src="spot?.img ? spot.img : '/src/assets/no-image.png'"
          class="w-full h-full rounded-md"
        />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
