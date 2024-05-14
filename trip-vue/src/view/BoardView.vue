<script setup>
import { RouterView } from "vue-router";
</script>

<template>
  <div class="w-full h-full flex flex-row justify-center items-center wrap">
    <div class="w-4/5 h-4/5 flex flex-col items-center justify-center">
      <div
        class="w-full h-full flex flex-col bg-zinc-100 justify-center items-center border-lg shadow-md"
      >
        <RouterView />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
