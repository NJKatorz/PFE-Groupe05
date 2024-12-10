<script setup>
import { ref } from 'vue';
import OurCard from '../components/OurCard.vue';
import FormsService from '../services/FormsService';
import { useRouter } from 'vue-router';
import {setAuthenticatedUser} from "@/services/auths.js";

// Form fields
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
      const { token, role, user, company } = response.data;

      if (role === 'company') {
        setAuthenticatedUser(company, token, role);
      } else if (role === 'admin') {
        setAuthenticatedUser(user, token, role);
      } else {
        throw new Error('Invalid user role');
      }

      console.log('Connexion réussie en tant que :', role);

      // Redirect based on role
      if (role === 'admin') {
        router.push('/boardPage');
      } else if (role === 'company') {
        router.push('/new-questionnaire');
      }
    }
  } catch (error) {
    console.error('Login error:', error);
    errorMessage.value = 'Connexion échouée. Veuillez vérifier vos identifiants.';
  }
};
</script>

<template>
  <div class="container">
    <div class="content">
      <!-- Header -->
      <div class="header">
        <h1>
          Formulaires ESG & ODD
          <span class="subtitle">par</span>
          <img src="../assets/img.png" alt="Better Business" class="img-size" />
        </h1>
        <p class="description">
          Découvrez votre impact et contribuez à un avenir durable grâce à notre plateforme d'évaluation innovante.
        </p>
      </div>

      <!-- Login Card -->
      <OurCard title="Se connecter" subtitle="Connectez-vous pour accéder aux questionnaires">
        <div class="form-container">
          <form @submit.prevent="submitForm">
            <div class="input-group">
              <label for="emailOrLogin">Email ou Login</label>
              <input
                id="emailOrLogin"
                v-model="emailOrLogin"
                type="text"
                placeholder="votreemail@exemple.com"
                required
              />
            </div>

            <div class="input-group">
              <label for="password">Mot de passe</label>
              <input
                id="password"
                v-model="password"
                type="password"
                placeholder="Mot de passe"
                required
              />
            </div>

            <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>

            <button type="submit" class="submit-button">Se connecter</button>
          </form>

          <div class="register-link">
            <span>Pas encore de compte? </span>
            <a href="#">Inscrivez-vous ici</a>
          </div>
        </div>
      </OurCard>
    </div>
  </div>
</template>

<style scoped>

.img-size {
  width: 160px;
}
.container {
  min-height: 100vh;
  background-color: #f0f4f5;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.content {
  width: 100%;
  max-width: 800px;
  margin-bottom: -100px;
}

.header {
  text-align: center;
  margin-top: -180px;
  margin-bottom: 30px;
}

.header h1 {
  font-size: 32px;
  font-weight: bold;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.header .subtitle {
  font-size: 16px;
  font-weight: normal;
}

.header .description {
  font-size: 18px;
  color: #606060;
}

.form-container {
  padding: 24px;
}

.input-group {
  margin-bottom: 16px;
}

.input-group label {
  display: block;
  font-size: 16px;
  font-weight: 500;
  color: #004851;
  margin-bottom: 8px;
}

.input-group input {
  width: 100%;
  height: 20px;
  padding: 12px;
  border: 2px solid #e2e8f0;
  border-radius: 8px;
  font-size: 16px;
  color: #4a5568;
}

.input-group input:focus {
  border-color: #40867a;
  outline: none;
}

.error-message {
  color: red;
  font-size: 14px;
  text-align: center;
  margin-bottom: 16px;
}

.submit-button {
  width: 100%;
  height: 52px;
  background-color: #004851;
  color: white;
  font-size: 16px;
  font-weight: bold;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.submit-button:hover {
  background-color: #40867a;
}

.register-link {
  margin-top: 15px;
  text-align: center;
  font-size: 15px;
}

.register-link a {
  color: #40867a;
  text-decoration: none;
}

.register-link a:hover {
  text-decoration: underline;
}
</style>

