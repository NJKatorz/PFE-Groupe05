<template>
  <header class="header">
    <div class="logo-container">
      <img src=../assets/logo.png alt="Shifting Pact Logo" class="logo" />
    </div>
  </header>
</template>

<script>
export default {
  name: 'HeaderPart'
};
</script>

<style scoped>
/* Styles spécifiques pour le header */
.header {
  background: linear-gradient(to right, #002E37, #40867A);
  height: 100px;
  position: relative;
}

.logo-container {
  position: absolute;
  left: 25px;
  top: 22px;
}

.logo {
  width: 180px;
  height: 70px;
}
</style>
