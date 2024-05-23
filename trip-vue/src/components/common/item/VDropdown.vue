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
