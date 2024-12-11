<template>
  <div class="page-container">
    <OurCard title="Résultats de votre évaluation ESG" class="results-card">
      <div class="score-content">
        <h1 class="global-score">Score global :{{ parseFloat(scoreESG).toFixed(2) }}%</h1>

        <div class="category-scores">
          <h2>Scores par catégorie</h2>
          

          <div class="score-item">
            <div class="score-label">
              <span>Environnement</span>
              <span>{{ parseFloat(scoreE).toFixed(2) }}</span>
            </div>
            <div class="progress-bar">
              <div class="progress" :style="{ width: calculateProgress(scoreE) }"></div>
            </div>
          </div>

          <div class="score-item">
            <div class="score-label">
              <span>Social</span>
              <span>{{ parseFloat(scoreS).toFixed(2) }}</span>
            </div>
            <div class="progress-bar">
              <div class="progress" :style="{ width: calculateProgress(scoreS) }"></div>
            </div>
          </div>

          <div class="score-item">
            <div class="score-label">
              <span>Gouvernance</span>
              <span>{{ parseFloat(scoreG).toFixed(2) }}</span>
            </div>
            <div class="progress-bar">
              <div class="progress" :style="{ width: calculateProgress(scoreG) }"></div>
            </div>
          </div>
        </div>

      

        <div class="recommendations">
          <h2>Recommandations</h2>
          <ul>
            <li>Améliorez votre politique de gestion des déchets</li>
            <li>Renforcez vos programmes de formation des employés</li>
            <li>Augmentez la transparence dans vos rapports financiers</li>
          </ul>
        </div>

        <div class="button-container">
          <router-link to="/new-questionnaire">
            <button class="return-button">
              Retourner aux questionnaires →
            </button>
          </router-link>
        </div>
      </div>
    </OurCard>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router';
import OurCard from '../components/OurCard.vue';
const route = useRoute();


// Récupérer les scores depuis les paramètres de l'URL
const scoreE = route.query.scoreE || 0;
const scoreS = route.query.scoreS || 0;
const scoreG = route.query.scoreG || 0;
const scoreESG = route.query.scoreESG || 0;

const calculateProgress = (score) => {
  const percentage = Math.min(Math.max(parseFloat(score), 0), 100);
  return `${percentage}%`;
};


</script>

<style scoped>
.page-container {
  min-height: 100vh;
  padding: 2rem;
  display: flex;
  justify-content: center;
  align-items: flex-start;
}

.results-card {
  width: 100%;
  max-width: 800px;
  margin: 0 auto;
  background: white;
  border-radius: 1rem;
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
}

.score-content {
  display: flex;
  flex-direction: column;
  gap: 2rem;
  padding: 2rem;
}

.global-score {
  color: #00574E;
  font-size: 2rem;
  font-weight: bold;
  text-align: center;
  margin: 1rem 0;
}

.category-scores h2,
.recommendations h2 {
  color: #40867A;
  font-size: 1.25rem;
  margin-bottom: 1rem;
}

.score-item {
  margin-bottom: 1rem;
}

.score-label {
  display: flex;
  justify-content: space-between;
  margin-bottom: 0.5rem;
  color: #002E37;
}

.progress-bar {
  background: #E5E7EB;
  border-radius: 9999px;
  height: 0.75rem;
  overflow: hidden;
}

.progress {
  background: #40867A;
  height: 100%;
  border-radius: 9999px;
  transition: width 0.3s ease;
}

.recommendations ul {
  list-style: none;
  padding: 0;
}

.recommendations li {
  color: #4B5563;
  margin-bottom: 0.5rem;
  padding-left: 1.5rem;
  position: relative;
}

.recommendations li::before {
  content: "•";
  color: #40867A;
  position: absolute;
  left: 0.5rem;
}

.button-container {
  display: flex;
  justify-content: flex-end;
  margin-top: 1rem;
}

.return-button {
  background: #F3F4F6;
  color: #374151;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 0.5rem;
  cursor: pointer;
  transition: background-color 0.2s;
}

.return-button:hover {
  background: #E5E7EB;
}
</style>