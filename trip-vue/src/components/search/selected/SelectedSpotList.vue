<script setup>
import SelectedSpotListItem from "@/components/search/selected/SelectedSpotListItem.vue";
import VButton from "@/components/common/item/VButton.vue";
import VHorizontalLine from "@/components/common/item/VHorizontalLine.vue";

import { ref } from "vue";
import { useRouter } from "vue-router";
import draggable from "vuedraggable";

import { useUserStore } from "@/stores/login";

const router = useRouter();
const store = useUserStore();

const isClosed = ref(false);

const list = ref([]);

const addSpot = (spot) => {
  if (list.value.length >= 10) {
    // 길이가 기준을 넘었을 때
    return -1;
  } else if (isAlreadySelected(spot)) {
    // 이미 저장된 장소일 때
    return 0;
  } else {
    // 저장 성공
    list.value.push(spot);
    return 1;
  }
};

// validation
const isAlreadySelected = (spot) => {
  const idx = list.value.findIndex((x) => x.id === spot.id);
  if (idx === -1) return false;
  else return true;
};

// button
const deleteSpot = (spot) => {
  list.value = list.value.filter((x) => x.id !== spot.id);
};

const makeTravelPlan = () => {
  if (!store.isLogined) {
    window.alert("로그인이 필요한 서비스입니다");
    router.push({ name: "login" });
  } else if (list.value.length <= 0) {
    window.alert("선택한 여행지 목록이 존재하지 않습니다");
  } else {
    sessionStorage.setItem("tripPlan", JSON.stringify(list.value));
    router.push({ name: "tripPlan" });
  }
};

// 다른 component에서 addSpot 함수를 사용할 수 있게 하기 위함
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
            <VButton class="mr-4" label="여행 계획 생성" color="red" @click="makeTravelPlan" />
          </div>
          <VHorizontalLine />
        </div>
        <div class="w-[15rem] h-5/6 flex flex-col items-center justify-start gap-3 overflow-y-auto">
          <div
            class="w-[14rem] h-5/6 transition-all"
            :class="{ 'opacity-100': !isClosed, 'opacity-0': isClosed }"
          >
            <draggable
              :list="list"
              tag="SelectedSpotListItem"
              group="spotList"
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
