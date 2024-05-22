<script setup>
const props = defineProps({
  title: String,
  items: Array,
  width: Number,
});

const emit = defineEmits(["changed"]);
const width = "w-[" + props.width + "rem]";

const click = (event) => {
  const code = event.target.value;
  emit("changed", code);
};

// 만약 title=='시/도 선택'이고 onMounted시 queryString에 sido code에 대한 내용이 있고 items가 주어진다면
// select option의 select value를 해당 index로 바꾸고
// 이벤트를 emit한다
</script>

<template>
  <div>
    <div :class="width">
      <select
        id="menu"
        class="border border-gray-300 rounded-md text-gray-600 h-8 pl-2 bg-white hover:border-gray-400 focus:outline-none appearance-none font-kor"
        :class="width"
        @change="click"
      >
        <option class="font-kor" default value="0">{{ title }}</option>
        <option class="font-kor" v-for="item in items" :key="item.id" :value="item.id">
          {{ item.name }}
        </option>
      </select>
    </div>
  </div>
</template>

<style scoped></style>
