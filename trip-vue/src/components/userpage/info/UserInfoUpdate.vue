<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { getUserInfo, userUpdate } from "@/api/user";
import { useUserStore } from "@/stores/login";
import { getFileDataFromInput, isFileSizeOk, encodeImageToBase64 } from "@/util/image";

import { toast } from "vue3-toastify";
import "vue3-toastify/dist/index.css";

import VInputForm from "@/components/common/item/VInputForm.vue";
import VButton from "@/components/common/item/VButton.vue";
import VButtonLarge from "@/components/common/item/VButtonLarge.vue";

const router = useRouter();
const store = useUserStore();

const userInfo = ref({
  loginId: "",
  password: "",
  name: "",
  email: "",
  image: "",
});

// Axios 비동기 처리
onMounted(async () => {
  const result = await getUserInfo(store.loginId);
  userInfo.value.loginId = result.data.userInfo.loginId;
  userInfo.value.password = result.data.userInfo.password;
  userInfo.value.name = result.data.userInfo.name;
  userInfo.value.email = result.data.userInfo.email;
  userInfo.value.image = result.data.userInfo.image;
});

// Input 변경
const passwordChange = (value) => {
  userInfo.value.password = value;
};

const passwordRepeatChange = (value) => {
  repeatedPassword.value = value;
};

const nicknameChange = (value) => {
  userInfo.value.name = value;
};

const emailChange = (value) => {
  userInfo.value.email = value;
};

// Validation
const repeatedPassword = ref("");

const isPasswordValid = computed(() => {
  return 5 <= userInfo.value.password.length && userInfo.value.password.length <= 16;
});

const isPasswordEqual = computed(() => {
  return userInfo.value.password === repeatedPassword.value;
});

const isContentExist = computed(() => {
  return !(
    userInfo.value.password.length === 0 &&
    userInfo.value.name.length === 0 &&
    userInfo.value.email.length === 0
  );
});

const isAllContentExist = computed(() => {
  return (
    userInfo.value.password.length != 0 &&
    userInfo.value.name.length != 0 &&
    userInfo.value.email.length != 0
  );
});

// Button
const update = async () => {
  if (isPasswordValid.value && isPasswordEqual.value && isAllContentExist.value) {
    await userUpdate(store.loginId, userInfo.value);
    router.push({ name: "userInfo" });
  } else {
    toast.error("올바른 정보를 입력해주세요");
  }
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

// File Input
const triggerInput = () => {
  document.querySelector("#profile").click();
};

const deleteProfile = () => {
  userInfo.value.image = "";
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
        <VButtonLarge label="프로필 등록" color="trip" @click="triggerInput" />
        <VButtonLarge label="프로필 삭제" color="gray" @click="deleteProfile" />
      </div>
      <div class="flex flex-col justify-center items-end w-[40rem] gap-4">
        <div class="flex flex-col w-[30rem] h-[20rem] gap-4">
          <VInputForm label="아이디" :value="userInfo.loginId" readonly />
          <div class="flex flex-col w-[30rem] h-auto">
            <VInputForm label="비밀번호" type="password" @input="passwordChange" />
            <p class="ml-2 text-red-500 font-kor font text-sm" v-show="!isPasswordValid">
              비밀번호는 5자리 이상 16자리 이하가 되어야 합니다
            </p>
          </div>
          <div class="flex flex-col w-[30rem] h-auto">
            <VInputForm label="비밀번호 확인" type="password" @input="passwordRepeatChange" />
            <p class="ml-2 text-red-500 font-kor font text-sm" v-show="!isPasswordEqual">
              비밀번호가 동일하지 않습니다
            </p>
          </div>
          <VInputForm label="닉네임" :value="userInfo.name" @input="nicknameChange" />
          <VInputForm label="이메일" :value="userInfo.email" @input="emailChange" />
        </div>
        <div class="flex flex-row justify-center items-center gap-2">
          <VButton label="취소" color="red" @click="cancel" />
          <VButton label="적용" color="trip" @click="update" />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
