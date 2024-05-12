<script setup>
import UserPageSideNavBarListItem from "@/components/userpage/navbar/UserPageSideNavBarListItem.vue";
</script>

<template>
  <ul class="ml-4 h-full">
    <div class="flex flex-col justify-center items-start gap-2 font-kor">
      <li class="mt-4">
        <p class="font-bold">계정</p>
      </li>
      <UserPageSideNavBarListItem name="userinfo" title="내 정보" />
      <UserPageSideNavBarListItem name="usertriplist" title="내 여행 코스" />

      <li class="mt-4">
        <p class="font-bold">게시판 관리</p>
      </li>
      <UserPageSideNavBarListItem name="userarticle" title="내가 작성한 글" />
      <UserPageSideNavBarListItem name="userarticle" title="내가 찜한 글" />
    </div>
  </ul>
</template>

<style scoped></style>
