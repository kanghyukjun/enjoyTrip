<script setup>
import BoardListItem from "@/components/board/BoardListItem.vue";
</script>

<template>
  <div
    class="w-full h-full flex flex-col bg-zinc-100 justify-start items-center border-lg shadow-md"
  >
    <div class="w-[54rem] mt-10 mb-10 flex flex-col justify-start">
      <p class="font-bold text-3xl font-kor text-gray-600">게시판</p>
      <p class="font-bold text-lg font-kor text-gray-600">
        여행지에 대한 의견을 자유롭게 나누어보세요
      </p>
    </div>
    <div>검색창</div>
    <table class="table-fixed rounded-2xl">
      <thead class="text-sm text-gray-100 uppercase bg-trip-color font-kor">
        <tr class="h-[3rem]">
          <th class="w-[3rem] text-center">no</th>
          <th class="w-[27rem]">제목</th>
          <th class="w-[14rem]">작성자</th>
          <th class="w-[5rem] text-center">조회수</th>
          <th class="w-[5rem] text-center">좋아요</th>
        </tr>
      </thead>
      <BoardListItem />
      <BoardListItem />
      <BoardListItem />
      <BoardListItem />
    </table>
    <div>navigation bar</div>
  </div>
</template>

<style scoped></style>
