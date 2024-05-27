<script setup>
defineProps({
  label: String,
});
</script>

<template>
  <div class="relative flex py-5 items-center">
    <div class="flex-grow border-t border-gray-400"></div>
    <span v-show="label" class="flex-shrink mx-4 text-gray-400">{{ label }}</span>
    <div class="flex-grow border-t border-gray-400"></div>
  </div>
</template>

<style scoped></style>
