<script setup>
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import { articleStore } from "@/stores/article";
import { registArticle } from "@/api/board";

import { toast } from "vue3-toastify";
import "vue3-toastify/dist/index.css";

import VButton from "@/components/common/item/VButton.vue";
import BoardArticleFormPlanSelectModal from "./BoardArticleFormPlanSelectModal.vue";

const props = defineProps({
  type: String,
});

const type = props.type;

const route = useRoute();
const router = useRouter();
const store = articleStore();

const isModalSeen = ref(false);

const article = ref({
  title: "",
  content: "",
  thumbnail: "",
  courseId: -1,
});

const selectedCourse = ref({});

const setCourse = (course) => {
  selectedCourse.value = course;
  article.value.courseId = course.id;
};

const articleno = route.params?.article;
onMounted(() => {
  if (articleno) {
    const get = store.getArticle(articleno);
    article.value = get;
  }
});

// const isContentExist = computed(() => {
//   return article.value.title.length > 0 || article.value.content.length > 0;
// });

const cancel = () => {
  if (type === "write") {
    router.push({ name: "boardList" });
  } else if (type === "modify") {
    router.push({
      name: "boardDetail",
      query: {
        article: 1,
      },
    });
  }
};

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

const regist = () => {
  if (type === "write") {
    // 서버로 post api 요청
    // 요청 전 유효성 검사 필요
    registArticle(article.value)
      .then(() => {
        window.alert("작성 완료");
        router.push({
          name: "boardList",
          query: {
            pgno: 1,
            keyword: "",
          },
        });
      })
      .catch(() => {
        toast.error("게시물 등록 에러 발생");
      });
  } else if (type === "modify") {
    // 서버로 put api 요청
  }
  // 리스트로 이동
};
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
        class="description bg-gray-100 sec p-3 w-[50rem] h-[20rem] border border-gray-300 outline-none rounded-md transition-all focus:border-2 focus:border-trip-color focus:outline-0"
        spellcheck="false"
        placeholder="본문"
        v-model="article.content"
      ></textarea>
      <div
        class="w-[50rem] h-[2rem] flex flex-row items-center justify-start rounded-md border gap-2"
      >
        <VButton class="ml-2" color="gray" title="이미지 선택" />
        <p class="font-kor text-gray-700">
          이미지 등록 :
          {{ image ? image : "등록된 이미지가 없습니다" }}
        </p>
      </div>
      <div
        class="w-[50rem] h-[2rem] flex flex-row items-center justify-start rounded-md border gap-2"
      >
        <VButton class="ml-2" color="gray" title="코스 선택" @click="isModalSeen = true" />
        <p class="font-kor text-gray-700">
          선택된 코스 제목:
          {{ selectedCourse.title ? selectedCourse.title : "코스를 선택해주세요" }}
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
