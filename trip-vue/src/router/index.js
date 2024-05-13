import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/view/HomeView.vue";

import BoardView from "@/view/BoardView.vue";
import BoardList from "@/components/board/BoardList.vue";

import TripSearchView from "@/view/TripSearchView.vue";
import KakaoMapSearch from "@/components/search/KakaoMapSearch.vue";

import UserLoginView from "@/view/UserLoginView.vue";
import UserLogin from "@/components/login/UserLogin.vue";
import UserRegister from "@/components/login/UserRegister.vue";

import UserPageView from "@/view/UserPageView.vue";
import UserPageInfo from "@/components/userpage/UserPageInfo.vue";
import UserPageInfoModify from "@/components/userpage/UserPageInfoModify.vue";
import UserArticleList from "@/components/userpage/UserArticleList.vue";
import UserTripList from "@/components/userpage/UserTripList.vue";

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
      redirect: { name: "board-list" },
      children: [
        {
          path: "",
          name: "board-list",
          component: BoardList,
        },
      ],
    },
    {
      path: "/search",
      name: "search",
      component: TripSearchView,
      redirect: { name: "map" },
      children: [
        {
          path: "",
          name: "map",
          component: KakaoMapSearch,
        },
      ],
    },
    {
      path: "/login",
      name: "login",
      component: UserLoginView,
      redirect: { name: "userlogin" },
      children: [
        {
          path: "",
          name: "userlogin",
          component: UserLogin,
        },
        {
          path: "register",
          name: "register",
          component: UserRegister,
        },
      ],
    },
    {
      path: "/userpage",
      name: "userpage",
      component: UserPageView,
      redirect: { name: "userinfo" },
      children: [
        {
          path: "",
          name: "userinfo",
          component: UserPageInfo,
        },
        {
          path: "articles",
          name: "userarticle",
          component: UserArticleList,
        },
        {
          path: "triplists",
          name: "usertriplist",
          component: UserTripList,
        },
        {
          path: "modify",
          name: "modify",
          component: UserPageInfoModify,
        },
      ],
    },
  ],
});

export default router;
