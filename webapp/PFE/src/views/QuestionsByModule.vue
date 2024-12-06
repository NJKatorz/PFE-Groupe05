
<script setup>
import { ref, computed, onMounted } from 'vue';
import OurCard from '../components/OurCard.vue';
import api from '../services/api';
import { useRouter } from 'vue-router';

const questionsByCategory = ref({});
const categories = ref([]);
const currentCategoryIndex = ref(0);
const selectedAnswers = ref({});
const progress = ref(0);
const router = useRouter(); // Router pour la navigation

// Données de la catégorie actuelle
const currentCategory = computed(() => categories.value[currentCategoryIndex.value]);
const currentQuestions = computed(() => questionsByCategory.value[currentCategory.value] || []);

onMounted(async () => {
  try {
    // Récupérer les données via l'API
    const response = await api.post('/forms/1');
    const formData = response.data;

    if (!formData  || !formData.questionList) {
      console.error('Aucune question trouvée dans la réponse de l’API.');
      return;
    }

    const questions = formData.questionList;

    // Regrouper les questions par catégorie
    questionsByCategory.value = questions.reduce((acc, question) => {
      const category = question.category || 'Non catégorisé'; // Gérer les catégories manquantes
      if (!acc[category]) acc[category] = [];
      acc[category].push(question);
      return acc;
    }, {});

    // Extraire les catégories
    categories.value = Object.keys(questionsByCategory.value);

    // Initialiser les réponses par catégorie
    selectedAnswers.value = categories.value.reduce((acc, category) => {
      acc[category] = questionsByCategory.value[category].reduce((answers, question) => {
        answers[question.questionId] = question.type === 'checkbox' ? [] : '';
        return answers;
      }, {});
      return acc;
    }, {});

    console.log('Questions regroupées par catégorie :', questionsByCategory.value);
    console.log('Réponses initialisées :', selectedAnswers.value);
  } catch (error) {
    console.error('Erreur lors du chargement des données :', error);
  }
});

// Mettre à jour le progrès
const updateProgress = () => {
  const category = currentCategory.value;
  const totalQuestions = currentQuestions.value.length;
  const answered = Object.values(selectedAnswers.value[category] || {}).filter((answer) =>
    Array.isArray(answer) ? answer.length > 0 : answer !== ''
  ).length;
  progress.value = totalQuestions > 0 ? (answered / totalQuestions) * 100 : 0;
};

// Gestion des réponses
const selectOption = (questionId, option) => {
  const category = currentCategory.value;
  selectedAnswers.value[category][questionId] = option;
  updateProgress();
};

const toggleCheckbox = (questionId, option) => {
  const category = currentCategory.value;
  const answers = selectedAnswers.value[category][questionId];
  const index = answers.indexOf(option);
  if (index === -1) {
    answers.push(option);
  } else {
    answers.splice(index, 1);
  }
  selectedAnswers.value[category][questionId] = answers;
  updateProgress();
};

const handleTextInput = (questionId, value) => {
  const category = currentCategory.value;
  selectedAnswers.value[category][questionId] = value;
  updateProgress();
};

const scrollToTop = () => {
  window.scrollTo({
    top: 0,
    behavior: 'smooth', // Ajoute un défilement fluide
  });
};

const goToPreviousCategory = () => {
  if (currentCategoryIndex.value > 0) {
    currentCategoryIndex.value--;
    scrollToTop(); 
    updateProgress();
  }
};

// Sauvegarder les réponses d'une catégorie
const saveAnswers = async () => {
  try {
    const category = currentCategory.value;
    const answers = Object.entries(selectedAnswers.value[category]).map(
      ([questionId, value]) => ({
        questionId: parseInt(questionId, 10), // Convertir en nombre si nécessaire
        answer: value,
      })
    );

    // Appeler l'API pour sauvegarder les réponses
    const response = await api.post(`/forms/1/saveAnswers`, answers);

    if (response.status === 200) {
      console.log('Réponses sauvegardées avec succès pour la catégorie :', category);
    } else {
      throw new Error('Erreur lors de la sauvegarde des réponses.');
    }
  } catch (error) {
    console.error('Erreur lors de la sauvegarde des réponses :', error);
  }
};

const submitForm = async () => {
  try {
    const response = await api.post(`/forms/1/submit`);

    if (response.status === 200) {
      console.log('Formulaire soumis avec succès :', response.data);
      router.push('/validation'); // Rediriger vers la page de validation
    } else {
      throw new Error('Erreur lors de la soumission du formulaire.');
    }
  } catch (error) {
    console.error('Erreur lors de la soumission du formulaire :', error);
  }
};

// Naviguer vers la catégorie suivante
const goToNextCategory = async () => {
  await saveAnswers(); // Sauvegarder les réponses de la catégorie actuelle

  if (currentCategoryIndex.value < categories.value.length - 1) {
    currentCategoryIndex.value++;
    scrollToTop();
    updateProgress();
  } else {
    await submitForm();
    // Redirection après la dernière catégorie
    router.push('/validation');
  }
}; 

</script>


