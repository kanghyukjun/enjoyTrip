<script setup>
defineProps({
  pgno: String,
  selected: Boolean,
  hide: Boolean,
});
</script>

<template>
  <li :class="{ invisible: hide }">
    <a
      class="mx-1 flex h-9 w-9 items-center justify-center rounded-lg border border-blue-gray-100 bg-transparent p-0 text-sm text-blue-gray-500 transition duration-150 ease-in-out hover:bg-light-300"
      href="#"
      aria-label="Previous"
      :class="{
        'bg-trip-color': selected,
        'shadow-trip-color': selected,
        'text-white': selected,
        'shadow-md': selected,
        'border-trip-color': selected,
      }"
    >
      {{ pgno }}
    </a>
  </li>
</template>

<style scoped></style>
