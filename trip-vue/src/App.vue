<script setup>
import { RouterView } from "vue-router";
import SideNavBar from "./components/common/SideNavBar.vue";
</script>

<template>
  <div class="flex flex-row border-none">
    <SideNavBar />
    <div class="flex justify-center items-center w-screen h-screen bg-zinc-100 border-none">
      <RouterView />
    </div>
  </div>
</template>

<style scoped></style>
