<script setup>
import { ref } from "vue";
import { RouterLink } from "vue-router";

import UserRegisterFormItem from "@/components/login/UserRegisterFormItem.vue";
import { getUserIdExists } from "@/api/login.js";

const isPasswordLengthOkay = ref(false);
const isIdLengthOkay = ref(false);
const isIdDuplicated = ref(false);

const passwordChange = (value) => {
  isPasswordLengthOkay.value = 5 <= value.length && value.length <= 16;
};

const idChange = (value) => {
  if (value.length < 5 || value.length > 16) {
    isIdLengthOkay.value = false;
    isIdDuplicated.value = false;
  } else {
    isIdLengthOkay.value = true;
    getUserIdExists(value, (response) => {
      isIdDuplicated.value = response.data;
    });
  }
};
</script>

<template>
  <div
    class="flex h-[38rem] w-[34rem] items-center justify-center rounded shadow-md bg-zinc-100 font-kor"
  >
    <div
      class="relative flex flex-col rounded-xl bg-transparent bg-clip-border text-gray-700 shadow-none"
    >
      <h4
        class="block font-sans text-2xl font-semibold leading-snug tracking-normal text-blue-gray-900 antialiased"
      >
        회원가입
      </h4>
      <p
        class="mt-1 block font-sans text-base font-normal leading-relaxed text-gray-700 antialiased"
      >
        회원 가입을 위한 정보를 입력해주세요
      </p>
      <form class="mt-8 mb-2 w-80 max-w-screen-lg sm:w-96">
        <div class="mb-4 flex flex-col gap-1">
          <UserRegisterFormItem label="아이디" @InputChangeEvent="idChange" />
          <p class="ml-2 text-red-500 font-kor font text-sm" v-show="!isIdLengthOkay">
            아이디는 5자리 이상 16자리 이하가 되어야 합니다
          </p>
          <p class="ml-2 text-red-500 font-kor font text-sm" v-show="isIdDuplicated">
            이미 존재하는 아이디 입니다
          </p>
        </div>
        <div class="mb-4 flex flex-col gap-1">
          <UserRegisterFormItem
            label="비밀번호"
            type="password"
            @InputChangeEvent="passwordChange"
          />
          <p class="ml-2 text-red-500 font-kor font text-sm" v-show="!isPasswordLengthOkay">
            비밀번호는 5자리 이상 16자리 이하가 되어야 합니다
          </p>
        </div>
        <div class="mb-4 flex flex-col gap-6">
          <UserRegisterFormItem label="이름" @InputChangeEvent="nameChange" />
          <UserRegisterFormItem label="이메일" @InputChangeEvent="emailChange" />
        </div>
        <button
          class="mt-6 block w-full select-none rounded-lg bg-blue-400 py-3 px-6 text-center align-middle font-sans text-xs font-bold uppercase text-white shadow-md shadow-blue-500/20 transition-all hover:shadow-lg hover:shadow-blue-500/40 focus:opacity-[0.85] focus:shadow-none active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
          type="button"
          data-ripple-light="true"
        >
          회원가입
        </button>
        <div class="relative flex py-5 items-center">
          <div class="flex-grow border-t border-gray-400"></div>
          <span class="flex-shrink mx-4 text-gray-400">OR</span>
          <div class="flex-grow border-t border-gray-400"></div>
        </div>
        <p
          class="mt-4 block text-center font-sans text-base font-normal leading-relaxed text-gray-700 antialiased"
        >
          이미 계정이 있으신가요?
          <RouterLink
            class="font-semibold text-blue-400 transition-colors hover:text-blue-700"
            href="#"
            :to="{ name: 'login' }"
          >
            로그인
          </RouterLink>
        </p>
      </form>
    </div>
  </div>
</template>

<style scoped></style>
