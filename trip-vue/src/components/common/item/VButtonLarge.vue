<script setup>
defineProps({
  label: String,
  color: String,
});
</script>

<template>
  <button
    class="block w-full bg-gray-600 shadow-gray-600 hover:shadow-gray-600 select-none rounded-lg py-3 px-6 text-center align-middle font-sans text-xs font-bold uppercase text-white shadow-md transition-all hover:shadow-lg focus:opacity-[0.85] focus:shadow-none active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
    :class="{
      'bg-trip-color': color === 'trip',
      'shadow-trip-color': color === 'trip',
      'hover:shadow-trip-color': color === 'trip',
    }"
    type="button"
    data-ripple-light="true"
  >
    {{ label }}
  </button>
</template>

<style scoped></style>
