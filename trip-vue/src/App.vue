<script setup>
import { RouterView } from "vue-router";
import SideNavBar from "./components/common/SideNavBar.vue";
</script>

<template>
  <div class="flex flex-row">
    <SideNavBar />
    <RouterView />
  </div>
</template>

<style scoped></style>
