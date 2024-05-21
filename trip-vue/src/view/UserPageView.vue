<script setup>
import { RouterView } from "vue-router";
import UserPageSideNavBar from "@/components/userpage/navbar/UserPageSideNavBar.vue";
</script>

<template>
  <div class="flex flex-row border-none w-full h-full">
    <div class="flex justify-center items-center bg-zinc-100 border-none w-full h-full">
      <div class="flex justify-center items-center h-5/6 w-5/6 bg-zinc-100 rounded-lg shadow-lg">
        <RouterView />
      </div>
    </div>
    <UserPageSideNavBar />
  </div>
</template>

<style scoped></style>
