<script setup>
import draggable from "vuedraggable";
import { ref } from "vue";

import SelectedSpotListItem from "@/components/search/selected/SelectedSpotListItem.vue";
import VButton from "@/components/common/item/VButton.vue";

const isClosed = ref(false);

const list = ref([]);

const addSpot = (spot) => {
  list.value.push(spot);
};

const deleteSpot = (spot) => {
  list.value = list.value.filter((x) => x.id !== spot.id);
};

const makeTravelPlan = () => {
  if (list.value.length <= 0) {
    window.alert("선택한 여행지 목록이 존재하지 않습니다");
  }
};

defineExpose({
  addSpot,
});
</script>

<template>
  <div class="w-[18rem] h-full flex flex-row justify-start items-center">
    <div
      class="h-full flex flex-row items-center justify-center bg-zinc-100 border border-zinc-200 hover:border-2 hover:border-zinc-300 opacity-100 transition-all"
      :class="{ 'w-[0rem]': isClosed, 'w-[16rem]': !isClosed }"
    >
      <div
        class="w-[15rem] h-full flex flex-col items-center justify-center font-kor text-gray-700"
        v-show="!isClosed"
      >
        <div class="w-[15rem] h-1/6 flex flex-col items-start justify-start">
          <p class="ml-3 mt-1 font-bold text-lg">추가한 여행지 리스트</p>
          <p class="ml-3 text-xs">지금까지 추가한 여행지 리스트들을 확인할 수 있습니다</p>
          <div class="w-[15rem] mt-3 flex flex-row items-center justify-end">
            <VButton class="mr-4" title="여행 계획 생성" color="red" @click="makeTravelPlan" />
          </div>
          <div class="flex justify-center mt-4 items-center w-[14rem]">
            <div class="flex-grow border-t border-gray-300"></div>
          </div>
        </div>
        <div class="w-[15rem] h-5/6 flex flex-col items-center justify-start gap-3 overflow-y-auto">
          <div
            class="w-[14rem] h-5/6 transition-all"
            :class="{ 'opacity-100': !isClosed, 'opacity-0': isClosed }"
          >
            <draggable
              :list="list"
              tag="SelectedSpotListItem"
              group="people"
              @start="drag = true"
              @end="drag = false"
              item-key="id"
              animation="150"
              :move="changeList"
            >
              <template #item="{ element }">
                <div>
                  <SelectedSpotListItem :spot="element" @deleteSpot="deleteSpot" />
                </div>
              </template>
            </draggable>
          </div>
        </div>
      </div>
    </div>
    <div class="w-[2rem] h-full flex flex-col items-center justify-start transition-all">
      <button @click="isClosed = !isClosed">
        <img
          src="/src/assets/arrow-red.png"
          class="ml-1 mt-4"
          :class="{ 'rotate-180': isClosed }"
        />
      </button>
    </div>
  </div>
</template>

<style scoped></style>
