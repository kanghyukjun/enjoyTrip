<script setup>
defineProps({
  title: String,
  color: String,
});
</script>

<template>
  <button
    class="middle none center rounded-lg text-white py-1 px-2 font-sans text-xs font-bold uppercase shadow-sm transition-all hover:shadow-md focus:opacity-[0.85] focus:shadow-none active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
    data-ripple-light="true"
    :class="{
      'bg-trip-color': color === 'trip',
      'shadow-trip-color': color === 'trip',
      'hover:shadow-trip-color': color === 'trip',
      'bg-red-500': color === 'red',
      'shadow-red-500': color === 'red',
      'hover:shadow-red-500': color === 'red',
      'bg-sky-500': color === 'sky',
      'shadow-sky-500': color === 'sky',
      'hover:shadow-sky-500': color === 'sky',
      'bg-gray-500': color === 'gray',
      'shadow-gray-500': color === 'gray',
      'hover:shadow-gray-500': color === 'gray',
      'bg-yellow-500': color === 'yellow',
      'shadow-yellow-500': color === 'yellow',
      'hover:shadow-yellow-500': color === 'yellow',
    }"
  >
    {{ title }}
  </button>
</template>

<style scoped></style>
