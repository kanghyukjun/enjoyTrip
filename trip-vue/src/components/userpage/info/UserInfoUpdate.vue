<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { getUserInfo, userUpdate } from "@/api/user";
import { useUserStore } from "@/stores/login";
import { getFileDataFromInput, isFileSizeOk, encodeImageToBase64 } from "@/util/image";

import { toast } from "vue3-toastify";
import "vue3-toastify/dist/index.css";

import UserRegisterFormItem from "@/components/login/UserRegisterFormItem.vue";

const router = useRouter();
const store = useUserStore();

const userInfo = ref({
  loginId: "",
  password: "",
  name: "",
  email: "",
  image: "",
});

onMounted(async () => {
  const result = await getUserInfo(store.loginId);
  userInfo.value.loginId = result.data.userInfo.loginId;
  userInfo.value.password = result.data.userInfo.password;
  userInfo.value.name = result.data.userInfo.name;
  userInfo.value.email = result.data.userInfo.email;
  userInfo.value.image = result.data.userInfo.image;
});

const passwordChange = (value) => {
  userInfo.value.password = value;
};

const nicknameChange = (value) => {
  userInfo.value.name = value;
};

const emailChange = (value) => {
  userInfo.value.email = value;
};

const isContentExist = computed(() => {
  return !(
    userInfo.value.password.length === 0 &&
    userInfo.value.name.length === 0 &&
    userInfo.value.email.length === 0
  );
});

const update = async () => {
  // update 쿼리 날려
  await userUpdate(store.loginId, userInfo.value);
  router.push({ name: "userInfo" });
};

const cancel = () => {
  if (isContentExist.value) {
    const confirm = window.confirm(
      "작성중인 내용이 있습니다. 작성중인 내용을 취소하고 이전 페이지로 돌아가시겠습니까?"
    );
    if (confirm) {
      router.push({ name: "userInfo" });
    } else {
      return false;
    }
  } else {
    router.push({ name: "userInfo" });
  }
};

const triggerInput = () => {
  document.querySelector("#profile").click();
};

const fileInput = (event) => {
  const file = getFileDataFromInput(event);
  if (!isFileSizeOk(file)) {
    toast.error("8MB 이하의 이미지만 등록해주세요");
    return;
  }

  encodeImageToBase64(file).then((response) => {
    userInfo.value.image = response;
  });
};
</script>

<template>
  <div class="w-4/5 h-4/5 flex flex-col">
    <h1 class="font-bold text-[1.75rem] font-kor text-zinc-600">내 정보 관리</h1>
    <div class="w-full h-full flex flex-row">
      <div class="flex flex-col justify-center items-center w-[10rem] gap-4">
        <img
          id="imgContainer"
          class="w-[12rem] h-[10rem] rounded-2xl transition-all shadow-sm hover:shadow-md"
          :src="userInfo.image ? userInfo.image : '/src/assets/no-image.png'"
        />
        <input
          id="profile"
          type="file"
          class="hidden"
          accept="image/png, image/jpeg"
          @input="fileInput"
        />
        <button
          class="middle none center rounded-lg bg-trip-color py-3 px-6 font-sans text-xs font-bold uppercase text-white shadow-sm shadow-trip-color transition-all hover:shadow-md hover:shadow-trip-color focus:opacity-[0.85] focus:shadow-none active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
          data-ripple-light="true"
          @click="triggerInput"
        >
          프로필 등록
        </button>
      </div>
      <div class="flex flex-col justify-center items-end w-[40rem] gap-4">
        <div class="flex flex-col w-[30rem] h-[14rem] gap-4">
          <UserRegisterFormItem label="아이디" type="text" :value="userInfo.loginId" readonly />
          <UserRegisterFormItem
            label="비밀번호"
            type="text"
            :value="userInfo.password"
            @inputChangeEvent="passwordChange"
          />
          <UserRegisterFormItem
            label="닉네임"
            type="text"
            :value="userInfo.name"
            @inputChangeEvent="nicknameChange"
          />
          <UserRegisterFormItem
            label="이메일"
            type="text"
            :value="userInfo.email"
            @inputChangeEvent="emailChange"
          />
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
