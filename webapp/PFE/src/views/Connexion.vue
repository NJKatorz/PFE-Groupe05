<script setup>
import { ref } from 'vue';
import FormsService from '../services/FormsService';
import { useRouter } from 'vue-router';
import {setAuthenticatedUser} from "@/utils/auths.js";

// Champs du formulaire
const emailOrLogin = ref('');
const password = ref('');
const errorMessage = ref('');
const router = useRouter();

const submitForm = async () => {
  try {
    const isEmail = emailOrLogin.value.includes('@');
    const credentials = {
      [isEmail ? 'email' : 'login']: emailOrLogin.value,
      password: password.value,
    };

    const response = await FormsService.login(credentials);

    if (response.status === 200) {
      const { token, role } = response.data;
      localStorage.setItem('authToken', token);
      if (role === 'company')
      setAuthenticatedUser(response.data.company, token, role);
      if (role === 'admin')
        setAuthenticatedUser(response.data.user, token, role);

        console.log('Connexion réussie en tant que :', role);

      if (role === 'admin') {
        router.push('/allClientForms');
      } else if (role === 'company') {
        router.push('/new-questionnaire');
      }
    }
  } catch (error) {
    console.error(error);
    errorMessage.value = 'Connexion échouée. Veuillez vérifier vos identifiants.';
  }
};
</script>

<template>
  <div class="min-h-screen bg-[#f0f4f5] p-6 flex flex-col justify-center items-center">
    <div class="w-full max-w-[560px] space-y-8">
      <!-- Header -->
      <div class="text-center">
        <h1 class="flex items-center justify-center gap-2 text-[32px] font-bold">
          Formulaires ESG & ODD
          <span class="text-base font-normal">par</span>
          <img src="../assets/img.png" alt="Better Business" class="h-10" />
        </h1>
        <p class="mt-4 text-lg text-gray-600">
          Découvrez votre impact et contribuez à un avenir durable grâce à notre plateforme d'évaluation innovante.
        </p>
      </div>

      <!-- Login Card -->
      <OurCard
        title="Se connecter"
        subtitle="Connectez-vous pour accéder aux questionnaires"
      >
        <div class="px-8 py-6 space-y-6">
          <form @submit.prevent="submitForm">
            <!-- Champ Email ou Login -->
            <div class="space-y-2">
              <label class="block text-[#004851] text-base font-medium">Email ou Login</label>
              <div class="relative">
                <div class="absolute left-4 top-1/2 -translate-y-1/2">
                  <!-- Icône pour email/login -->
                  <svg
                    width="24"
                    height="24"
                    fill="none"
                    xmlns="http://www.w3.org/2000/svg"
                    class="text-[#40867A]"
                  >
                    <path d="M22 8.608V16.75C22 17.4793 21.7103 18.1788 21.1945 18.6945C20.6788 19.2103 19.9793 19.5 19.25 19.5H4.75C4.02065 19.5 3.32118 19.2103 2.80546 18.6945C2.28973 18.1788 2 17.4793 2 16.75V8.608L11.526 13.926C11.6761 14.0207 11.8466 14.0705 12.021 14.0705C12.1954 14.0705 12.3659 14.0207 12.516 13.926L22 8.608ZM19.25 4.5C19.9584 4.50033 20.6393 4.77195 21.1552 5.26493C21.6712 5.7579 21.9734 6.42664 22.006 7.134L12 12.752L1.994 7.134C2.02657 6.42664 2.32884 5.7579 2.84478 5.26493C3.36072 4.77195 4.04163 4.50033 4.75 4.5H19.25Z" fill="currentColor" />
                  </svg>
                </div>
                <input
                  type="text"
                  v-model="emailOrLogin"
                  placeholder="Entrez votre email ou login"
                  class="w-full h-[52px] px-12 rounded-lg border-2 border-[#E2E8F0] text-[#4A5568] placeholder-[#A0AEC0] focus:border-[#40867A] focus:outline-none transition-colors"
                  required
                />
              </div>
            </div>

            <!-- Champ Mot de passe -->
            <div class="space-y-2">
              <label class="block text-[#004851] text-base font-medium">Mot de passe</label>
              <div class="relative">
                <input
                  type="password"
                  v-model="password"
                  placeholder="Mot de passe"
                  class="w-full h-[52px] px-12 rounded-lg border-2 border-[#E2E8F0] text-[#4A5568] placeholder-[#A0AEC0] focus:border-[#40867A] focus:outline-none transition-colors"
                  required
                />
              </div>
            </div>

            <!-- Message d'erreur -->
            <div v-if="errorMessage" class="text-red-500 text-sm">{{ errorMessage }}</div>

            <!-- Bouton de soumission -->
            <button
              type="submit"
              class="w-full h-[52px] flex items-center justify-center gap-2 rounded-lg bg-[#004851] text-white hover:bg-[#40867A] transition-colors"
            >
              Se connecter
            </button>
          </form>

          <!-- Lien pour s'inscrire -->
          <div class="text-center text-[15px] mt-4">
            <span class="text-[#1A202C]">Pas encore de compte ? </span>
            <a href="#" class="text-[#40867A] hover:underline">Inscrivez-vous ici</a>
          </div>
        </div>
      </OurCard>
    </div>
  </div>
</template>
<style scoped>
.connexion-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background-color: #f5f5f5;
}

.connexion-card {
  background: white;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
}

h2 {
  font-size: 1.8rem;
  margin-bottom: 1rem;
  color: #004851;
}

p {
  font-size: 1rem;
  color: #666;
}

.form-group {
  margin-bottom: 1rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
  color: #333;
}

input[type="text"],
input[type="password"] {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
}

.checkbox-group {
  display: flex;
  align-items: center;
  justify-content: flex-start;
}

.checkbox-group label {
  font-size: 0.9rem;
  color: #555;
}

.error-message {
  color: red;
  font-size: 0.9rem;
  margin-bottom: 1rem;
}

.btn {
  background-color: #004851;
  color: white;
  padding: 0.75rem 1.5rem;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.btn:hover {
  background-color: #00796b;
}
</style>
