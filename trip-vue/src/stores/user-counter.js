import { ref } from "vue";
import { defineStore } from "pinia";

export const useUserStore = defineStore("user", () => {
  const userBoardList = ref([
    {
      boardId: 1,
      hit: 100,
      boardTitle: "강혁준의 모험",
      boardRegisterTime: "2024-05-14",
      boardThumbnail: "/src/assets/board/햄.png",
      courseId: 1,
    },
    {
      boardId: 2,
      hit: 1000,
      boardTitle: "강태경의 모험",
      boardRegisterTime: "2024-05-14",
      boardThumbnail: "/src/assets/board/햄.png",
      courseId: 2,
    },
    {
      boardId: 3,
      hit: 100000,
      boardTitle: "하이쌤의 모험",
      boardRegisterTime: "2024-05-14",
      boardThumbnail: "/src/assets/board/햄.png",
      courseId: 3,
    },
    {
      boardId: 4,
      hit: 100000,
      boardTitle: "하이쌤의 모험",
      boardRegisterTime: "2024-05-14",
      boardThumbnail: "/src/assets/board/햄.png",
      courseId: 4,
    },
    {
      boardId: 5,
      hit: 100000,
      boardTitle: "하이쌤의 모험",
      boardRegisterTime: "2024-05-14",
      boardThumbnail: "/src/assets/board/햄.png",
      courseId: 3,
    },
    {
      boardId: 6,
      hit: 100000,
      boardTitle: "하이쌤의 모험",
      boardRegisterTime: "2024-05-14",
      boardThumbnail: "/src/assets/board/햄.png",
      courseId: 3,
    },
  ]);
  return { userBoardList };
});
