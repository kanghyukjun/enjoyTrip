<script setup>
import VButton from "@/components/common/item/VButton.vue";

import { onMounted, ref } from "vue";

const props = defineProps({
  spot: Object,
});

const emit = defineEmits(["addSpot"]);

const itemTitle = ref(props.spot.title);
const itemAddr = ref(props.spot.addr);

onMounted(() => {
  const maxTitleLength = 5;
  const maxAddrLength = 16;
  if (itemTitle.value.length > maxTitleLength) {
    itemTitle.value = itemTitle.value.substr(0, maxTitleLength);
    itemTitle.value += "...";
  }

  if (itemAddr.value.length > maxAddrLength) {
    itemAddr.value = itemAddr.value.substr(0, maxAddrLength);
    itemAddr.value += "...";
  }
});

// button
const addSpot = (spot) => {
  emit("addSpot", spot);
};
</script>

<template>
  <div
    class="w-[20rem] h-[8rem] rounded-md bg-zinc-100 flex flex-row items-center justify-center border-2 border-zinc-200 hover:border-4 hover:border-zinc-300 transition-all mb-3"
  >
    <div class="w-[18rem] h-[6rem] flex flex-row items-center justify-center gap-3">
      <div class="w-[10.5rem] h-[6rem]">
        <div class="w-[10.5rem] h-[6rem] flex flex-col justify-between items-start">
          <div>
            <span class="font-kor text-gray-700 text-xl">{{ itemTitle }}</span>
            <span class="font-kor text-gray-700 text-xs ml-1">{{ spot.type }}</span>
            <p class="font-kor text-gray-600 text-xs">{{ itemAddr }}</p>
          </div>
          <VButton label="추가" color="sky" @click="addSpot(spot)" />
        </div>
      </div>
      <div class="w-[6.5rem] h-[6rem]">
        <img
          :src="spot.img ? spot.img : '/src/assets/no-image.png'"
          class="rounded-md w-[6.5rem] h-[6rem]"
        />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
