<script setup>
import { useRouter } from "vue-router";

import UserPageInfoItem from "./UserPageInfoItem.vue";

const router = useRouter();

const modify = () => {
  router.push({ name: "modify" });
};
</script>

<template>
  <div class="w-4/5 h-4/5 flex flex-col">
    <h1 class="font-bold text-[1.75rem] font-kor text-zinc-600">내 정보 관리</h1>
    <div class="w-full h-full flex flex-row">
      <div class="flex flex-col justify-center items-center w-[10rem]">
        <img
          class="w-40 rounded-2xl transition-all shadow-sm hover:shadow-md"
          src="@/assets/no-image.png"
        />
      </div>
      <div class="flex flex-col justify-center items-end w-[40rem] gap-4">
        <UserPageInfoItem title="아이디" />
        <UserPageInfoItem title="닉네임" />
        <UserPageInfoItem title="이메일" />
        <button
          class="middle none center rounded-lg bg-trip-color py-2 px-4 font-sans text-xs font-bold uppercase text-white shadow-sm shadow-trip-color transition-all hover:shadow-md hover:shadow-trip-color focus:opacity-[0.85] focus:shadow-none active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
          data-ripple-light="true"
          @click="modify"
        >
          수정
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
