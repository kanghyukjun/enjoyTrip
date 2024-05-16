import { ref } from "vue";
import { defineStore } from "pinia";

export const articleStore = defineStore("article", () => {
  const articles = ref([
    {
      articleno: 1,
      title: "나다",
      content:
        "내가하이쌤이다내가하이쌤이다내가하이쌤이다내가하이쌤이다내가하이쌤이다내가하이쌤이다내가하이쌤이다내가하이쌤이다내가하이쌤이다내가하이쌤이다",
      user: "하이쌤",
      hit: 65,
      liked: 2,
    },
    {
      articleno: 2,
      title: "저를 아세요?",
      content:
        "홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍홍",

      user: "홍박사",
      hit: 162,
      liked: 13,
    },
    {
      articleno: 3,
      title: "인피케이다 ㄴ출격",
      content:
        "내가 알고리즘의 신이다내가 알고리즘의 신이다내가 알고리즘의 신이다내가 알고리즘의 신이다내가 알고리즘의 신이다내가 알고리즘의 신이다내가 알고리즘의 신이다내가 알고리즘의 신이다",
      user: "창기형",
      hit: 86,
      liked: 12,
    },
    {
      articleno: 4,
      title: "내가 프론트다",
      content: "프론트는 나한테 맡기라구",
      user: "태경",
      hit: 8733,
      liked: 1,
    },
    {
      articleno: 5,
      title: "난눈구",
      content: "짬뽕먹고시파",
      user: "짬뻥",
      hit: 1252,
      liked: 9,
    },
    {
      articleno: 6,
      title: "병서",
      content: "날 받들어라",
      user: "왕자님",
      hit: 34,
      liked: 2,
    },
    {
      articleno: 7,
      title: "상욱이는 공주다",
      content: "안녕하세요",
      user: "상욱",
      hit: 2363,
      liked: 3,
    },
    {
      articleno: 8,
      title: "누구세요",
      content: "나는 누구...?",
      user: "나",
      hit: 87,
      liked: 0,
    },
  ]);

  const getArticle = (articleno) => {
    const idx = articles.value.findIndex((x) => x.articleno == articleno);
    if (idx < articles.value.length) return articles.value[idx];
    return null;
  };

  return { articles, getArticle };
});
