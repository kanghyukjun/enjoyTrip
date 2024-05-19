<script setup>
defineProps({
  userBoard: Object,
});
</script>

<template>
    <div class="da relative flex flex-row justify-center overflow-hidden bg-gray-50">
      <div class="bg-center dark:bg-black"></div>
      <div
        class="group relative m-0 flex rounded-xl shadow-xl ring-gray-900/5 sm:mx-auto sm:max-w-lg"
      >
        <div
          class="z-10 h-full w-full overflow-hidden rounded-xl border border-gray-200 opacity-80 transition duration-300 ease-in-out group-hover:opacity-100 dark:border-gray-700 dark:opacity-70"
        >
          <img
            :src="userBoard.boardThumbnail"
            class="animate-fade-in block h-full w-full scale-100 transform object-cover object-center opacity-100 transition duration-300 group-hover:scale-110"
            alt=""
          />
        </div>
        <div
          class="absolute bottom-0 z-20 m-0 pb-4 ps-4 transition duration-300 ease-in-out group-hover:-translate-y-1 group-hover:translate-x-3 group-hover:scale-110"
        >
          <h1 class="font-serif text-2xl font-bold text-white shadow-xl">{{ userBoard.boardTitle }}</h1>
          <h1 class="text-sm font-light text-gray-200 shadow-xl">강혁준</h1>
        </div>
      </div>
    </div>
</template>

<style scoped></style>
