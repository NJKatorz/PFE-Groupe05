<template>
  <div class="center">
    <h1>Liste des Formulaires</h1>

    <!-- Search bar with icon -->
    <div class="search-container">
      <input
        v-model="searchQuery"
        type="text"
        placeholder="Rechercher par nom d'entreprise, date ou complété"
        class="search-input"
      />
      <SearchIcon class="search-icon" />
    </div>

    <div class="form-grid">
      <router-link
        v-for="form in filteredForms"
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
          <p><strong>Progression :</strong> {{ Math.round(form.progression * 100) }}%</p>
          <p><strong>Statut :</strong> {{ form.isSubmitted ? 'Soumis' : 'En cours' }}</p>
        </OurCard>
      </router-link>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import FormsService from '@/services/FormsService'
import OurCard from '../components/OurCard.vue'
import { SearchIcon } from 'lucide-vue-next'

const router = useRouter()
const forms = ref([])
const searchQuery = ref('')

const filteredForms = computed(() => {
  if (!searchQuery.value) return forms.value

  const query = searchQuery.value.toLowerCase()
  return forms.value.filter(form =>
    form.companyName.toLowerCase().includes(query) ||
    formatDate(form.createdAt).toLowerCase().includes(query) ||
    form.completed.toString().includes(query) ||
    Math.round(form.progression * 100).toString().includes(query) ||
    (form.isSubmitted ? 'soumis' : 'en cours').includes(query)
  )
})

const fetchForms = async () => {
  try {
    const response = await FormsService.getAllFormClients()
    const fetchedForms = response.data

    for (const form of fetchedForms) {
      try {
        const companyResponse = await FormsService.getFormClientByCompanyId(form.companyId)
        form.companyName = companyResponse.data.name
      } catch (error) {
        console.error(`Erreur pour l'entreprise avec companyId ${form.companyId}:`, error)
        form.companyName = 'Nom non disponible'
      }
    }

    forms.value = fetchedForms
  } catch (error) {
    console.error('Erreur lors de la récupération des formulaires:', error)
  }
}

const formatDate = (date) => {
  const options = { year: 'numeric', month: 'long', day: 'numeric' }
  return new Date(date).toLocaleDateString('fr-FR', options)
}

onMounted(() => {
  fetchForms()
})
</script>

<style scoped>
.center {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  margin-top: 50px;
  margin-bottom: 50px;
}

.form-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 16px;
  max-width: 1200px;
  width: 100%;
}

.card-link {
  text-decoration: none;
  color: inherit;
  transition: transform 0.2s ease-in-out;
}

.card-link:hover {
  transform: scale(1.02);
}

.search-container {
  margin-bottom: 20px;
  width: 100%;
  max-width: 600px;
  position: relative;
}

.search-input {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.search-icon {
  position: absolute;
  right: -10px;
  top: 50%;
  transform: translateY(-50%);
  color: #888;
  pointer-events: none; /* Ensures the icon doesn't interfere with input interactions */
}
</style>
