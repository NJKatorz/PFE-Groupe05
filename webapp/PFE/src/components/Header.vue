<template>
  <header class="header">
    <div class="logo-container">
      <router-link to="/">
        <img src="../assets/logo.png" alt="Shifting Pact Logo" class="logo" />
      </router-link>
    </div>
    <div class="auth-buttons">
      <router-link to="/login" class="connect-btn">Se connecter</router-link>
    </div>
  </header>
</template>

<script>
export default {
  name: 'HeaderPart'
};
</script>

<style scoped>
.header {
  background: linear-gradient(to right, #002E37, #40867A);
  height: 100px;
  position: relative;
  display: flex;
  justify-content: space-between;
  align-items: center;
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

.auth-buttons {
  position: absolute;
  right: 25px;
  top: 50%;
  transform: translateY(-50%);
}

.connect-btn {
  background-color: #40867A;
  color: white;
  padding: 10px 20px;
  border-radius: 5px;
  text-decoration: none;
  font-weight: 500;
  transition: background-color 0.3s ease;
}

.connect-btn:hover {
  background-color: #346d63;
}
</style>
