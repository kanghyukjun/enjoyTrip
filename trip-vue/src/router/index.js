import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/view/HomeView.vue";
import BoardView from "@/view/BoardView.vue";
import TripSearchView from "@/view/TripSearchView.vue";

import UserLoginView from "@/view/UserLoginView.vue";
import UserLogin from "@/components/login/UserLogin.vue";
import UserRegister from "@/components/login/UserRegister.vue";

import UserPageView from "@/view/UserPageView.vue";

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
    },
    {
      path: "/search",
      name: "search",
      component: TripSearchView,
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
    },
  ],
});

export default router;
