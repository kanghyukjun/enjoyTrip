<script setup>
defineProps({
  label: String,
});

const emit = defineEmits(["input"]);
const inputChange = (event) => {
  emit("input", event.target.value);
};
</script>

<template>
  <div class="w-[auto] flex flex-col items-start justify-center">
    <label :for="label" class="font-kor text-gray-700">{{ label }}</label>
    <input
      :id="label"
      type="date"
      class="w-[2/5] bg-tansparent font-kor text-gray-700 rounded"
      @input="inputChange"
    />
  </div>
</template>

<style scoped></style>
