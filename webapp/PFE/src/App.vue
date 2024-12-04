<script setup>
import { RouterView } from 'vue-router';
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
</script>

<template>
  <Header />

  <main>
    <RouterView />
  </main>

  <Footer />
</template>

<style scoped>

html, body {
  height: 100%;
}

.content {
  flex: 0; /* This ensures the content section expands to take available space */
}

header,
footer {
  position: fixed;
  width: 100%;
  z-index: 10000;
}

footer {
  bottom: 0;
}
</style>
