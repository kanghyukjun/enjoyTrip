<script setup>
import SideNavBarListItem from "./SideNavBarListItem.vue";

import { useUserStore } from "@/stores/login";

const store = useUserStore();

const logout = () => {
  // server와 연결해서 token과 session, database 관련 정보 삭제 요청 후 홈으로 이동
  // 현재 store로 판단을 하고 있기 때문에 차이 발생 가능
  store.logout();
  window.alert("로그아웃 되었습니다");
};
</script>

<template>
  <ul class="ml-4 h-full">
    <div class="h-5/6 flex flex-col justify-center">
      <SideNavBarListItem name="home" title="홈" imgSrc="/src/assets/icon/home.svg" />
      <SideNavBarListItem name="search" title="여행 경로" imgSrc="/src/assets/icon/search.svg" />
      <SideNavBarListItem
        name="board"
        :query="{ pgno: 1, word: '' }"
        title="여행 게시판"
        imgSrc="/src/assets/icon/board.svg"
      />
      <SideNavBarListItem
        name="userPage"
        title="마이페이지"
        imgSrc="/src/assets/icon/account.svg"
      />
    </div>
    <div class="h-1/6">
      <SideNavBarListItem
        v-if="!store.isLogined"
        name="userLogin"
        title="로그인"
        imgSrc="/src/assets/icon/login.svg"
      />
      <SideNavBarListItem
        v-else
        name="home"
        title="로그아웃"
        imgSrc="/src/assets/icon/logout.svg"
        @click="logout"
      />
    </div>
  </ul>
</template>

<style scoped></style>
