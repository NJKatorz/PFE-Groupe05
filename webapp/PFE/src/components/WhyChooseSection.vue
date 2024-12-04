<template>
  <v-row class="why-choose-section" justify="center" align="start">
    <!-- Titre -->
    <v-col cols="12" class="text-center">
      <div class="section-title">Pourquoi choisir nos services ?</div>
    </v-col>

    <!-- Cartes affichées horizontalement avec espacement -->
    <v-col
      v-for="(card, index) in cards"
      :key="index"
      cols="12" md="auto"
      class="why-choose-card-container"
    >
      <div class="why-choose-card">
        <div class="headline">{{ card.headline }}</div>
        <div class="subheading">{{ card.subheading }}</div>
      </div>
    </v-col>

    <!-- Bouton -->
    <v-col cols="12" class="text-center">
      <v-btn color="primary" class="mt-5">Commencer l'expérience</v-btn>
    </v-col>
  </v-row>
</template>

<script>
export default {
  name: 'WhyChooseSection',
  data() {
    return {
      cards: [
        {
          headline: "Fiabilité",
          subheading: "Évaluations basées sur des normes internationales reconnues pour des résultats précis et pertinents."
        },
        {
          headline: "Personnalisation",
          subheading: "Questionnaires adaptés à votre secteur d'activité et à la taille de votre entreprise."
        },
        {
          headline: "Analyse détaillée",
          subheading: "Obtenez des rapports détaillés et des recommandations concrètes pour améliorer vos performances."
        }
      ]
    };
  }
};
</script>

<style scoped>
.why-choose-section {
  display: flex;
  flex-direction: column;
  gap: 30px; /* Espacement entre les éléments */
}

.section-title {
  font-family: 'Lalezar', sans-serif;
  font-weight: 400;
  font-size: 48px;
  line-height: 75.22px;
  text-align: center;
  margin-top: 50px; /* Espace au-dessus du titre */
  margin-bottom: 20px; /* Espace sous le titre */
}

.why-choose-card-container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0 15px; /* Espacement horizontal entre les cartes */
}

.why-choose-card {
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #fff;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.headline {
  font-size: 1.75rem;
  font-weight: bold;
}

.subheading {
  font-size: 1rem;
  color: #555;
}
</style>
