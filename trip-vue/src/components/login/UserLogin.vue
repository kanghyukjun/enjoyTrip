<script setup>
import { RouterLink } from "vue-router";
import UserRegisterFormItem from "@/components/login/UserRegisterFormItem.vue";
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
        로그인
      </h4>
      <p
        class="mt-1 block font-sans text-base font-normal leading-relaxed text-gray-700 antialiased"
      >
        아이디와 비밀번호를 입력해주세요
      </p>
      <form class="mt-8 mb-2 w-80 max-w-screen-lg sm:w-96">
        <div class="mb-4 flex flex-col gap-6">
          <UserRegisterFormItem label="아이디" @InputChangeEvent="idChange" />
          <UserRegisterFormItem
            label="비밀번호"
            type="password"
            @InputChangeEvent="passwordChange"
          />
        </div>
        <button
          class="mt-6 block w-full select-none rounded-lg bg-trip-color py-3 px-6 text-center align-middle font-sans text-xs font-bold uppercase text-white shadow-md shadow-trip-color transition-all hover:shadow-lg hover:shadow-trip-color focus:opacity-[0.85] focus:shadow-none active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
          type="button"
          data-ripple-light="true"
        >
          로그인
        </button>
        <div class="relative flex py-5 items-center">
          <div class="flex-grow border-t border-gray-400"></div>
          <span class="flex-shrink mx-4 text-gray-400">OR</span>
          <div class="flex-grow border-t border-gray-400"></div>
        </div>
        <p
          class="mt-4 block text-center font-sans text-base font-normal leading-relaxed text-gray-700 antialiased"
        >
          아직 계정이 없으신가요?
          <RouterLink
            class="font-semibold text-trip-color transition-colors hover:text-orange-700"
            href="#"
            :to="{ name: 'register' }"
          >
            회원가입
          </RouterLink>
        </p>
      </form>
    </div>
  </div>
</template>

<style scoped></style>
