import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/view/HomeView.vue";

import BoardView from "@/view/BoardView.vue";
import BoardList from "@/components/board/BoardList.vue";
import BoardDetail from "@/components/board/BoardDetail.vue";
import BoardWrite from "@/components/board/BoardWrite.vue";
import BoardUpdate from "@/components/board/BoardUpdate.vue";

import TripSearchView from "@/view/TripSearchView.vue";
import TripSpotSearch from "@/components/search/TripSpotSearch.vue";

import UserLoginView from "@/view/UserLoginView.vue";
import UserLogin from "@/components/login/UserLogin.vue";
import UserRegister from "@/components/login/UserRegister.vue";

import UserPageView from "@/view/UserPageView.vue";
import UserPageInfo from "@/components/userpage/info/UserPageInfo.vue";
import UserInfoModify from "@/components/userpage/info/UserInfoModify.vue";
import UserArticleList from "@/components/userpage/article/UserArticleList.vue";
import UserSavedTripList from "@/components/userpage/UserSavedTripList.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/board",
      name: "board",
      component: BoardView,
      redirect: { name: "boardList" },
      children: [
        {
          path: "",
          name: "boardList",
          component: BoardList,
        },
        {
          path: "detail/:article",
          name: "boardDetail",
          component: BoardDetail,
        },
        {
          path: "update/:article",
          name: "boardUpdate",
          component: BoardUpdate,
        },
        {
          path: "write",
          name: "boardWrite",
          component: BoardWrite,
        },
      ],
    },
    {
      path: "/search",
      name: "search",
      component: TripSearchView,
      redirect: { name: "mapSearch" },
      children: [
        {
          path: "",
          name: "mapSearch",
          component: TripSpotSearch,
        },
      ],
    },
    {
      path: "/login",
      name: "login",
      component: UserLoginView,
      redirect: { name: "userLogin" },
      children: [
        {
          path: "",
          name: "userLogin",
          component: UserLogin,
        },
        {
          path: "register",
          name: "userRegister",
          component: UserRegister,
        },
      ],
    },
    {
      path: "/userpage",
      name: "userPage",
      component: UserPageView,
      redirect: { name: "userInfo" },
      children: [
        {
          path: "",
          name: "userInfo",
          component: UserPageInfo,
        },
        {
          path: "articles",
          name: "userArticle",
          component: UserArticleList,
        },
        {
          path: "triplists",
          name: "userTripList",
          component: UserSavedTripList,
        },
        {
          path: "modify",
          name: "userModify",
          component: UserInfoModify,
        },
      ],
    },
  ],
});

export default router;
