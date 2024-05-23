<script setup>
import { RouterView } from "vue-router";
import SideNavBar from "./components/common/navbar/SideNavBar.vue";
window.Kakao.init("825ba8eb447bc5dac3843c7ff02c14fa");
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
