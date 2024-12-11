<template>
  <header class="header">
    <div class="logo-container">
      <router-link to="/">
        <img src="../assets/logo.png" alt="Shifting Pact Logo" class="logo" />
      </router-link>
      <div v-if="authenticatedUser" class="nav-buttons">
        <router-link
          v-if="isAdmin"
          to="/boardPage"
          class="connect-btn"
        >
          Tableau de Bord
        </router-link>
        <router-link
          v-if="isAdmin"
          to="/allClientForms"
          class="connect-btn"
        >
          Formulaires Clients
        </router-link>
        <router-link
          v-if="isAdmin"
          to="/allCompanies"
          class="connect-btn"
        >
          Liste des Clients
        </router-link>
        <router-link
          v-if="isCompany"
          to="/new-questionnaire"
          class="connect-btn"
        >
          Nos Questionnaires
        </router-link>
      </div>
    </div>
    <div class="auth-buttons">
      <template v-if="authenticatedUser">
        <span class="user-email">{{ displayIdentifier }}</span>
        <button @click="logout" class="connect-btn">
          Se déconnecter
        </button>
      </template>
      <router-link
        v-else
        to="/login"
        class="connect-btn"
      >
        Se connecter
      </router-link>
    </div>
  </header>
</template>

<script setup>
import { ref, onMounted, watch, computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import {
  getAuthenticatedUser,
  clearAuthenticatedUser,
  isAuthenticated,
} from "../services/auths.js";

const router = useRouter();
const route = useRoute();

const authenticatedUser = ref(null);

const displayIdentifier = computed(() => {
  const user = authenticatedUser.value;
  if (!user) return '';

  // Check if the user data is stored in localStorage as COMPANY
  const isCompany = localStorage.getItem('company') !== null;

  return isCompany ? user.login : user.email;
});

const isAdmin = computed(() => {
  return authenticatedUser.value && localStorage.getItem('company') === null;
});

const isCompany = computed(() => {
  return authenticatedUser.value && localStorage.getItem('company') !== null;
});

const checkAuthentication = () => {
  if (isAuthenticated()) {
    authenticatedUser.value = getAuthenticatedUser();
    console.log('Authenticated user:', authenticatedUser.value);
  } else {
    authenticatedUser.value = null;
  }
};

const logout = () => {
  clearAuthenticatedUser();
  authenticatedUser.value = null;
  router.push("/login");
};

onMounted(() => {
  checkAuthentication();
});

watch(() => route.path, () => {
  checkAuthentication();
});
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
  display: flex;
  align-items: center;
}

.logo {
  width: 180px;
  height: 70px;
  margin-right: 15px;
}

.nav-buttons {
  display: flex;
  gap: 10px;
}

.auth-buttons {
  position: absolute;
  right: 25px;
  top: 50%;
  transform: translateY(-50%);
  display: flex;
  align-items: center;
}

.user-email {
  color: white;
  margin-right: 15px;
  font-weight: 500;
}

.connect-btn {
  background-color: #40867A;
  color: white;
  padding: 10px 20px;
  border-radius: 5px;
  text-decoration: none;
  font-weight: 500;
  transition: background-color 0.3s ease;
  border: none;
  cursor: pointer;
  display: inline-block;
  text-align: center;
  line-height: 1.5;
  white-space: nowrap;
}

.connect-btn:hover {
  background-color: #346d63;
}

.logo-container .connect-btn {
  margin-left: 15px;
}
</style>
