<script setup>
import { KakaoMap, KakaoMapMarker } from "vue3-kakao-maps";
const coordinate = {
  lat: 37.566826,
  lng: 126.9786567,
};
</script>

<template>
  <div class="flex flex-row border-none w-full h-full">
    <div class="w-[70rem] h-[45.5rem]">
      <KakaoMap
        width="70rem"
        height="45.5rem"
        :lat="coordinate.lat"
        :lng="coordinate.lng"
        :draggable="true"
      >
        <KakaoMapMarker :lat="coordinate.lat" :lng="coordinate.lng"></KakaoMapMarker>
      </KakaoMap>
    </div>
    <div class="w-52 bg-zinc-600">side</div>
  </div>
</template>

<style scoped></style>
