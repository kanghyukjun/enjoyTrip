<script setup>
import UserPageSideNavBarList from "@/components/userpage/navbar/UserPageSideNavBarList.vue";
</script>

<template>
  <div class="font-kor">
    <nav class="flex flex-col bg-blue-200 w-52 h-screen px-4 text-gray-800 border-none">
      <div class="flex flex-wrap h-1/3 mt-10">
        <div class="flex flex-col w-full justify-center items-center gap-3">
          <img src="@/assets/no-image.png" class="w-32 rounded-2xl" />
          <p class="font-bold">유저 이름</p>
        </div>
      </div>
      <div class="h-2/3">
        <UserPageSideNavBarList />
      </div>
    </nav>
  </div>
</template>

<style scoped></style>
