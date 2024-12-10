<template>
  <div class="center">
    <h1>Liste des Formulaires</h1>
    <div class="form-grid">
      <router-link
          v-for="form in forms"
          :key="form.formId"
          :to="{ name: 'ClientFormDetails', params: { formId: form.formId } }"
          class="card-link"
      >
        <OurCard
            :title="form.companyName"
            :subtitle="`Date de création : ${formatDate(form.createdAt)}`"
        >
          <p><strong>Total des questions :</strong> {{ form.total }}</p>
          <p><strong>Total des questions complétées :</strong> {{ form.completed }}</p>
        </OurCard>
      </router-link>
    </div>
  </div>
</template>

<script>
import FormsService from '@/services/FormsService'
import OurCard from '../components/OurCard.vue' // Import the OurCard component

export default {
  components: {
    OurCard, // Register the OurCard component
  },
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
  flex-direction: column;
}

/* Grille des formulaires */
.form-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 16px;
  max-width: 1200px;
  width: 100%;
}

/* Style pour le lien englobant la carte */
.card-link {
  text-decoration: none;
  color: inherit;
  transition: transform 0.2s ease-in-out;
}

.card-link:hover {
  transform: scale(1.02);
}
</style>
