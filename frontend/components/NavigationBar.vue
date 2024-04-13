<template>
  <header class="h-16 flex justify-between items-center px-8 bg-primary">
    <h1>File Server</h1>
    <p class="cursor-pointer" @click="logout">Logout</p>
  </header>
</template>

<script setup lang="ts">
const { logout } = useKeycloak();
</script>
