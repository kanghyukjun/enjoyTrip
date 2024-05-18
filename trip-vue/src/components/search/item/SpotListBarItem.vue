<script setup>
import { onMounted, ref } from "vue";

import VButton from "@/components/common/item/VButton.vue";

const emit = defineEmits(["deleteSpot"]);

const props = defineProps({
  spot: Object,
});

const itemTitle = ref(props.spot.title);

onMounted(() => {
  const maxTitleLength = 14;
  if (itemTitle.value.length > maxTitleLength) {
    itemTitle.value = itemTitle.value.substr(0, maxTitleLength);
    itemTitle.value += "...";
  }
});

const deleteSpot = (spot) => {
  emit("deleteSpot", spot);
};
</script>

<template>
  <div
    class="w-[14rem] h-[6rem] rounded-md border-2 flex flex-row items-center justify-center border-zinc-200 hover:border-4 hover:border-zinc-300 transition-all mb-3 gap-2"
  >
    <div class="w-[6rem] h-[5rem] flex flex-col justify-start items-start font-kor text-gray-600">
      <div class="w-[6rem] h-[3.5rem] flex flex-row">
        {{ itemTitle }}
      </div>
      <div class="w-[6rem] h-[1.5rem] flex flex-row">
        <VButton title="삭제" color="gray" @click="deleteSpot(spot)" />
      </div>
    </div>
    <div class="w-[6rem] h-[5rem]"></div>
  </div>
</template>

<style scoped></style>
