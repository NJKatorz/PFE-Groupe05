<template>
  <div class="questionnaire-container">
    <OurCard :title="'QUESTIONNAIRE ' + type">
      <!-- Progress Bar -->
      <div class="progress-bar">
        <div class="progress-fill" :style="{ width: progress + '%' }"></div>
      </div>

      <!-- Module Header -->
      <div class="module-header">
        <div class="module-icon">
          <img src="../assets/moduleImage.png" alt="Santé et bien être" />
          <span class="module-number">3</span>
        </div>
        <div class="module-info">
          <h2 class="module-title">MODULE 3</h2>
          <p class="module-subtitle">SANTÉ ET BIEN ÊTRE</p>
        </div>
      </div>

      <!-- Questions -->
      <div class="questions-container">
        <div 
          v-for="(question, index) in questions" 
          :key="index" 
          class="question"
        >
          <h3>{{ question.text }}</h3>
          <div class="options">
            <!-- Wrapper element now holds the v-if -->
            <template v-if="question.type === 'radio'">
              <div
                v-for="option in question.options"
                :key="option"
                class="radio-option"
                @click="selectOption(index, option)"
              >
                <div class="radio-circle">
                  <div class="radio-inner" v-if="selectedAnswers[index] === option"></div>
                </div>
                <span>{{ option }}</span>
              </div>
            </template>

            <template v-else-if="question.type === 'checkbox'">
              <div
                v-for="option in question.options"
                :key="option"
                class="checkbox-option"
                @click="toggleCheckbox(index, option)"
              >
                <div class="checkbox">
                  <div class="checkbox-inner" v-if="selectedAnswers[index]?.includes(option)"></div>
                </div>
                <span>{{ option }}</span>
              </div>
            </template>
          </div>
        </div>
      </div>

      <!-- Navigation Buttons -->
      <div class="navigation-buttons">
        <button class="btn btn-previous" @click="$router.back()">
          <i class="fas fa-arrow-left"></i>
           Précédent
        </button>
        <button class="btn btn-save">
          <i class="fas fa-save"></i>
          Sauvegarder
        </button>
        <button class="btn btn-next" @click="handleSubmit">
          Suivant
          <i class="fas fa-arrow-right"></i>
        </button>
      </div>
    </OurCard>
  </div>
</template>


  
<script setup>
import { ref } from 'vue';
import OurCard from '../components/OurCard.vue';
import { useRouter } from 'vue-router'

// eslint-disable-next-line no-unused-vars
const props = defineProps({
  type: {
    type: String,
    required: true
  }
});

 const router = useRouter()

 const handleSubmit = () => {
      router.push('/validation')
  }
 

// Questions data
const questions = ref([
  {
    text: 'Quel est le niveau de votre politique ESG?',
    type: 'radio',
    options: ['Débutant', 'Intermédiaire', 'Avancé']
  },
  {
    text: 'Sélectionnez les certifications que vous possédez',
    type: 'checkbox',
    options: ['ISO 14001', 'ISO 26000', 'B Corp', 'Label RSE']
  }
]);

const selectedAnswers = ref(questions.value.map(question => 
  question.type === 'checkbox' ? [] : ''
));

// Progress calculation
const progress = ref(0);
const updateProgress = () => {
  const totalQuestions = questions.value.length;
  const answered = selectedAnswers.value.filter(answer =>
    Array.isArray(answer) ? answer.length > 0 : answer !== ''
  ).length;
  progress.value = (answered / totalQuestions) * 100;
};

// Handlers for answers
const selectOption = (index, option) => {
  selectedAnswers.value[index] = option;
  updateProgress();
};

const toggleCheckbox = (index, option) => {
  const currentAnswers = selectedAnswers.value[index];
  const answerIndex = currentAnswers.indexOf(option);
  if (answerIndex === -1) {
    currentAnswers.push(option);
  } else {
    currentAnswers.splice(answerIndex, 1);
  }
  updateProgress();
};
</script>

  
  <style scoped>
  .questionnaire-container {
    max-width: 800px;
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
    padding: 1rem;
    border: 2px solid #E2E8F0;
    border-radius: 8px;
    cursor: pointer;
    transition: all 0.2s ease;
  }
  
  .radio-option:hover, .checkbox-option:hover {
    border-color: #2F8886;
    background-color: #F7FAFC;
  }
  
  .radio-circle, .checkbox {
    width: 24px;
    height: 24px;
    border: 2px solid #2F8886;
    margin-right: 1rem;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  
  .radio-circle {
    border-radius: 50%;
  }
  
  .checkbox {
    border-radius: 4px;
  }
  
  .radio-inner {
    width: 12px;
    height: 12px;
    background-color: #2F8886;
    border-radius: 50%;
  }
  
  .checkbox-inner {
    width: 12px;
    height: 12px;
    background-color: #2F8886;
    border-radius: 2px;
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