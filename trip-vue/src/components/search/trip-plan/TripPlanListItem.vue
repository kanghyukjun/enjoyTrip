<script setup>
import { onMounted, ref } from "vue";

const props = defineProps({
  spot: Object,
});

const maxTitleLength = 27;
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
    class="w-full h-[8rem] flex flex-row items-center justify-center rounded-md bg-zinc-100 border-zinc-300 border-2 hover:border-4 transition-all"
  >
    <div class="w-5/12 h-[7rem] flex flex-col items-start justify-start font-kor">
      <p class="text-gray-700 font-bold">장소</p>
      <p class="text-gray-700">{{ title }}</p>
    </div>
    <div class="w-5/12 h-[7rem] flex flex-col items-center justify-center">
      <img
        :src="spot.img ? spot.img : '/src/assets/no-image.png'"
        class="w-[6rem] h-[6rem] rounded-md"
      />
    </div>
  </div>
</template>

<style scoped></style>
