<script>
import CompaniesService from '@/services/CompaniesService.js'

export default {
  data() {
    return {
      companies: [], // Liste des entreprises
    }
  },
  methods: {
    async fetchCompanies() {
      try {
        // Récupérer les entreprises depuis le service
        const response = await CompaniesService.getAll();

        // Trier les entreprises pour que les non validées apparaissent en premier
        this.companies = response.data.sort((a, b) => {
          if (!a.validated && b.validated) {
            return -1; // a (non validée) vient avant b (validée)
          }
          if (a.validated && !b.validated) {
            return 1; // b (non validée) vient avant a (validée)
          }
          return 0; // Si les deux sont validées ou non validées, l'ordre reste inchangé
        });
      } catch (error) {
        console.error('Erreur lors de la récupération des entreprises:', error);
      }
    },

    async validateCompany(companyId) {
      try {
        console.log("Id de l'entreprise : ", companyId)
        // Appel API pour valider l'entreprise
        const response = await CompaniesService.validateCompany(companyId)

        // Mettre à jour l'état local si l'entreprise est validée
        if (response.data.success) {
          const updatedCompany = this.companies.find(c => c.id === companyId)
          if (updatedCompany) {
            updatedCompany.validated = true // Marquer l'entreprise comme validée
          }
        }
      } catch (error) {
        console.error('Erreur lors de la validation de l\'entreprise:', error)
      }
    },

    formatDate(date) {
      const options = { year: 'numeric', month: 'long', day: 'numeric' }
      return new Date(date).toLocaleDateString('fr-FR', options)
    },
  },
  mounted() {
    this.fetchCompanies()
  },
}
</script>


<template>
  <div class="container">
    <h1 class="title">Liste des Entreprises</h1>
    <div class="form-grid">
      <!-- Boucle sur les entreprises -->
      <div
        class="form-card"
        v-for="company in companies"
        :key="company.id"
        :class="{'small-card': !company.validated}"
      >
        <h2>{{ company.name }}</h2>
        <p><strong>Code NACE :</strong> {{ company.naceCode }}</p>
        <p><strong>Date de validation :</strong> {{ formatDate(company.registrationDate) }}</p>
        <p><strong>Personne de contact :</strong> {{ company.contactEmail }}</p>
        <p><strong>Nombre d'employés :</strong> {{ company.numberOfWorkers }}</p>

        <!-- Affichage des templates -->
        <div v-if="company.templates && company.templates.length > 0">
          <h3>Templates associés</h3>
          <ul>
            <li v-for="template in company.templates" :key="template">{{ template }}</li>
          </ul>
        </div>
        <div v-else>
          <p>Aucun template associé.</p>
        </div>

        <!-- Affichage du bouton de validation uniquement si l'entreprise n'est pas validée -->
        <div v-if="!company.validated">
          <button @click="validateCompany(company.companyId)" class="validate-btn">Valider l'entreprise</button>
        </div>

      </div>
    </div>
  </div>
</template>


<style scoped>

/* Styles pour le bouton de validation */
.validate-btn {
  background-color: #28a745; /* Couleur verte */
  color: #fff; /* Texte blanc */
  border: none; /* Retirer la bordure */
  border-radius: 5px; /* Coins arrondis */
  padding: 10px 20px; /* Espacement interne */
  cursor: pointer; /* Pointeur pour l'interaction */
  transition: background-color 0.3s ease; /* Animation de changement de couleur */
}

.validate-btn:hover {
  background-color: #218838; /* Couleur plus foncée au survol */
}

.validate-btn:focus {
  outline: none; /* Retirer le contour par défaut */
  box-shadow: 0 0 5px rgba(40, 167, 69, 0.8); /* Ajouter un léger ombrage au focus */
}

.form-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
  gap: 20px;
  max-width: 1200px;
  width: 100%;
  margin: 0 auto;
  grid-auto-rows: minmax(300px, auto);
  padding: 10px;
}

/* Styles des cartes */
.form-card {
  background-color: #fff;
  border-radius: 15px;
  padding: 20px;
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  border: 1px solid #ddd;
  font-family: 'Roboto', sans-serif;
  min-height: 300px;
  box-sizing: border-box;
}
</style>
