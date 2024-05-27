<script setup>
import { onMounted, ref } from "vue";
import UserSavedTripListItem from "@/components/userpage/trip-list/UserSavedTripListItem.vue";
import { useUserStore } from "@/stores/login";
import { getUserCourse } from "@/api/user";

const store = useUserStore();
const emit = defineEmits(["closeModal", "courseSelected"]);

const userCourses = ref([]);

onMounted(async () => {
  getUserCourse(store.loginId).then((response) => {
    userCourses.value = response.data.courses;
  });
});

const close = () => {
  emit("closeModal");
};

const courseSelected = (course) => {
  emit("courseSelected", course);
  close();
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
      class="w-[40rem] h-[35rem] flex flex-col items-center justify-center rounded-lg opacity-100 bg-zinc-100 z-30 font-kor"
    >
      <div class="w-[40rem] h-[3rem] flex items-center justify-between">
        <p class="ml-2 font-bold text-2xl font-kor text-gray-700">여행 코스 선택</p>
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
      <div class="relative flex items-center w-[38rem]">
        <div class="flex-grow border-t border-gray-300"></div>
      </div>
      <div class="w-[40rem] h-[30rem] flex flex-col items-center justify-center">
        <div
          class="w-5/6 h-5/6 flex flex-col items-center justify-start rounded-md border-gray-300 border-2 overflow-y-auto"
        >
          <UserSavedTripListItem
            class="mt-3"
            v-for="course in userCourses"
            :key="course.id"
            :course="course"
            @click="courseSelected(course)"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
