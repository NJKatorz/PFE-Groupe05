<template>
  <div class="header">
    <div class="logo-container">
      <img class="logo" src="@/assets/logo.png" alt="Logo" />
    </div>
  </div>
</template>

<script>
export default {
  name: 'Header',
};
</script>

<style scoped>
.header {
  background: linear-gradient(to right, #002E37, #40867A);
  display: block;
  height: 125px;
  position: relative;
}

.logo-container {
  position: absolute;
  left: 25px;
  top: 22px;
}

.logo {
  width: 242px;
  height: 97.87px;
}
</style>
