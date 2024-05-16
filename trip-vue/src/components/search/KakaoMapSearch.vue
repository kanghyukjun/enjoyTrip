<script setup>
import { onMounted } from "vue";
// import { getSido } from "@/api/search";

// import { KakaoMap, KakaoMapMarker } from "vue3-kakao-maps";
// const coordinate = {
//   lat: 37.566826,
//   lng: 126.9786567,
// };

// console.log(
//   getSido(
//     async (response) => {
//       await console.log(response);
//     },
//     async (error) => {
//       await console.log(error);
//     }
//   )
// );

onMounted(() => {
  const container = document.querySelector("#container");
  console.log(container);
  console.log(container.offsetWidth);
  console.log(container.offsetHeight);
});
</script>

<template>
  <div class="flex flex-row border-none w-full h-full">
    <div id="container" class="w-full h-full">
      <!-- <KakaoMap
        width="70rem"
        height="45.5rem"
        :lat="coordinate.lat"
        :lng="coordinate.lng"
        :draggable="true"
      >
        <KakaoMapMarker :lat="coordinate.lat" :lng="coordinate.lng"></KakaoMapMarker>
      </KakaoMap> -->
    </div>
    <div class="w-52 bg-zinc-600">side</div>
  </div>
</template>

<style scoped></style>
