<script setup>
import ArticleListItem from "@/components/userpage/ArticleListItem.vue";
</script>

<template>
  <div class="w-full h-full flex flex-col items-center justify-center">
    <div class="w-5/6 h-[3rem] flex flex-row items-center justify-start font-kor">
      <p class="ml-3 text-gray-700 font-bold text-2xl mt-10">내 여행 코스</p>
    </div>
    <div class="w-5/6 h-full flex flex-col items-center justify-center font-kor transition-all">
      <div
        class="w-11/12 h-5/6 flex flex-col items-center justify-start rounded-md overflow-y-auto"
      >
        <!-- <UserSavedTripListItem
          class="mt-3"
          v-for="course in userCourses"
          :key="course.id"
          :course="course"
          @click="moveDetail(course.id)"
        /> -->
        <ArticleListItem />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
