<template>
  <section class="why-choose-section">
    <h2 class="section-title">Pourquoi choisir notre plateforme?</h2>

    <div class="features-container">
      <div class="feature-card" v-for="(feature, index) in features" :key="index">
        <h3 class="feature-title">{{ feature.title }}</h3>
        <p class="feature-text">{{ feature.description }}</p>
      </div>
    </div>

    <router-link :to="ctaLink" class="cta-button">
      Commencer l'expérience
      <span class="arrow">→</span>
    </router-link>
  </section>
</template>

<script setup>
import { ref, computed } from 'vue';
import { isAuthenticated, getAuthenticatedUser } from '../services/auths';



const features = ref([
  {
    title: "Fiabilité",
    description: "Évaluations basées sur des normes internationales reconnues pour des résultats précis et pertinents."
  },
  {
    title: "Personnalisation",
    description: "Questionnaires adaptés à votre secteur d'activité et à la taille de votre entreprise."
  },
  {
    title: "Analyse détaillée",
    description: "Obtenez des rapports détaillés et des recommandations concrètes pour améliorer vos performances."
  }
]);

const ctaLink = computed(() => {
  if (!isAuthenticated()) {
    return '/login';
  }

  const user = getAuthenticatedUser();
  if (!user) return '/login';

  const isCompany = localStorage.getItem('company') !== null;
  if (isCompany) {
    return '/new-questionnaire';
  }

  return '/boardPage';
});
</script>

<style scoped>
.why-choose-section {
  padding: 30px 0;
  text-align: center;
  background-color: #013238;
  margin-top: 80px;
}

.section-title {
  color: #ffffff;
  font-size: 2.5rem;
  font-weight: 700;
  margin-bottom: 3rem;
  position: relative;
  display: inline-block;
}

.features-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
  max-width: 1200px;
  margin: 0 auto 3rem;
  padding: 0 1rem;
}

.feature-card {
  background: white;
  border-radius: 12px;
  padding: 2rem;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
  transition: transform 0.3s ease;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: left;
}

.feature-card:hover {
  transform: translateY(-5px);
}

.feature-title {
  color: #004D56;
  font-size: 30px;
  font-weight: 600;
  margin-bottom: 1rem;
}

.feature-text {
  color: #4A5568;
  line-height: 1.6;
  font-size: 20px;
}

.cta-button {
  background: white;
  color:#004D56 ;
  border: none;
  padding: 1rem 2.5rem;
  border-radius: 50px;
  font-size: 1.125rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  display: inline-flex;
  align-items: center;
  gap: 0.5rem;
  text-decoration: none;
}

.cta-button:hover {
  background: #b5cdbf;
  transform: translateY(-2px);
}

.arrow {
  font-size: 1.25rem;
  transition: transform 0.3s ease;
}

.cta-button:hover .arrow {
  transform: translateX(4px);
}

@media (max-width: 768px) {
  .section-title {
    font-size: 2rem;
  }

  .features-container {
    grid-template-columns: 1fr;
  }

  .feature-card {
    padding: 1.5rem;
  }
}
</style>

