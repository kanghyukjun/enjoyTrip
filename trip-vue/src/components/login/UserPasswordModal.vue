<script setup>
import { ref } from "vue";
import { getPassword } from "@/api/user";
import VButtonLarge from "@/components/common/item/VButtonLarge.vue";
import VInputForm from "@/components/common/item/VInputForm.vue";
const emit = defineEmits(["close"]);

const close = () => {
  emit("close");
};

const data = ref({
  loginId: "",
  email: "",
});

const findPassword = () => {
  getPassword(data.value)
    .then((response) => {
      window.alert(`비밀번호는 ${response.data.password} 입니다`);
      close();
    })
    .catch(() => {
      window.alert("해당 아이디와 이메일과 일치하는 비밀번호가 없습니다");
      data.value.loginId = "";
      data.value.email = "";
    });
};

const setEmail = (value) => {
  data.value.email = value;
};

const setId = (value) => {
  data.value.loginId = value;
};
</script>

<template>
  <div
    class="absolute flex flex-row items-center justify-center w-screen h-screen top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 z-20 transition-all"
  >
    <div
      class="absolute flex flex-row items-center justify-center w-screen h-screen bg-black top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 z-20 opacity-50"
      @click="close"
    ></div>
    <div
      class="w-[20rem] h-[20rem] flex flex-col items-center justify-start rounded-lg opacity-100 bg-zinc-100 z-30 font-kor"
    >
      <div class="w-[20rem] h-[3rem] flex items-center justify-between">
        <p class="ml-2 font-bold text-2xl text-gray-700">비밀번호 찾기</p>
        <button
          class="middle none center mr-4 h-8 max-h-[32px] w-8 max-w-[32px] rounded-lg bg-zinc-500 font-sans text-xs font-bold uppercase text-white shadow-sm shadow-zinc-500/20 transition-all hover:shadow-md hover:shadow-zinc-500/40 focus:opacity-[0.85] focus:shadow-none active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
          @click.prevent="close"
        >
          <svg
            class="ml-1"
            xmlns="http://www.w3.org/2000/svg"
            height="24px"
            viewBox="0 -960 960 960"
            width="24px"
            fill="#e8eaed"
          >
            <path
              d="m256-200-56-56 224-224-224-224 56-56 224 224 224-224 56 56-224 224 224 224-56 56-224-224-224 224Z"
            />
          </svg>
        </button>
      </div>
      <div class="relative flex items-center w-[19rem]">
        <div class="flex-grow border-t border-gray-300"></div>
      </div>
      <div class="w-[20rem] h-[18rem] flex flex-col items-center justify-center">
        <div class="relative h-11 w-[18rem] min-w-[200px]">
          <VInputForm label="아이디" @input="setId" />
        </div>
        <div class="relative h-11 w-[18rem] min-w-[200px] mt-5">
          <VInputForm label="이메일" @input="setEmail" />
        </div>
        <div class="w-5/6">
          <VButtonLarge label="비밀번호 찾기" @click="findPassword" />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
