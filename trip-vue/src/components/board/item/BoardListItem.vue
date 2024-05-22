<script setup>
defineProps({
  article: Object,
});
</script>

<template>
  <tbody class="w-full">
    <tr class="h-[3rem] bg-white border-b hover:bg-gray-50 text-gray-700 cursor-default text-kor">
      <td class="text-center">{{ article.id }}</td>
      <td class="cursor-pointer">
        {{ article.title }}
      </td>
      <td>{{ article.author }}</td>
      <td class="text-center">{{ article.hit }}</td>
      <td class="text-center">{{ article.liked }}</td>
    </tr>
  </tbody>
</template>

<style scoped></style>