<template>
  <div class="questionnaire-container">
    <OurCard :title="'QUESTIONNAIRE ESG '">
      <!-- Progress Bar -->
      <div class="progress-bar">
        <div class="progress-fill" :style="{ width: progress + '%' }"></div>
      </div>

      <!-- Titre de la catégorie -->
      <div class="module-header" >
        <div class="module-info">
          <div class="module-title">
            <p>{{ categories[currentCategoryIndex] }}</p>
          </div>
        </div>
      </div>

      <!-- Questions -->
      <div class="questions-container">
        <div
          v-for="(question) in currentQuestions"
          :key="question.questionId"
          class="question"
        >
          <h3>{{ question.question }}</h3>
          <div class="options">
            <!-- Si le type est "radio" -->
            <template v-if="question.type === 'radio'">
              <div
                v-for="option in question.choice"
                :key="option"
                class="radio-option"
                @click="selectOption(question.questionId, option)"
              >
                <div class="radio-circle">
                  <div
                    class="radio-inner"
                    v-if="selectedAnswers[categories[currentCategoryIndex]][question.questionId] === option"
                  ></div>
                </div>
                <span>{{ option }}</span>
              </div>
            </template>

            <!-- Si le type est "checkbox" -->
            <template v-else-if="question.type === 'checkbox'">
              <div
                v-for="option in question.choice"
                :key="option"
                class="checkbox-option"
                @click="toggleCheckbox(question.questionId, option)"
              >
                <div class="checkbox">
                  <div
                    class="checkbox-inner"
                    v-if="selectedAnswers[categories[currentCategoryIndex]][question.questionId]?.includes(option)"
                  ></div>
                </div>
                <span>{{ option }}</span>
              </div>
            </template>

            <!-- Pour le texte libre -->
            <template v-else-if="question.type === 'champ libre'">
              <input
                type="text"
                class="text-input"
                @input="handleTextInput(question.questionId, $event.target.value)"
                :value="selectedAnswers[categories[currentCategoryIndex]][question.questionId]"
              />
            </template>
          </div>
        </div>
      </div>
    

      <!-- Boutons de navigation -->
        <div class="navigation-buttons">
    <!-- Bouton précédent -->
    <button
      class="btn btn-previous"
      @click="goToPreviousCategory"
      :disabled="currentCategoryIndex === 0"
    >
      Précédent
    </button>

    <!-- Bouton suivant -->
    <button
      class="btn btn-next"
      @click="goToNextCategory"
    >
      {{ currentCategoryIndex === categories.length - 1 ? 'Soumettre' : 'Suivant' }}
    </button>
  </div>
    </OurCard>
  </div>
</template>



  
  <style scoped>
  .questionnaire-container {
    max-width: 1000px;
    margin: 0 auto;
    padding: 1rem;
  }
  
  .progress-bar {
    height: 8px;
    background-color: rgba(255, 255, 255, 0.3);
    border-radius: 4px;
    margin: 1rem 0;
  }
  
  .progress-fill {
    height: 100%;
    background-color: #fff;
    border-radius: 4px;
    transition: width 0.3s ease;
  }
  
  .module-header {
    background-color: #2F8886;
    padding: 1.5rem;
    border-radius: 8px;
    display: flex;
    align-items: center;
    gap: 1rem;
    margin: 2rem 0;
  }
  
  .module-icon {
    position: relative;
    width: 60px;
    height: 60px;
    background-color: #4CAF50;
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  
  .module-icon img {
    width: 32px;
    height: 32px;
  }
  
  .module-number {
    position: absolute;
    top: -8px;
    left: -8px;
    background-color: white;
    color: #2F8886;
    width: 24px;
    height: 24px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    font-weight: bold;
  }
  
  .module-info {
    color: white;
  }
  
  .module-title {
    font-size: 1.5rem;
    font-weight: bold;
    margin: 0;
  }
  
  .module-subtitle {
    margin: 0;
    opacity: 0.9;
  }
  
  .questions-container {
    padding: 2rem 0;
  }
  
  .question {
    margin-bottom: 2rem;
  }
  
  .question h3 {
    color: #004851;
    margin-bottom: 1rem;
  }
  
  .options {
    display: flex;
    flex-direction: column;
    gap: 0.75rem;
  }
  
  .radio-option, .checkbox-option {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 1rem;
  border: 2px solid #E2E8F0;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.2s ease;
  min-height: 60px; /* Fixe une hauteur minimale */
}

.radio-option:hover, .checkbox-option:hover {
  border-color: #2F8886;
  background-color: #F7FAFC;
}

.radio-circle, .checkbox {
  flex-shrink: 0; /* Empêche la réduction de taille des cercles/carrés */
  width: 24px;
  height: 24px;
  border: 2px solid #2F8886;
  display: flex;
  align-items: center;
  justify-content: center;
}

.radio-circle {
  border-radius: 100%;
}

.checkbox {
  border-radius: 4px;
}

.radio-inner, .checkbox-inner {
  width: 12px;
  height: 12px;
  background-color: #2F8886;
}

.radio-inner {

  border-radius: 50%; /* Assure que l'intérieur reste rond */
}
  
  .checkbox-option {
    justify-content: space-between;
  }
  
  .checkbox-option i {
    color: #2F8886;
  }
  
  .navigation-buttons {
    display: flex;
    justify-content: space-between;
    gap: 1rem;
    margin-top: 2rem;
  }
  
  .btn {
    padding: 0.75rem 1.5rem;
    border-radius: 8px;
    border: none;
    cursor: pointer;
    display: flex;
    align-items: center;
    gap: 0.5rem;
    font-weight: 500;
    transition: opacity 0.2s ease;
  }
  
  .btn:hover {
    opacity: 0.9;
  }
  
  .btn-previous {
    background-color: #004851;
    color: white;
  }
  
  .btn-save {
    background-color: #E2E8F0;
    color: #004851;
  }
  
  .btn-next {
    background-color: #004851;
    color: white;
  }

  .text-input {
  width: 95%;
  padding: 1rem;
  border: 2px solid #E2E8F0;
  border-radius: 8px;
  font-size: 1rem;
  transition: border-color 0.2s ease, background-color 0.2s ease;
}

.text-input:focus {
  outline: none;
  border-color: #2F8886;
  background-color: #F7FAFC;
}

  
  @media (max-width: 640px) {
    .navigation-buttons {
      flex-direction: column;
    }
    
    .btn {
      width: 100%;
      justify-content: center;
    }
  }
  </style>