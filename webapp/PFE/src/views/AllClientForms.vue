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
        <h2>{{ form.companyName }}</h2>
        <p><strong>Date de création :</strong> {{ formatDate(form.createdAt) }}</p>
        <p><strong>Total des questions :</strong> {{ form.total }}</p>
        <p><strong>Total des questions complétées :</strong> {{ form.completed }}</p>
      </router-link>
    </div>
  </div>
</template>


<script>
import FormsService from '@/services/FormsService'

export default {
  data() {
    return {
      forms: [], 
      company: [], 
    }
  },
  methods: {
    async fetchForms() {
      try {
        // Récupérer les formulaires
        const response = await FormsService.getAllFormClients()
        const forms = response.data

        // Enrichir chaque formulaire avec le nom de l'entreprise
        for (const form of forms) {
          try {
            const companyResponse = await FormsService.getFormClientByCompanyId(form.companyId)
            form.companyName = companyResponse.data.name // Ajouter dynamiquement companyName
          } catch (error) {
            console.error(`Erreur pour l'entreprise avec companyId ${form.companyId}:`, error)
            form.companyName = 'Nom non disponible' // Valeur par défaut
          }
        }

        this.forms = forms // Mettre à jour la liste des formulaires enrichis
      } catch (error) {
        console.error('Erreur lors de la récupération des formulaires:', error)
      }
    },

    formatDate(date) {
      const options = { year: 'numeric', month: 'long', day: 'numeric' }
      return new Date(date).toLocaleDateString('fr-FR', options)
    },
  },
  mounted() {
    this.fetchForms()
  },
}
</script>

<style scoped>
/* Conteneur principal pour centrer le contenu */
div {
  display: flex;
  justify-content: center; /* Centrer horizontalement */
  align-items: center; /* Centrer verticalement */
  min-height: 10vh; /* Prendre toute la hauteur de la page */
  flex-direction: column; /* Empile les éléments verticalement */
}

/* Grille des formulaires */
.form-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 16px;
  max-width: 1200px; /* Largeur maximale de la grille */
  width: 100%; /* Adapter la largeur au conteneur */
}

/* Style des cartes */
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
