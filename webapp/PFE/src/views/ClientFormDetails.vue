<template>
    <div>
      <h1>Détails du Formulaire</h1>
      <div v-if="form && company">
        <h2>Entreprise : {{ company.name }}</h2>
        <p><strong>ID Entreprise :</strong> {{ company.id }}</p>
        <p><strong>Date de création :</strong> {{ formatDate(form.created_at) }}</p>
        <p><strong>Total Questions :</strong> {{ form.total }}</p>
        <p><strong>Complété :</strong> {{ form.completed }}</p>
        <p><strong>Questions :</strong></p>
        <ul>
          <li v-for="(question, index) in form.question_list" :key="index">{{ question }}</li>
        </ul>
      </div>
      <p v-else>Chargement des données...</p>
    </div>
  </template>
  
  <script>
  import FormsService from '@/services/FormsService';
  
  export default {
    data() {
      return {
        form: null, // Stocke les détails du formulaire
        company: null, // Stocke les détails de l'entreprise
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
          console.error('Erreur lors de la récupération des détails du formulaire:', error);
        }
      },
      formatDate(date) {
        const options = { year: 'numeric', month: 'long', day: 'numeric' };
        return new Date(date).toLocaleDateString('fr-FR', options);
      },
    },
    mounted() {
      this.fetchFormDetails();
    },
  };
  </script>
  