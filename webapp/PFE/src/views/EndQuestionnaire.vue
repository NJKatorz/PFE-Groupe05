<template>
    <div class="page-container">
      <OurCard title="Questionnaire soumis avec succès">
        <div class="success-content">
          <div class="success-icon">
            <svg viewBox="0 0 24 24" class="check-icon">
              <path fill="currentColor" d="M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z"/>
            </svg>
          </div>
          
          <div class="success-message">
            <p class="message-primary">Merci d'avoir complété le questionnaire.</p>
            <p class="message-secondary">Vos réponses ont été enregistrées avec succès.</p>
          </div>
  
          <button 
            class="view-score-button"
            @click="$router.push({path: '/score',
            query: { scoreE, scoreS, scoreG, scoreESG }})"
          >
            Consulter mon score →
          </button>
        </div>
      </OurCard>
    </div>
  </template>
  
  <script setup>
  import OurCard from '../components/OurCard.vue'

  import { useRoute } from 'vue-router';

    const route = useRoute();


    // Récupérer les scores depuis les paramètres de l'URL
    const scoreE = route.query.scoreE || 0;
    const scoreS = route.query.scoreS || 0;
    const scoreG = route.query.scoreG || 0;
    const scoreESG = route.query.scoreESG || 0;
  </script>
  
  <style scoped>
  .page-container {
    max-width: 800px;
    margin: 2rem auto;
    padding: 0 1rem;
  }
  
  .success-content {
    padding: 3rem 1rem;
    text-align: center;
  }
  
  .success-icon {
    width: 64px;
    height: 64px;
    background-color: #4CAF50;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 0 auto 2rem;
  }
  
  .check-icon {
    width: 40px;
    height: 40px;
    color: white;
  }
  
  .success-message {
    margin-bottom: 2rem;
  }
  
  .message-primary {
    font-size: 1.25rem;
    color: #004851;
    margin-bottom: 0.5rem;
  }
  
  .message-secondary {
    font-size: 1.125rem;
    color: #004851;
  }
  
  .view-score-button {
    background-color: #004851;
    color: white;
    padding: 0.75rem 1.5rem;
    border-radius: 8px;
    border: none;
    font-size: 1rem;
    cursor: pointer;
    transition: opacity 0.2s ease;
    width: 100%;
    max-width: 300px;
  }
  
  .view-score-button:hover {
    opacity: 0.9;
  }
  
  @media (max-width: 640px) {
    .success-content {
      padding: 2rem 1rem;
    }
  }
  </style>