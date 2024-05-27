<script setup>
import { ref } from "vue";
import { isFileSizeOk, getFileDataFromInput } from "@/util/image";
import VButton from "@/components/common/item/VButton.vue";
const filePath = ref("");

const emit = defineEmits(["fileUploaded"]);

const imgName = ref("");

const triggerInputElement = () => {
  document.getElementById("uploadFile").click();
};

const fileInput = (event) => {
  const file = getFileDataFromInput(event);
  if (!file.type.match("image/.*")) {
    window.alert("이미지 확장자만 업로드 가능합니다");
    return;
  } else if (!isFileSizeOk(file)) {
    window.alert("8MB 이하의 이미지만 등록해주세요");
    return;
  }
  // 미리보기 이미지 제공
  imgName.value = file.name;
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
    <div class="w-[15rem] relative ml-2 mt-2 flex items-center">
      <div class="flex-grow border-t border-gray-300"></div>
    </div>
    <div class="w-[16rem] h-[4rem] flex flex-col items-start justify-center">
      <label for="uploadFile" class="text-sm ml-3">8MB 이하의 이미지만 등록해주세요</label>
      <div class="w-full h-[3rem] flex flex-row items-center justify-start rounded-md border gap-2">
        <VButton
          class="ml-2 wrap whitespace-nowrap"
          color="gray"
          label="선택"
          @click="triggerInputElement"
        />
        <p class="font-kor text-gray-700 text-xs">
          이미지 등록 :
          {{ imgName ? imgName : "등록된 이미지가 없습니다" }}
        </p>
      </div>
      <input
        id="uploadFile"
        type="file"
        class="ml-3 font-kor font-gray-700 rouned-md hidden"
        accept="image/png, image/jpeg"
        @change="fileInput"
      />
    </div>
  </div>
</template>

<style scoped></style>
