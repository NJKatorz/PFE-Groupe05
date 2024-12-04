<template>
  <div class="footer">
    <div class="footer-container">
      <div class="footer-content">
        <p class="footer-text">ESG & ODD Évaluation © 2023 Shifting Pact. Tous droits réservés.</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Footer',
};
</script>

<style scoped>
.footer {
  background: linear-gradient(to right, #002E37, #40867A);
  height: 72px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.footer-container {
  width: 100%;
  display: flex;
  justify-content: center;
}

.footer-content {
  text-align: center;
}

.footer-text {
  font-size: 14px;
  color: white;
  margin: 0;
}
</style>
