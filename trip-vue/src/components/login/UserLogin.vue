<script setup>
import VButtonLarge from "@/components/common/item/VButtonLarge.vue";
import VInputForm from "@/components/common/item/VInputForm.vue";
import UserPasswordModal from "@/components/login/UserPasswordModal.vue";

import { ref } from "vue";
import { RouterLink, useRouter } from "vue-router";

import { useUserStore } from "@/stores/login";

const store = useUserStore();
const router = useRouter();

const isShowModal = ref(false);

const userInfo = ref({
  loginId: "",
  password: "",
});

// event
const idChange = (value) => {
  userInfo.value.loginId = value;
};

const passwordChange = (value) => {
  userInfo.value.password = value;
};

// button
const login = async () => {
  store
    .login(userInfo.value)
    .then(() => {
      router.push({ name: "home" });
    })
    .catch(() => {
      window.alert("올바르지 않은 회원 정보입니다");
    });
};
</script>

<template>
  <UserPasswordModal v-show="isShowModal" @close="isShowModal = false" />
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
          <VInputForm label="아이디" @input="idChange" :width="1" />
          <VInputForm label="비밀번호" type="password" @input="passwordChange" />
          <VButtonLarge label="로그인" @click="login" color="trip" />
        </div>
        <div class="flex flex-col items-center justify-center">
          <div class="flex flex-row items-center justify-center mt-2 text-sm">
            <p class="font-kor text-gray-700">비밀번호를 잊어버리셨나요?</p>
            <p class="ml-2 text-trip-color font-kor cursor-pointer" @click="isShowModal = true">
              비밀번호 찾기
            </p>
          </div>
        </div>
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
            :to="{ name: 'userRegister' }"
          >
            회원가입
          </RouterLink>
        </p>
      </form>
    </div>
  </div>
</template>

<style scoped></style>
