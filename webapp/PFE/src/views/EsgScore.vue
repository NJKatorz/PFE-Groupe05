<template>
  <div class="page-container">
    <OurCard title="Résultats de votre évaluation ESG" class="results-card">
      <div class="score-content">
        <h1 class="global-score">Score global : {{ parseFloat(scoreESG).toFixed(2) }}%</h1>

        <div class="category-scores">
          <h2>Scores par catégorie</h2>

          <div class="score-item">
            <div class="score-label">
              <span>Environnement</span>
              <span>{{ parseFloat(scoreE).toFixed(2) }}</span>
            </div>
            <div class="progress-bar">
              <div class="progress" :style="{ width: calculateProgress(scoreE) , background:'#009a4a' }"></div>
            </div>
          </div>

          <div class="score-item">
            <div class="score-label">
              <span>Social</span>
              <span>{{ parseFloat(scoreS).toFixed(2) }}</span>
            </div>
            <div class="progress-bar">
              <div class="progress" :style="{ width: calculateProgress(scoreS), background:'#0081c3' }"></div>
            </div>
          </div>

          <div class="score-item">
            <div class="score-label">
              <span>Gouvernance</span>
              <span>{{ parseFloat(scoreG).toFixed(2) }}</span>
            </div>
            <div class="progress-bar">
              <div class="progress" :style="{ width: calculateProgress(scoreG), background:'#f07833' }"></div>
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
          <router-link v-if="isCompany" to="/new-questionnaire">
            <button class="return-button">
              Retourner aux questionnaires →
            </button>
          </router-link>
          <button v-else class="return-button" @click="goBack">
            Retour
          </button>
        </div>
      </div>
    </OurCard>
  </div>
</template>

<script setup>
import {ref, onMounted, computed} from 'vue';
import { useRoute } from 'vue-router';
import OurCard from '../components/OurCard.vue';
import FormsService from "@/services/FormsService";

const route = useRoute();

const scoreE = ref(0);
const scoreS = ref(0);
const scoreG = ref(0);
const scoreESG = ref(0);

const calculateProgress = (score) => {
  const percentage = Math.min(Math.max(parseFloat(score), 0), 100);
  return `${percentage}%`;
};

const fetchScoreData = async (formId) => {
  try {
    const response = await FormsService.getFormClientByFormId(formId);
    const formData = response.data;
    scoreE.value = formData.scoreE || 0;
    scoreS.value = formData.scoreS || 0;
    scoreG.value = formData.scoreG || 0;
    scoreESG.value = formData.scoreESG || 0;
  } catch (error) {
    console.error("Error fetching score data:", error);
  }
};

const isCompany = computed(() => {
  return localStorage.getItem('company') !== null;
});

onMounted(() => {
  const formId = route.query.formId;
  if (formId) {
    fetchScoreData(formId);
  } else {
    // Use the scores from the URL parameters if no formId is provided
    scoreE.value = route.query.scoreE || 0;
    scoreS.value = route.query.scoreS || 0;
    scoreG.value = route.query.scoreG || 0;
    scoreESG.value = route.query.scoreESG || 0;
  }
});
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
  margin-left: 10px;
}

.return-button:hover {
  background: #E5E7EB;
}
</style>
