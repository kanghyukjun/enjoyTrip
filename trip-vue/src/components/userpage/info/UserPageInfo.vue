<script setup>
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";

import { useUserStore } from "@/stores/login";
import { getUserInfo } from "@/api/user";
import UserPageInfoItem from "@/components/userpage/info/UserPageInfoItem.vue";
import VButton from "@/components/common/item/VButton.vue";

const router = useRouter();
const store = useUserStore();

const userInfo = ref({
  loginId: "",
  name: "",
  email: "",
  image: "",
});

const modify = () => {
  router.push({ name: "userModify" });
};

onMounted(async () => {
  getUserInfo(store.loginId)
    .then((response) => {
      userInfo.value.loginId = response.data.userInfo.loginId;
      userInfo.value.name = response.data.userInfo.name;
      userInfo.value.email = response.data.userInfo.email;
      userInfo.value.image = response.data.userInfo.image;
    })
    .catch((error) => {
      console.log(error);
    });
});
</script>

<template>
  <div class="w-4/5 h-4/5 flex flex-col">
    <h1 class="font-bold text-[1.75rem] font-kor text-zinc-600">내 정보 관리</h1>
    <div class="w-full h-full flex flex-row">
      <div class="flex flex-col justify-center items-center w-[10rem]">
        <img
          class="w-[12rem] h-[10rem] rounded-2xl transition-all shadow-sm hover:shadow-md"
          :src="userInfo.image ? userInfo.image : '/src/assets/no-image.png'"
        />
      </div>
      <div class="flex flex-col justify-center items-end w-[40rem] gap-4">
        <UserPageInfoItem title="아이디" :content="userInfo.loginId" />
        <UserPageInfoItem title="닉네임" :content="userInfo.name" />
        <UserPageInfoItem title="이메일" :content="userInfo.email" />
        <VButton color="trip" label="수정" @click="modify" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
