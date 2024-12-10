<script setup>
import { ref, computed, onMounted } from 'vue';
import OurCard from '../components/OurCard.vue';
import api from '../services/api';
import { useRouter } from 'vue-router';
import {getAuthenticatedUser} from "@/services/auths.js";

const questionsByCategory = ref({});
const categories = ref([]);
const currentCategoryIndex = ref(0);
const selectedAnswers = ref({});
const router = useRouter(); // Router pour la navigation

// Données de la catégorie actuelle
const currentCategory = computed(() => categories.value[currentCategoryIndex.value]);
const currentQuestions = computed(() => questionsByCategory.value[currentCategory.value] || []);
const company = getAuthenticatedUser();

const progress = ref(0); // Stocke la progression actuelle (récupérée du backend)


const formId = ref(null); // Ajoutez une variable réactive pour l'ID du formulaire

const progressPercentage = computed(() => {
  if (!categories.value.length) return 0;
  return (currentCategoryIndex.value  / categories.value.length) * 100;
});

onMounted(async () => {
  try {
    // Récupérer les données via l'API
    const response = await api.post(`/forms/company/${company.companyId}`);
    console.log('Réponse de l’API :', response.data);
    const formData = response.data;

    if (!formData || !formData.questionList) {
      console.error('Aucune question trouvée dans la réponse de l’API.');
      return;
    }

    formId.value = formData.formId; // Stockez l'ID du formulaire
    console.log('ID du formulaire :', formId.value);

    const questions = formData.questionList;



    // Fonction pour remplacer "XXX" par le nom de l'entreprise
    const replaceXXXWithCompanyName = (questions, companyName) => {
      return questions.map((question) => {
        if (typeof question.question === 'string') {
          question.question = question.question.replace(/XXX/g, companyName);
        }
        return question;
      });
    };

    // Appliquer la fonction de remplacement
    const companyName = company.name || 'Votre entreprise';
    replaceXXXWithCompanyName(questions, companyName);

    // Parser les choix pour chaque question
    questions.forEach((question) => {
    if (question.choice) {
        question.choice = question.choice.map((option) => {
            if (typeof option === 'string') {
                try {
                    return JSON.parse(option); // Parser seulement si c'est une chaîne JSON
                } catch (error) {
                    console.error('Erreur lors du parsing du choix :', option, error);
                    return { choice: option, poids: 0 }; // Valeur par défaut en cas d'erreur
                }
            }
            return option; // Retourner directement si c'est déjà un objet
        });
    }
});


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

    // Charger la progression initiale
    //await progressPercentage();

    // Charger la progression initiale
  } catch (error) {
    console.error('Erreur lors du chargement des données :', error);
  }
});

// Gestion des réponses
const selectOption = (questionId, option) => {
  const category = currentCategory.value;
  selectedAnswers.value[category][questionId] = option;
  console.log('Option sélectionnée :', selectedAnswers.value[category]);
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
  console.log('Réponse mise à jour :', selectedAnswers.value[category]);
};

const handleTextInput = (questionId, value) => {
  const category = currentCategory.value;
  selectedAnswers.value[category][questionId] = value;
  console.log('Texte saisi :', selectedAnswers.value[category]);
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
  }
};

const saveAnswers = async () => {
  try {
    const category = currentCategory.value;
    const answers = Object.entries(selectedAnswers.value[category]).map(
      ([questionId, value]) => ({
        questionId: parseInt(questionId, 10),
        response: Array.isArray(value)
          ? JSON.stringify(value.map(v => v.choice || v))
          : value.choice || value,
        comments: '',
      })
    );

    console.log('Données envoyées au backend :', JSON.stringify(answers));

    const response = await api.post(`/forms/${formId.value}/saveAnswers`, answers);

    if (response.status === 200) {
      console.log('Réponses sauvegardées avec succès.');


    } else {
      throw new Error('Erreur lors de la sauvegarde des réponses.');
    }
  } catch (error) {
    console.error('Erreur lors de la sauvegarde des réponses :', error);
  }
};



const submitForm = async () => {
  try {
    const response = await api.post(`/forms/${formId.value}/submit`);

    if (response.status === 200) {
      console.log('Formulaire soumis avec succès :', response.data);

      const scoreE = response.data.scoreE ;
      const scoreS = response.data.scoreS;
      const scoreG = response.data.scoreG;
      const scoreESG = response.data.scoreESG;

      // Rediriger vers la page de validation avec les scores comme paramètres
      router.push({
        path: '/validation',
        query: { scoreE, scoreS, scoreG, scoreESG }
      });

      // Extraire les scores de l'objet renvoyé
    } else {
      throw new Error('Erreur lors de la soumission du formulaire.');
    }
  } catch (error) {
    console.error('Erreur lors de la soumission du formulaire :', error);
  }
};


const goToNextCategory = async () => {
  await saveAnswers(); // Sauvegarder les réponses de la catégorie actuelle

  if (currentCategoryIndex.value < categories.value.length - 1) {
    currentCategoryIndex.value++;
    try {
      const response = await api.put(`/forms/${formId.value}/progression`);
      progress.value = response.data.progression; // Mettre à jour la progression locale
      console.log('Progression mise à jour :', progress.value);
    } catch (error) {
      console.error('Erreur lors de la mise à jour de la progression :', error);
    }
    scrollToTop();
  } else {
     await submitForm();
  }
};
</script>

<template>
  <div class="questionnaire-container">
    <OurCard :title="'QUESTIONNAIRE ESG '">
      <!-- Barre de progression -->
      <div class="progress-bar">
        <div
          class="progress-fill"
          :style="{ width: `${progressPercentage}%` }"
        ></div>
        <div class="progress-percentage">{{ progressPercentage.toFixed(0) }}%</div>
      </div>

      <!-- Titre de la catégorie -->
      <div class="module-header">
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
                <span>{{ option.choice }}</span>
              </div>
            </template>

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
                <span>{{ option.choice }}</span>
              </div>
            </template>

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
        <button
          class="btn btn-previous"
          @click="goToPreviousCategory"
          :disabled="currentCategoryIndex === 0"
        >
          Précédent
        </button>
        <button class="btn btn-next" @click="goToNextCategory">
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
  position: relative;
  height: 16px;
  background-color: #e0e0e0;
  border-radius: 8px;
  margin: 1rem 0;
  overflow: hidden;
}

.progress-fill {
  height: 100%;
  background-color: #4caf50;
  border-radius: 8px;
  transition: width 0.3s ease;
}

.progress-percentage {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 14px;
  font-weight: bold;
  color: #ffffff;
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

.module-title {
  font-size: 1.5rem;
  font-weight: bold;
  color: white;
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
  min-height: 60px;
}

.radio-option:hover, .checkbox-option:hover {
  border-color: #2F8886;
  background-color: #F7FAFC;
}

.radio-circle, .checkbox {
  flex-shrink: 0;
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
  border-radius: 50%;
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

