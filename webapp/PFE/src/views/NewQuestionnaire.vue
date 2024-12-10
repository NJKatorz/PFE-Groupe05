<!-- Progress Content -->
<template>
  <div class="questionnaire-container">
    <div class="tabs-container">
      <div
        :class="['tab', { active: activeTab === 'new' }]"
        @click="activeTab = 'new'"
      >
        Nouveau questionnaire
      </div>
      <div
        :class="['tab', { active: activeTab === 'progress' }]"
        @click="activeTab = 'progress'"
      >
        Questionnaires en cours
      </div>
    </div>

    <!-- Section pour les nouveaux questionnaires -->
    <OurCard
      v-if="activeTab === 'new'"
      title="Choisissez votre questionnaire"
      subtitle="Sélectionnez le type d'évaluation que vous souhaitez effectuer"
    >
      <!-- Options de questionnaire -->
      <div
        :class="['questionnaire-option', { selected: selectedQuestionnaire === 'ESG' }]"
        @click="selectQuestionnaire('ESG')"
      >
        <div class="radio-circle">
          <div class="radio-inner" v-if="selectedQuestionnaire === 'ESG'"></div>
        </div>
        <div class="option-icon">🌱</div>
        <span>Questionnaire ESG</span>
      </div>

      <div
        :class="['questionnaire-option', { selected: selectedQuestionnaire === 'ODD' }]"
        @click="selectQuestionnaire('ODD')"
      >
        <div class="radio-circle">
          <div class="radio-inner" v-if="selectedQuestionnaire === 'ODD'"></div>
        </div>
        <div class="option-icon">🌍</div>
        <span>Questionnaire ODD</span>
      </div>

      <button
        class="next-button"
        :disabled="!selectedQuestionnaire"
        @click="startQuestionnaire"
      >
        Suivant →
      </button>
    </OurCard>

    <!-- Section pour les questionnaires en cours -->
    <div v-else-if="activeTab === 'progress'">
      <div v-if="questionnaires.length > 0" class="progress-cards">
        <OurCard
          v-for="q in questionnaires"
          :key="q.id"
          :title="q.name === 'ESG' ? 'Questionnaire ESG' : 'Questionnaire ODD'"
        >
          <div class="card-progress-info">
            <div class="progress-header">
              <span class="progress-text">
                Progression : {{ q.progress < 100 ? `${q.progress}%` : 'Terminé' }}
              </span>
              <span class="date-text">
                {{ q.submitted ? `Soumis le : ${q.submitted}` : `Créé le : ${q.created}` }}
              </span>
            </div>

            <div class="progress-bar-container">
              <div
                class="progress-bar-fill"
                :style="{ width: `${q.progress}%` }"
              ></div>
            </div>

            <button
              v-if="q.progress < 100"
              class="continue-button"
              @click="router.push('/questionnaire/' + q.name)"
            >
              Continuer →
            </button>
          </div>
        </OurCard>
      </div>
      <div v-else>
        <p class="no-questionnaires">Aucun questionnaire en cours</p>
      </div>
    </div>
  </div>
</template>


<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import OurCard from '../components/OurCard.vue';

const router = useRouter();
const selectedQuestionnaire = ref('');
const activeTab = ref('new');

// Dummy data for demonstration
//à adapter avec les données de la base de données/backend
const questionnaires = ref([
  { id: 1, name: 'ESG', progress: 80, created: '03/12/2024' },
  { id: 2, name: 'ODD', progress: 100, submitted: '03/12/2024' },
]);

const selectQuestionnaire = (type) => {
  selectedQuestionnaire.value = type;
};

const startQuestionnaire = () => {
  if (selectedQuestionnaire.value) {
    router.push('/questionnaire/' + selectedQuestionnaire.value);
  }
};
</script>

  <style scoped>
  .questionnaire-container {
    width: 100%;
    max-width: 800px;
    margin: 2rem auto;
    padding: 0 1rem;
  }

  .tabs-container {
    display: flex;
    border-radius: 8px;
    overflow: hidden;
    margin-bottom: 2rem;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }

  .tab {
    flex: 1;
    padding: 1rem;
    text-align: center;
    background-color: #8a8a8a;
    color: white;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }

  .tab.active {
    background-color: #2F8886;
  }

  .questionnaire-option {
    display: flex;
    align-items: center;
    padding: 1.25rem;
    margin-bottom: 1rem;
    border: 2px solid #E2E8F0;
    border-radius: 12px;
    cursor: pointer;
    transition: all 0.3s ease;
  }

  .questionnaire-option:hover {
    border-color: #2F8886;
    background-color: #F7FAFC;
  }

  .questionnaire-option.selected {
    border-color: #2F8886;
    background-color: #F7FAFC;
  }

  .radio-circle {
    width: 24px;
    height: 24px;
    border: 2px solid #2F8886;
    border-radius: 50%;
    margin-right: 1rem;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .radio-inner {
    width: 12px;
    height: 12px;
    background-color: #2F8886;
    border-radius: 50%;
  }

  .option-icon {
    font-size: 1.5rem;
    margin-right: 1rem;
  }

  .next-button {
    width: 100%;
    padding: 1rem;
    margin-top: 1.5rem;
    background: #004851;
    color: white;
    border: none;
    border-radius: 8px;
    font-size: 1rem;
    cursor: pointer;
    transition: opacity 0.3s ease;
  }

  .next-button:disabled {
    opacity: 0.7;
    cursor: not-allowed;
  }

  .next-button:not(:disabled):hover {
    opacity: 0.9;
  }

  .no-questionnaires {
    color: #666;
    text-align: center;
  }

  @media (max-width: 640px) {
    .questionnaire-container {
      margin: 1rem auto;
    }
  }

  .progress-cards {
    display: flex;
    flex-direction: column;
    gap: 1rem;
  }

  .card-progress-info {
    display: flex;
    flex-direction: column;
    gap: 1.5rem;
  }

  .progress-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    color: #4A5568;
  }

  .progress-text {
    font-size: 1rem;
    font-weight: 500;
  }

  .date-text {
    font-size: 0.875rem;
  }

  .progress-bar-container {
    width: 100%;
    height: 8px;
    background-color: #E2E8F0;
    border-radius: 999px;
    overflow: hidden;
  }

  .progress-bar-fill {
    height: 100%;
    background: linear-gradient(to right, #004851, #40867A);
    border-radius: 999px;
    transition: width 0.3s ease;
  }

  .continue-button {
    width: 100%;
    padding: 0.875rem;
    background-color: #004851;
    color: white;
    border: none;
    border-radius: 8px;
    font-size: 1rem;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }

  .continue-button:hover {
    background-color: #003840;
  }
  </style>
