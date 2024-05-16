<script setup>
import { onMounted, ref } from "vue";
// import { getSido } from "@/api/search";

import { KakaoMap, KakaoMapMarker } from "vue3-kakao-maps";
const coordinate = {
  lat: 37.566826,
  lng: 126.9786567,
};

const mapWidth = ref(0);
const mapHeight = ref(0);
const isLoaded = ref(false);

onMounted(() => {
  const container = document.querySelector("#container");
  // console.log(container);
  // console.log(container.offsetWidth);
  // console.log(container.offsetHeight);
  mapWidth.value = container.offsetWidth * 0.95;
  mapHeight.value = container.offsetHeight * 0.95;
  isLoaded.value = true;
});
</script>

<template>
  <div class="flex flex-row border-none w-full h-full">
    <div id="container" class="w-full h-full flex justify-center items-center">
      <template v-if="isLoaded">
        <KakaoMap
          :width="mapWidth + 'px'"
          :height="mapHeight + 'px'"
          :lat="coordinate.lat"
          :lng="coordinate.lng"
          :draggable="true"
        >
          <KakaoMapMarker :lat="coordinate.lat" :lng="coordinate.lng"></KakaoMapMarker>
        </KakaoMap>
      </template>
    </div>
    <div class="w-[18rem] bg-zinc-600">side</div>
  </div>
</template>

<style scoped></style>
