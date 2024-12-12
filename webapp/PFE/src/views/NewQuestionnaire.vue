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
        :class="['questionnaire-option', { selected: selectedQuestionnaire === 'ESG', disabled: hasESGForm }]"
        @click="!hasESGForm && selectQuestionnaire('ESG')"
      >
        <div class="radio-circle">
          <div class="radio-inner" v-if="selectedQuestionnaire === 'ESG'"></div>
        </div>
        <div class="option-icon"></div>
        <span>Questionnaire ESG</span>
      </div>
      <!-- Message d'avertissement pour le questionnaire ESG -->
      <div v-if="hasESGForm" class="warning-message">
        Vous avez déjà un questionnaire ESG en cours.
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
          :key="q.formId"
          :title="'Questionnaire ESG'"
        >
          <div class="card-progress-info">
            <div class="progress-header">
              <span class="progress-text">
                Progression :
                <template v-if="q.completed !== q.total">
                  {{ q.progress }}%
                </template>
                <template v-else>
                  Terminé
                </template>
              </span>
              <span class="date-text">
                {{ q.submitted ? `Soumis le : ${formatDate(q.sendAt)}` : `Créé le : ${formatDate(q.createdAt)}` }}
              </span>
            </div>

            <div class="progress-bar-container">
              <div
                class="progress-bar-fill"
                :style="{ width: `${q.progress}%` }"
              ></div>
            </div>

            <button
              v-if="!q.submitted"
              class="continue-button"
              @click="router.push('/questionnaire/' + q.formId)"
            >
              Continuer →
            </button>
            <button
              v-else
              class="results-button"
              @click="router.push('/Forms/' + q.formId)"
            >
              Consulter mes résultats →
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
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import OurCard from '../components/OurCard.vue';
import { getAuthenticatedUser } from "@/services/auths.js";
import FormsService from "@/services/FormsService.js";

const router = useRouter();
const selectedQuestionnaire = ref('');
const activeTab = ref('new');
const hasESGForm = ref(false);
const questionnaires = ref([]);

// Fonction pour calculer la progression
const calculateProgress = (completed, total) => {
  if (!total || total === 0) return 0; // Évite une division par zéro
  return Math.round((completed / total) * 100);
};

const formatDate = (date) => {
  const options = { year: 'numeric', month: 'long', day: 'numeric' };
  return new Date(date).toLocaleDateString('fr-FR', options);
};

// Charger les questionnaires
const loadQuestionnaire = async () => {
  try {
    const currentCompany = getAuthenticatedUser();
    const response = await FormsService.getFormByCompanyId(currentCompany.companyId);
    const questionnaire = response.data;

    if (questionnaire) {
      hasESGForm.value = true;
      questionnaires.value.push({
        ...questionnaire,
        progress: calculateProgress(questionnaire.completed, questionnaire.total),
      });
    } else {
      hasESGForm.value = false;
    }
  } catch (error) {
    console.error("Erreur lors du chargement du formulaire :", error);
  }
};

const selectQuestionnaire = (type) => {
  selectedQuestionnaire.value = type;
};

const startQuestionnaire = () => {
  if (selectedQuestionnaire.value) {
    router.push('/questionnaire/' + selectedQuestionnaire.value);
  }
};

onMounted(() => {
  loadQuestionnaire();
});
</script>


  <style scoped>
.progress-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.5rem;
}

.progress-text {
  font-size: 1rem;
  font-weight: bold;
  color: #2F8886;
}

.date-text {
  font-size: 0.875rem;
  color: #666;
}

.progress-bar-container {
  width: 100%;
  height: 8px;
  background-color: #E2E8F0;
  border-radius: 999px;
  overflow: hidden;
  margin-top: 0.5rem;
}

.progress-bar-fill {
  height: 100%;
  background: linear-gradient(to right, #004851, #40867A);
  border-radius: 999px;
  transition: width 0.3s ease;
}

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
.warning-message {
  color: #d9534f;
  font-size: 0.875rem;
  margin-top: 1rem;
}

.questionnaire-option.disabled {
  opacity: 0.6;
  pointer-events: none;
  cursor: not-allowed; /* Ajout du curseur interdit */
}

.results-button {
  width: 100%;
  padding: 0.875rem;
  background-color: #40867A;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.results-button:hover {
  background-color: #2F8886;
}

</style>

