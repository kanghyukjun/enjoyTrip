<script setup>
defineProps({
  name: String,
  imgSrc: String,
});
</script>

<template>
  <li
    class="mb-2 px-6 py-4 font-kor text-zinc-100 flex flex-row border-none hover:text-zinc-600 hover:bg-gray-100 hover:font-bold rounded rounded-lg"
  >
    <span class="flex justify-center items-center">
      <!-- <svg class="fill-current h-5 w-5" viewBox="0 0 24 24">
        <path
          d="M16 20h4v-4h-4m0-2h4v-4h-4m-6-2h4V4h-4m6
                        4h4V4h-4m-6 10h4v-4h-4m-6 4h4v-4H4m0 10h4v-4H4m6
                        4h4v-4h-4M4 8h4V4H4v4z"
        ></path>
      </svg> -->
      <img :src="imgSrc" alt="noimg" />
    </span>
    <a href="#">
      <span class="ml-2">{{ name }}</span>
    </a>
  </li>
</template>

<style scoped></style>
