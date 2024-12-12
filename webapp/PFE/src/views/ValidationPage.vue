<template>
    <div class="page-container">
      <OurCard 
        title="Validation du questionnaire"
        subtitle="Veuillez confirmer la fiabilité de vos réponses avant de soumettre définitivement le questionnaire."
      >
        <div class="validation-content">
          <div class="checkbox-container">
            <label class="checkbox-label">
              <input 
                type="checkbox" 
                v-model="isConfirmed"
                class="checkbox-input"
              >
              <span class="checkbox-text">
                Je confirme que les réponses fournies dans ce questionnaire sont fiables et reflètent fidèlement la situation de mon entreprise.
              </span>

            </label>
          </div>
  
          <p class="info-text">
            En cochant cette case, vous vous engagez à respecter les engagements choisis durant le questionnaire et à fournir des informations précises pour obtenir des résultats cohérents.
          </p>
  
          <div class="buttons-container">
            <button 
              class="button button-secondary"
              @click="$router.back()"
            >
              ← Retourner au questionnaire
            </button>
            <button 
              class="button button-primary"
              :disabled="!isConfirmed"
              @click="handleSubmit"
            >
              Confirmer et soumettre →
            </button>
          </div>
        </div>
      </OurCard>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import { useRouter } from 'vue-router'
  import OurCard from '../components/OurCard.vue'

  import { useRoute } from 'vue-router';

const route = useRoute();


// Récupérer les scores depuis les paramètres de l'URL
const scoreE = route.query.scoreE || 0;
const scoreS = route.query.scoreS || 0;
const scoreG = route.query.scoreG || 0;
const scoreESG = route.query.scoreESG || 0;
  
  const router = useRouter()
  const isConfirmed = ref(false)
  
  const handleSubmit = () => {
    if (isConfirmed.value) {
      router.push({path: '/EndQuestionnaire',
      query: { scoreE, scoreS, scoreG, scoreESG }})
    }
  }
  </script>
  
  <style scoped>
  .page-container {
    max-width: 800px;
    margin: 2rem auto;
    padding: 0 1rem;
  }
  
  .validation-content {
    padding: 2rem 1rem;
  }
  
  .checkbox-container {
    margin-bottom: 1.5rem;
  }
  
  .checkbox-label {
    display: flex;
    align-items: flex-start;
    gap: 1rem;
    cursor: pointer;
  }
  
  .checkbox-input {
    width: 20px;
    height: 20px;
    margin-top: 3px;
  }
  
  .checkbox-text {
    font-size: 1rem;
    line-height: 1.5;
  }
  
  .info-text {
    color: #2F8886;
    font-size: 0.875rem;
    line-height: 1.5;
    margin-bottom: 2rem;
  }
  
  .buttons-container {
    display: flex;
    justify-content: space-between;
    gap: 1rem;
  }
  
  .button {
    padding: 0.75rem 1.5rem;
    border-radius: 8px;
    border: none;
    font-size: 1rem;
    cursor: pointer;
    transition: opacity 0.2s ease;
  }
  
  .button:disabled {
    opacity: 0.5;
    cursor: not-allowed;
  }
  
  .button-primary {
    background-color: #004851;
    color: white;
  }
  
  .button-secondary {
    background-color: #E2E8F0;
    color: #004851;
  }
  
  @media (max-width: 640px) {
    .buttons-container {
      flex-direction: column;
    }
    
    .button {
      width: 100%;
    }
  }
  </style>