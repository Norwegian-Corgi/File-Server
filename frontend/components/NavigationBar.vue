<template>
  <header class="navigation-bar">
    <h1>File Server</h1>
    <p class="clickable" @click="logout">Logout</p>
  </header>
</template>

<script setup lang="ts">
const { logout } = useKeycloak();
</script>

<style scoped>
.navigation-bar {
  display: flex;
  background-color: rgb(84, 126, 215);
  height: 4rem;
  justify-content: space-between;
  align-items: center;
  padding: 0 2rem;
}
</style>
