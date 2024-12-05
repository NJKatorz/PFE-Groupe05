<template>
    <div>
    <h1>Liste des Formulaires</h1>
    <ul>
      <li v-for="form in forms" :key="form.id">
        {{ form.question }} - {{ product.enjeux }}
      </li>
    </ul>
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
        this.products = response.data; // Stocker les données dans la liste
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