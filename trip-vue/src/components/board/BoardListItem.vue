<script setup>
import { useRouter } from "vue-router";

const router = useRouter();

const moveDetail = () => {
  router.push({
    name: "board-detail",
    params: {
      article: 1,
    },
  });
};
</script>

<template>
  <tbody>
    <tr class="h-[3rem] bg-white border-b hover:bg-gray-50 text-gray-700 cursor-default">
      <td class="text-center">1</td>
      <td class="cursor-pointer" @click="moveDetail">
        The Sliding Mr. Bones (Next Stop, Pottersville)
      </td>
      <td>Malcolm Lockyer</td>
      <td class="text-center">1961</td>
      <td class="text-center">32</td>
    </tr>
  </tbody>
</template>

<style scoped></style>
