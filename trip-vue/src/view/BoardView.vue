<script setup>
import { RouterView } from "vue-router";
</script>

<template>
  <div class="w-full h-full flex flex-row justify-center items-center wrap">
    <div class="w-4/5 h-4/5 flex flex-col items-center justify-center">
      <RouterView />
    </div>
  </div>
</template>

<style scoped></style>
