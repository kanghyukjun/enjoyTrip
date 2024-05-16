import "@/assets/style.css";
import "@/assets/font.css";
import "@/assets/scroll-bar.css";

import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";
import { useKakao } from "vue3-kakao-maps/@utils";

const { VITE_KAKAO_OPEN_API_KEY } = import.meta.env;

useKakao(VITE_KAKAO_OPEN_API_KEY);
const app = createApp(App);

app.use(createPinia());
app.use(router);

app.mount("#app");
