<template>
  <div class="form-details">
    <div class="header">
      <h1 class="title">Détails du Formulaire</h1>
      <p class="subtitle">Consultation des réponses</p>
    </div>

    <div v-if="form && company" class="form-container">
      <!-- Company Info -->
      <div class="company-info">
        <h2>{{ company.name }}</h2>
        <div class="company-meta">
          <span>ID Entreprise : {{ company.companyId }}</span>
          <span class="separator">•</span>
          <span>ID Form : {{ form.formId }}</span>
          <span class="separator">•</span>
          <span>{{ formatDate(form.createdAt) }}</span>
        </div>
      </div>

      <!-- Statistics Cards -->
      <div class="stats-grid">
        <div class="stat-card">
          <div class="stat-value">{{ form.total }}</div>
          <div class="stat-label">Questions Totales</div>
        </div>
        <div class="stat-card">
          <div class="stat-value">{{ form.completed }}</div>
          <div class="stat-label">Questions Complétées</div>
        </div>
        <div class="stat-card">
          <div class="stat-value">{{ (form.scoreESG || 0).toFixed(2) }}%</div>
          <div class="stat-label">Score ESG</div>
        </div>
      </div>

      <!-- Questions List -->
      <div class="questions-section">
        <h3>Questions et Réponses</h3>
        <div class="questions-list">
          <div
            v-for="(question, index) in form.questionList"
            :key="question.questionId"
            class="question-card"
          >
            <div class="question-header">
              <div class="question-number">Q{{ index + 1 }}</div>
              <div class="question-text">{{ question.question }}</div>
            </div>
            <div class="answer-section">
              <!-- Display answer or fallback message -->
              <p v-if="getAnswerForQuestion(question.questionId)" class="answer">
                {{ getAnswerForQuestion(question.questionId).response }}
              </p>
              <p v-else class="no-answer">Aucune réponse fournie</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import FormsService from "@/services/FormsService";

export default {
  data() {
    return {
      form: null,
      company: null,
    };
  },
  computed: {
    calculateProgress() {
      if (!this.form?.total) return 0;
      return Math.round((this.form.completed / this.form.total) * 100);
    }
  },
  methods: {
    async fetchFormDetails() {
      const formId = Number(this.$route.params.formId);
      try {
        const formResponse = await FormsService.getFormClientByFormId(formId);
        this.form = formResponse.data;

        const companyResponse = await FormsService.getFormClientByCompanyId(
          this.form.companyId
        );
        this.company = companyResponse.data;
        console.log("company : ", this.company);
      } catch (error) {
        console.error(
          "Erreur lors de la récupération des détails du formulaire:",
          error
        );
      }
    },
    formatDate(date) {
      const options = { year: "numeric", month: "long", day: "numeric" };
      return new Date(date).toLocaleDateString("fr-FR", options);
    },
    // Helper method to find an answer for a specific questionId
    getAnswerForQuestion(questionId) {
      if (!this.form || !this.form.answersList) return null;
      return this.form.answersList.find((answer) => answer.questionId === questionId);
    },
  },

  mounted() {
    this.fetchFormDetails();
  },
};
</script>

<style scoped>
.form-details {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
}

.header {
  text-align: center;
  margin-bottom: 2rem;
  background: #006D77;
  color: white;
  padding: 2rem;
  border-radius: 8px;
}

.title {
  font-size: 2rem;
  font-weight: 600;
  margin: 0;
}

.subtitle {
  margin-top: 0.5rem;
  opacity: 0.9;
}

.form-container {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 2rem;
}

.company-info {
  margin-bottom: 2rem;
}

.company-info h2 {
  color: #006D77;
  margin: 0 0 0.5rem 0;
}

.company-meta {
  color: #666;
  font-size: 0.9rem;
}

.separator {
  margin: 0 0.5rem;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1rem;
  margin-bottom: 2rem;
}

.stat-card {
  background: #f8f9fa;
  padding: 1.5rem;
  border-radius: 8px;
  text-align: center;
}

.stat-value {
  font-size: 2rem;
  font-weight: bold;
  color: #006D77;
}

.stat-label {
  color: #666;
  font-size: 0.9rem;
  margin-top: 0.5rem;
}

.questions-section {
  margin-top: 2rem;
}

.questions-section h3 {
  color: #006D77;
  margin-bottom: 1rem;
}

.questions-list {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.question-card {
  background: #f8f9fa;
  border-radius: 8px;
  padding: 1.5rem;
}

.question-header {
  display: flex;
  gap: 1rem;
  margin-bottom: 1rem;
}

.question-number {
  background: #006D77;
  color: white;
  padding: 0.25rem 0.75rem;
  border-radius: 4px;
  font-weight: 500;
}

.question-text {
  font-weight: 500;
}

.answer-section {
  padding-left: 3rem;
}

.answer {
  color: #333;
}

.no-answer {
  color: #666;
  font-style: italic;
}
</style>
