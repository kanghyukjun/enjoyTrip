<script setup>
import { ref } from "vue";
import { getPassword } from "@/api/user";
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
          <input
            class="peer h-full w-full rounded-md border border-blue-gray-200 bg-transparent px-3 py-3 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all placeholder-shown:border placeholder-shown:border-blue-gray-200 placeholder-shown:border-t-blue-gray-200 focus:border-2 focus:border-trip-color focus:border-t-transparent focus:outline-0 disabled:border-0 disabled:bg-blue-gray-50"
            placeHolder=" "
            v-model="data.loginId"
          />
          <label
            class="before:content[' '] after:content[' '] pointer-events-none absolute left-0 -top-1.5 flex h-full w-full select-none text-[11px] font-normal leading-tight text-blue-gray-400 transition-all before:pointer-events-none before:mt-[6.5px] before:mr-1 before:box-border before:block before:h-1.5 before:w-2.5 before:rounded-tl-md before:border-t before:border-l before:border-blue-gray-200 before:transition-all after:pointer-events-none after:mt-[6.5px] after:ml-1 after:box-border after:block after:h-1.5 after:w-2.5 after:flex-grow after:rounded-tr-md after:border-t after:border-r after:border-blue-gray-200 after:transition-all peer-placeholder-shown:text-sm peer-placeholder-shown:leading-[4.1] peer-placeholder-shown:text-blue-gray-500 peer-placeholder-shown:before:border-transparent peer-placeholder-shown:after:border-transparent peer-focus:text-[11px] peer-focus:leading-tight peer-focus:text-trip-color peer-focus:before:border-t-2 peer-focus:before:border-l-2 peer-focus:before:!border-trip-color peer-focus:after:border-t-2 peer-focus:after:border-r-2 peer-focus:after:!border-trip-color peer-disabled:text-transparent peer-disabled:before:border-transparent peer-disabled:after:border-transparent peer-disabled:peer-placeholder-shown:text-blue-gray-500"
          >
            아이디
          </label>
        </div>
        <div class="relative h-11 w-[18rem] min-w-[200px] mt-5">
          <input
            class="peer h-full w-full rounded-md border border-blue-gray-200 bg-transparent px-3 py-3 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all placeholder-shown:border placeholder-shown:border-blue-gray-200 placeholder-shown:border-t-blue-gray-200 focus:border-2 focus:border-trip-color focus:border-t-transparent focus:outline-0 disabled:border-0 disabled:bg-blue-gray-50"
            placeHolder=" "
            v-model="data.email"
          />
          <label
            class="before:content[' '] after:content[' '] pointer-events-none absolute left-0 -top-1.5 flex h-full w-full select-none text-[11px] font-normal leading-tight text-blue-gray-400 transition-all before:pointer-events-none before:mt-[6.5px] before:mr-1 before:box-border before:block before:h-1.5 before:w-2.5 before:rounded-tl-md before:border-t before:border-l before:border-blue-gray-200 before:transition-all after:pointer-events-none after:mt-[6.5px] after:ml-1 after:box-border after:block after:h-1.5 after:w-2.5 after:flex-grow after:rounded-tr-md after:border-t after:border-r after:border-blue-gray-200 after:transition-all peer-placeholder-shown:text-sm peer-placeholder-shown:leading-[4.1] peer-placeholder-shown:text-blue-gray-500 peer-placeholder-shown:before:border-transparent peer-placeholder-shown:after:border-transparent peer-focus:text-[11px] peer-focus:leading-tight peer-focus:text-trip-color peer-focus:before:border-t-2 peer-focus:before:border-l-2 peer-focus:before:!border-trip-color peer-focus:after:border-t-2 peer-focus:after:border-r-2 peer-focus:after:!border-trip-color peer-disabled:text-transparent peer-disabled:before:border-transparent peer-disabled:after:border-transparent peer-disabled:peer-placeholder-shown:text-blue-gray-500"
          >
            이메일
          </label>
        </div>
        <div class="w-5/6">
          <button
            class="mt-6 block w-full select-none rounded-lg bg-gray-600 py-3 px-6 text-center align-middle font-sans text-xs font-bold uppercase text-white shadow-md shadow-gray-600 transition-all hover:shadow-lg hover:shadow-gray-600 focus:opacity-[0.85] focus:shadow-none active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
            type="button"
            data-ripple-light="true"
            @click="findPassword"
          >
            비밀번호 찾기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
