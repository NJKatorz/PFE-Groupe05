<template>
    <div>
      <h1>Liste des Formulaires</h1>
      <div class="form-grid">
        <div class="form-card" v-for="form in forms" :key="form.formId">
          <h2>TestCompany - {{ form.formId }}</h2>
          <p><strong>Date de création :</strong> 10.12.2021</p>
          <p><strong>Nombre total de questions :</strong> 19</p>
          <p><strong>Score :</strong> 100 </p>
        </div>
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
    // Méthode pour récupérer les formulaires des entreprises
    async fetchForms() {
      try {
        const response = await FormsService.getAllFormClients();
        console.log('Données récupérées :', response.data); // Ajoute ceci pour vérifier les données
        this.forms = response.data; // Stocker les données dans la liste
      } catch (error) {
        console.error('Erreur lors de la récupération des formulaires:', error);
      }
    },
  },
  mounted() {
    // Récupère les formulaires lorsque le composant est monté
    this.fetchForms();
  },
};
</script>

<style scoped>
/* Style pour organiser les vignettes */
.form-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 16px;
  margin-top: 20px;
}

/* Style pour chaque vignette */
.form-card {
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 16px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease-in-out;
}

.form-card:hover {
  transform: scale(1.02);
}

.form-card h2 {
  font-size: 1.5em;
  margin-bottom: 10px;
  color: #333;
}

.form-card p {
  font-size: 1em;
  margin: 5px 0;
  color: #666;
}

.form-card strong {
  color: #333;
}
</style>