<script setup>
import { ref } from "vue";
const filePath = ref("");

const emit = defineEmits(["fileUploaded"]);

const fileInput = (event) => {
  const file = event.target.files[0];
  if (!file.type.match("image/.*")) {
    window.alert("이미지 확장자만 업로드 가능합니다");
    return;
  }
  // 미리보기 이미지 제공
  let reader = new FileReader();
  reader.onload = (event) => {
    filePath.value = event.target.result;
  };
  reader.readAsDataURL(file);
  emit("fileUploaded", file);
};
</script>

<template>
  <div class="w-[17rem] h-[30rem] flex flex-col items-center justify-center">
    <div
      class="w-[16rem] h-[15rem] flex flex-col items-center justify-center rounded-md border-2 border-gray-200"
    >
      <!-- 이미지 미리보기 -->
      <img
        id="imagePreview"
        :src="filePath"
        v-show="filePath !== ''"
        class="w-[16rem] h-[16rem] rounded-md"
      />
    </div>
    <div class="w-[15rem] relative flex py-5 items-center">
      <div class="flex-grow border-t border-gray-300"></div>
    </div>
    <div class="w-[16rem] h-[3rem] flex flex-col items-start justify-center">
      <input
        id="uploadFile"
        type="file"
        class="ml-3 font-kor font-gray-700"
        accept="image/png, image/jpeg"
        @change="fileInput"
      />
    </div>
  </div>
</template>

<style scoped></style>
