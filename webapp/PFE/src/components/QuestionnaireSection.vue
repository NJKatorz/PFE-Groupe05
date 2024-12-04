<template>
  <div class="questionnaire-section">
    <div class="title-container">
      <div class="title">Nos Questionnaires</div>
    </div>

    <div class="questionnaire-column">
      <div class="questionnaire-card">
        <div class="headline">Questionnaire ESG</div>
        <div class="subheading">
          Mesurez vos performances environnementales, sociales et de gouvernance pour identifier les domaines d'amélioration et valoriser vos actions responsables.
        </div>
      </div>
    </div>

    <div class="questionnaire-column">
      <div class="questionnaire-card">
        <div class="headline">Questionnaire ODD</div>
        <div class="subheading">
          Alignez vos activités avec les ODD de l'ONU et contribuez à un avenir plus durable tout en renforçant votre impact positif sur la société.
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'QuestionnaireSection',
};
</script>

<style scoped>
.questionnaire-section {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  text-align: center;
}

.title-container {
  width: 100%;
  margin-bottom: 20px;
}

.title {
  font-family: 'Lalezar', sans-serif;
  font-weight: 400; /* Regular weight */
  font-size: 48px;  /* Font size */
  line-height: 75.22px; /* Line height */
  color: #004C53;
}

.questionnaire-column {
  flex: 0 0 auto;
  width: 586.23px;
  height: 272px;
  padding: 10px;
  box-sizing: border-box;
  text-align: center;
}

.questionnaire-card {
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 21px;
  background-color: #fff;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.headline {
  font-family: 'Inter', sans-serif;
  font-weight: 700; /* Bold */
  font-size: 36px;  /* Font size */
  line-height: 43.57px; /* Line height */
  margin-bottom: 10px; /* Spacing between headline and subheading */
}

.subheading {
  font-family: 'Inter', sans-serif;
  font-weight: 400; /* Regular weight */
  font-size: 26px;  /* Font size */
  line-height: 31.47px; /* Line height */
  color: #555;
}
</style>
