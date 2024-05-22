<script setup>
import { RouterView, useRouter } from "vue-router";
import UserPageSideNavBar from "@/components/userpage/navbar/UserPageSideNavBar.vue";
import { useUserStore } from "@/stores/user-counter";
import { onMounted } from "vue";

const router = useRouter();
const store = useUserStore();

onMounted(() => {
  if (!store.isLogined) {
    window.alert("로그인이 필요합니다");
    router.push({ name: "userLogin" });
  }
});
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
