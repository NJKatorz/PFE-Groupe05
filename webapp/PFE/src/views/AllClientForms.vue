<template>
    <div>
      <h1>Liste des Formulaires</h1>
      <div class="form-grid">
        <router-link
          class="form-card"
          v-for="form in forms"
          :key="form.formId"
          :to="{ name: 'ClientFormDetails', params: { formId: form.formId } }"
        >
          <h2>Entreprise ID: {{ form.company_id }}</h2>
          <p><strong>Date de création :</strong> {{ formatDate(form.created_at) }}</p>
          <p><strong>Total Questions :</strong> {{ form.total }}</p>
          <p><strong>Complété :</strong> {{ form.completed }}</p>
        </router-link>
      </div>
    </div>
  </template>
  
  <script>
  import FormsService from '@/services/FormsService';
  
  export default {
    data() {
      return {
        forms: [], // Stocke les formulaires récupérés
      };
    },
    methods: {
      async fetchForms() {
        try {
          const response = await FormsService.getAllFormClients();
          this.forms = response.data; // Stocker les données dans 'forms'
        } catch (error) {
          console.error('Erreur lors de la récupération des formulaires:', error);
        }
      },
      formatDate(date) {
        const options = { year: 'numeric', month: 'long', day: 'numeric' };
        return new Date(date).toLocaleDateString('fr-FR', options);
      },
    },
    mounted() {
      this.fetchForms();
    },
  };
  </script>
  
  <style scoped>
  .form-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: 16px;
    margin-top: 20px;
  }
  
  .form-card {
    background-color: #f9f9f9;
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 16px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    transition: transform 0.2s ease-in-out;
    text-decoration: none;
    color: inherit;
  }
  
  .form-card:hover {
    transform: scale(1.02);
  }
  </style>
  