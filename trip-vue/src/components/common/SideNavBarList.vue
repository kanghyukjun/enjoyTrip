<script setup>
import SideNavBarListItem from "./SideNavBarListItem.vue";
</script>

<template>
  <ul class="ml-4 h-full">
    <div class="h-5/6 flex flex-col justify-center">
      <SideNavBarListItem name="home" title="홈" imgSrc="./src/assets/icon/home.svg" />
      <SideNavBarListItem name="search" title="여행 경로" imgSrc="./src/assets/icon/search.svg" />
      <SideNavBarListItem name="board" title="여행 게시판" imgSrc="./src/assets/icon/board.svg" />
      <SideNavBarListItem
        name="userpage"
        :params="{ id: 1 }"
        title="마이페이지"
        imgSrc="./src/assets/icon/account.svg"
      />
    </div>
    <div class="h-1/6">
      <SideNavBarListItem name="login" title="로그인" imgSrc="./src/assets/icon/login.svg" />
    </div>
  </ul>
</template>

<style scoped></style>
