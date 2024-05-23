<script setup>
import { ref, onMounted, watch, computed } from "vue";
import { useRouter, useRoute } from "vue-router";
import { registArticle, getArticleDetail, updateArticle } from "@/api/board";
import { useUserStore } from "@/stores/login";
import { getFileDataFromInput, isFileSizeOk, encodeImageToBase64, getFileName } from "@/util/image";

import { toast } from "vue3-toastify";
import "vue3-toastify/dist/index.css";

import VButton from "@/components/common/item/VButton.vue";
import BoardArticleFormPlanSelectModal from "./BoardArticleFormPlanSelectModal.vue";

const props = defineProps({
  type: String,
});

const type = props.type;

const store = useUserStore();
const route = useRoute();
const router = useRouter();

const isModalSeen = ref(false);

const article = ref({
  title: "",
  content: "",
  thumbnail: "",
  courseId: -1,
});

const maxContentSize = parseInt(import.meta.env.VITE_BOARD_MAX_CONTENT_SIZE);
const maxTitleSize = parseInt(import.meta.env.VITE_BOARD_MAX_TITLE_SIZE);
const isValid = computed(() => {
  return article.value.title && article.value.content && article.value.courseId != -1;
});

watch(
  () => article.value.content,
  (newValue) => {
    if (newValue.length >= maxContentSize) {
      window.alert(`게시글 글자는 ${maxContentSize}자를 넘을 수 없습니다`);
      article.value.content = article.value.content.substring(0, maxContentSize - 1);
    }
  }
);

watch(
  () => article.value.title,
  (newValue) => {
    if (newValue.length >= maxTitleSize) {
      window.alert(`게시글 글자는 ${maxTitleSize}자를 넘을 수 없습니다`);
      article.value.title = article.value.title.substring(0, maxTitleSize - 1);
    }
  }
);

const selectedCourse = ref({});

const setCourse = (course) => {
  selectedCourse.value = course;
  article.value.courseId = course.id;
};

const articleno = route.params?.article;
onMounted(() => {
  if (type === "modify") {
    getArticleDetail(articleno).then((response) => {
      if (store.loginId != response.data.article.authorLoginId) {
        window.alert("권한이 없습니다");
        router.push({ name: "boardList" });
      } else {
        console.log(response);
        article.value.title = response.data.article.title;
        article.value.content = response.data.article.content;
        article.value.thumbnail = response.data.article.thumbnail;
        setCourse(response.data.course);
      }
    });
  }
});

const cancel = () => {
  if (type === "write") {
    router.push({
      name: "boardList",
      query: {
        pgno: 1,
        word: "",
      },
    });
  } else if (type === "modify") {
    router.push({
      name: "boardDetail",
      query: {
        article: 1,
      },
    });
  }
};

const triggerInput = () => {
  document.querySelector("#image").click();
};

const getImage = (event) => {
  const file = getFileDataFromInput(event);
  if (!isFileSizeOk(file)) {
    window.alert("8MB 이하의 파일만 업로드 해주세요");
    return;
  }
  imgName.value = getFileName(file);
  encodeImageToBase64(file).then((response) => {
    article.value.thumbnail = response;
  });
};

const imgName = ref(null);

const regist = () => {
  if (!isValid.value) {
    toast.error("게시물 등록 에러 발생");
    return;
  }

  if (type === "write") {
    // 서버로 post api 요청
    // 요청 전 유효성 검사 필요
    registArticle(store.loginId, article.value)
      .then(() => {
        window.alert("작성 완료");
        router.push({
          name: "boardList",
          query: {
            pgno: 1,
            word: "",
          },
        });
      })
      .catch(() => {
        toast.error("게시물 등록 에러 발생");
      });
  } else if (type === "modify") {
    // 서버로 put api 요청
    updateArticle(articleno, store.loginId, article.value)
      .then(() => {
        window.alert("작성 완료");
        router.push({
          name: "boardDetail",
          query: {
            article: articleno,
          },
        });
      })
      .catch(() => {
        toast.error("게시물 등록 에러 발생");
      });
  }
};

// const isContentExist = computed(() => {
//   return article.value.title.length > 0 || article.value.content.length > 0;
// });

// onBeforeRouteLeave(() => {
//   if (isContentExist.value) {
//     const move = window.confirm(
//       "작성중인 내용이 있습니다. 이동할 경우 작성중인 내용이 사라집니다. 계속하시겠습니까?"
//     );
//     if (move) {
//       return true;
//     } else {
//       return false;
//     }
//   } else {
//     return true;
//   }
// });
</script>

<template>
  <BoardArticleFormPlanSelectModal
    v-show="isModalSeen"
    @closeModal="isModalSeen = false"
    @courseSelected="setCourse"
  />
  <div
    class="w-[54rem] h-[35rem] shadow-sm bg-white rounded-2xl flex flex-col justify-start items-center font-kor text-gray-800 font-kor"
  >
    <div class="w-[54rem] h-[7rem] bg-white flex flex-col justify-center items-center shadow-md">
      <!-- 타이틀 -->
      <p class="font-bold text-xl font-gray-700 ml-4 mb-3">글작성</p>
      <input
        class="title bg-gray-100 border border-gray-300 p-2 w-[50rem] mb-4 outline-none rounded-md focus:border-2 focus:border-trip-color transition-all focus:outline-0"
        spellcheck="false"
        placeholder="제목"
        type="text"
        v-model="article.title"
      />
    </div>
    <div class="w-[52rem] h-[25rem] flex flex-col items-center justify-center gap-1">
      <!-- 본문 -->

      <textarea
        class="description whitespace-pre bg-gray-100 sec p-3 w-[50rem] h-[20rem] border border-gray-300 outline-none rounded-md transition-all focus:border-2 focus:border-trip-color focus:outline-0"
        spellcheck="false"
        placeholder="본문"
        v-model="article.content"
      ></textarea>
      <div
        class="w-[50rem] h-[2rem] flex flex-row items-center justify-start rounded-md border gap-2"
      >
        <VButton class="ml-2" color="gray" title="이미지 선택" @click="triggerInput" />
        <input
          type="file"
          id="image"
          accept="image/png, image/jpeg"
          class="hidden"
          @input="getImage"
        />
        <p class="font-kor text-gray-700">
          이미지 등록 :
          {{ imgName ? imgName : "8MB 이하의 이미지를 등록해주세요" }}
        </p>
      </div>
      <div
        class="w-[50rem] h-[2rem] flex flex-row items-center justify-start rounded-md border gap-2"
      >
        <VButton class="ml-2" color="gray" title="코스 선택" @click="isModalSeen = true" />
        <p class="font-kor text-gray-700">
          선택된 코스 제목:
          {{ selectedCourse.title ? selectedCourse.title : "반드시 코스를 선택해주세요" }}
        </p>
      </div>
    </div>
    <div class="w-[54rem] h-[3rem] bg-white flex flex-row items-center justify-end">
      <div class="mr-3 flex flex-row items-center justify-end gap-2">
        <VButton color="red" title="취소" @click="cancel" />
        <VButton color="sky" title="등록" @click="regist" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
