<template>
  <div class="form-details">
    <h1 class="title">Détails du Formulaire</h1>
    <div v-if="form && company" class="form-container">
      <div class="company-info">
        <h2>Entreprise : {{ company.name }}</h2>
        <p><strong>ID Entreprise :</strong> {{ company.id }}</p>
      </div>
      <div class="form-info">
        <p><strong>Date de création :</strong> {{ formatDate(form.createdAt) }}</p>
        <p><strong>Total des questions :</strong> {{ form.total }}</p>
        <p><strong>Total des questions complétées :</strong> {{ form.completed }}</p>
        <h3>Questions :</h3>
        <ul>
          <li v-for="question in form.questionList" :key="question.question_id">{{ question.question }}
          </li>
        </ul>
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
  methods: {
    async fetchFormDetails() {
      const formId = Number(this.$route.params.formId); // Récupère formId depuis l'URL
      try {
        const formResponse = await FormsService.getFormClientByFormId(formId);
        this.form = formResponse.data;

        // Récupérer les informations de l'entreprise via company_id du formulaire
        const companyResponse = await FormsService.getFormClientByCompanyId(
          this.form.companyId
        );
        this.company = companyResponse.data;
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
  },
  mounted() {
    this.fetchFormDetails();
  },
};
</script>

<style scoped>
.form-details {
  font-family: Arial, sans-serif;
  padding: 20px;
  max-width: 800px;
  margin: auto;
}

.title {
  text-align: center;
  margin-bottom: 20px;
}

.form-container {
  border: 1px solid #ddd;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.company-info {
  margin-bottom: 20px;
}

.form-info ul {
  list-style-type: disc;
  margin-left: 20px;
}
</style>
