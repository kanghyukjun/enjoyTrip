<script setup>
import { useUserStore } from "@/stores/login";
import { getUserInfo } from "@/api/user";
import UserPageSideNavBarList from "@/components/userpage/navbar/UserPageSideNavBarList.vue";
import { onMounted, ref, watch } from "vue";

import { toast } from "vue3-toastify";
import "vue3-toastify/dist/index.css";

const store = useUserStore();
const user = ref({});
onMounted(() => {
  getUserInfo(store.loginId)
    .then((response) => {
      user.value = response.data.userInfo;
    })
    .catch(() => {
      toast.error("오류");
    });
});

watch(
  () => user.value.image,
  (newValue) => {
    user.value.image = newValue;
  },
  {
    deep: true,
  }
);
</script>

<template>
  <div class="font-kor">
    <nav
      class="flex flex-col justify-center items-cente w-52 h-screen px-4 text-gray-800 border-none"
    >
      <div
        class="flex flex-col justify-center items-center h-[40rem] bg-zinc-100 border-none rounded-lg shadow-lg"
      >
        <div class="flex flex-wrap h-1/3 mt-10">
          <div class="flex flex-col w-full justify-center items-center gap-3">
            <img
              :src="user.image ? user.image : '/src/assets/no-image.png'"
              class="w-[8rem] h-[7rem] rounded-2xl"
            />
            <p class="font-bold">{{ user.name }}</p>
          </div>
        </div>
        <div class="h-2/3">
          <UserPageSideNavBarList />
        </div>
      </div>
    </nav>
  </div>
</template>

<style scoped></style>
