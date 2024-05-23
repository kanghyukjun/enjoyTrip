<script setup>
import SideNavBarList from "./SideNavBarList.vue";
</script>

<template>
  <!-- component -->
  <div>
    <nav class="flex flex-col bg-trip-color w-52 h-screen px-4 text-gray-900 border-none z-30">
      <div class="flex flex-wrap h-1/6">
        <div class="flex w-full justify-center items-center">
          <span class="font-semibold text-white">
            <img src="/src/assets/logo.png" />
          </span>
        </div>
      </div>
      <div class="h-5/6">
        <SideNavBarList />
      </div>
    </nav>
  </div>
</template>

<style scoped></style>
