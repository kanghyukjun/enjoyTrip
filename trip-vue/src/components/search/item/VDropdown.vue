<script setup>
defineProps({
  title: String,
  items: Array,
});

const emit = defineEmits(["changed"]);

const click = (event) => {
  const code = event.target.value;
  emit("changed", code);
};
</script>

<template>
  <div>
    <div class="w-[10rem]">
      <select
        id="menu"
        class="border border-gray-300 rounded-md text-gray-600 h-8 pl-2 w-[10rem] bg-white hover:border-gray-400 focus:outline-none appearance-none font-kor"
        @change="click"
      >
        <option default value="0">{{ title }}</option>
        <option v-for="item in items" :key="item.id" :value="item.id">
          {{ item.name }}
        </option>
      </select>
    </div>
  </div>
</template>

<style scoped></style>
