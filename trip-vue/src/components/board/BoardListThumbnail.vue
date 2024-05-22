<script setup>
defineProps({
  xCoord: Number,
  yCoord: Number,
  show: Boolean,
  img: String,
});
</script>

<template>
  <div
    class="fixed w-[5rem] h-[5rem] rounded transition-opacity pointer-events-none"
    :class="{ 'opacity-100': show, 'opacity-0': !show }"
    :style="{ left: xCoord + 'px', top: yCoord + 'px' }"
  >
    <img :src="img" class="rounded-md w-[5rem] h-[4.5rem]" />
  </div>
</template>

<style scoped></style>
