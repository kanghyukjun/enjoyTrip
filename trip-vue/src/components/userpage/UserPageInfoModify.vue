<script setup>
import { ref, computed } from "vue";
import { useRouter } from "vue-router";

import UserRegisterFormItem from "@/components/login/UserRegisterFormItem.vue";

const router = useRouter();

const password = ref("");
const nickname = ref("");
const email = ref("");

const passwordChange = (value) => {
  password.value = value;
};

const nicknameChange = (value) => {
  nickname.value = value;
};

const emailChange = (value) => {
  email.value = value;
};

const isContentExist = computed(() => {
  return !(password.value.length === 0 && nickname.value.length === 0 && email.value.length === 0);
});

const update = () => {
  router.push({ name: "userinfo" });
};

const cancel = () => {
  if (isContentExist.value) {
    const confirm = window.confirm(
      "작성중인 내용이 있습니다. 작성중인 내용을 취소하고 이전 페이지로 돌아가시겠습니까?"
    );
    if (confirm) {
      router.push({ name: "userinfo" });
    } else {
      return false;
    }
  } else {
    router.push({ name: "userinfo" });
  }
};
</script>

<template>
  <div class="w-4/5 h-4/5 flex flex-col">
    <h1 class="font-bold text-[1.75rem] font-kor text-zinc-600">내 정보 관리</h1>
    <div class="w-full h-full flex flex-row">
      <div class="flex flex-col justify-center items-center w-[10rem] gap-4">
        <img
          class="w-40 rounded-2xl transition-all shadow-sm hover:shadow-md"
          src="@/assets/no-image.png"
        />
        <button
          class="middle none center rounded-lg bg-trip-color py-3 px-6 font-sans text-xs font-bold uppercase text-white shadow-sm shadow-trip-color transition-all hover:shadow-md hover:shadow-trip-color focus:opacity-[0.85] focus:shadow-none active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
          data-ripple-light="true"
        >
          프로필 등록
        </button>
      </div>
      <div class="flex flex-col justify-center items-end w-[40rem] gap-4">
        <div class="flex flex-col w-[30rem] h-[14rem] gap-4">
          <UserRegisterFormItem label="아이디" type="text" />
          <UserRegisterFormItem
            label="비밀번호"
            type="password"
            @inputChangeEvent="passwordChange"
          />
          <UserRegisterFormItem label="닉네임" type="text" @inputChangeEvent="nicknameChange" />
          <UserRegisterFormItem label="이메일" type="text" @inputChangeEvent="emailChange" />
        </div>
        <div class="flex flex-row justify-center items-center gap-2">
          <button
            class="middle none center rounded-lg bg-orange-700 py-2 px-4 font-sans text-xs font-bold uppercase text-white shadow-sm shadow-orange-700 transition-all hover:shadow-md hover:shadow-orange-700 focus:opacity-[0.85] focus:shadow-none active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
            data-ripple-light="true"
            @click="cancel"
          >
            취소
          </button>
          <button
            class="middle none center rounded-lg bg-trip-color py-2 px-4 font-sans text-xs font-bold uppercase text-white shadow-sm shadow-trip-color transition-all hover:shadow-md hover:shadow-trip-color focus:opacity-[0.85] focus:shadow-none active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
            data-ripple-light="true"
            @click="update"
          >
            적용
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
