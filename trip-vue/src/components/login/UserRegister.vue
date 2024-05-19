<script setup>
import { ref } from "vue";
import { RouterLink, useRouter } from "vue-router";

import UserRegisterFormItem from "@/components/login/UserRegisterFormItem.vue";
import { getUserIdExists, userRegister } from "@/api/login.js";

const router = useRouter();

const isPasswordLengthOkay = ref(false);
const isIdLengthOkay = ref(false);
const isIdDuplicated = ref(false);
const isNameLengthOkay = ref(true);
const isEmailLengthOkay = ref(true);

const userInfo = ref({
  loginId: "",
  password: "",
  name: "",
  email: "",
});

const passwordChange = (value) => {
  isPasswordLengthOkay.value = 5 <= value.length && value.length <= 20;
  if (isPasswordLengthOkay.value) {
    userInfo.value.password = value;
  }
};

const idChange = (value) => {
  if (value.length < 5 || value.length > 20) {
    isIdLengthOkay.value = false;
    isIdDuplicated.value = false;
  } else {
    isIdLengthOkay.value = true;
    getUserIdExists(value, (response) => {
      isIdDuplicated.value = response.data;
    });
    if (!isIdDuplicated.value) {
      userInfo.value.loginId = value;
    }
  }
};

const nameChange = (value) => {
  if (value.length > 10) {
    isNameLengthOkay.value = false;
  } else {
    isNameLengthOkay.value = true;
    userInfo.value.name = value;
  }
};

const emailChange = (value) => {
  if (value.length > 50) {
    isEmailLengthOkay.value = false;
  } else {
    isEmailLengthOkay.value = true;
    userInfo.value.email = value;
  }
};

const register = () => {
  if (
    !isPasswordLengthOkay.value ||
    !isIdLengthOkay.value ||
    isIdDuplicated.value ||
    !isNameLengthOkay.value ||
    !isEmailLengthOkay.value
  ) {
    window.alert("올바른 정보를 입력해주세요");
  } else {
    userRegister(
      userInfo.value,
      () => {
        window.alert("회원 가입이 완료 되었습니다");
        router.push({ name: "userlogin" });
      },
      (error) => {
        console.log(error);
        window.alert("서버 내부 에러");
      }
    );
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
        <div class="mb-4 flex flex-col gap-1">
          <UserRegisterFormItem label="이름" @InputChangeEvent="nameChange" />
          <p class="ml-2 text-red-500 font-kor font text-sm" v-show="!isNameLengthOkay">
            이름은 10자리 이하가 되어야 합니다
          </p>
        </div>
        <div class="mb-4 flex flex-col gap-1">
          <UserRegisterFormItem label="이메일" @InputChangeEvent="emailChange" />
          <p class="ml-2 text-red-500 font-kor font text-sm" v-show="!isEmailLengthOkay">
            이메일은 50자리 이하가 되어야 합니다
          </p>
        </div>
        <button
          class="mt-6 block w-full select-none rounded-lg bg-trip-color py-3 px-6 text-center align-middle font-sans text-xs font-bold uppercase text-white shadow-md shadow-trip-color transition-all hover:shadow-lg hover:shadow-trip-color focus:opacity-[0.85] focus:shadow-none active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
          type="button"
          data-ripple-light="true"
          @click="register"
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
            class="font-semibold text-trip-color transition-colors hover:text-orange-700"
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
