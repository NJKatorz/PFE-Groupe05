<template>
  <div class="questionnaire-section">
    <div class="title-container">
      <h1 class="section-title">Nos questionnaires</h1>
    </div>

    <div class="questionnaire-container">
      <div class="questionnaire-card">
        <div class="card-content">
          <div class="icon-title">
            <span class="icon">🍃</span>
            <h2 class="headline">Questionnaire ESG</h2>
          </div>
          <p class="subheading">
            Mesurez vos performances environnementales, sociales et de gouvernance pour identifier les domaines d'amélioration et valoriser vos actions responsables.
          </p>
        </div>
      </div>

      <div class="questionnaire-card">
        <div class="card-content">
          <div class="icon-title">
            <span class="icon">🌐</span>
            <h2 class="headline">Questionnaire ODD</h2>
          </div>
          <p class="subheading">
            Alignez vos activités avec les ODD de l'ONU et contribuez à un avenir plus durable <br> tout en renforçant votre impact positif sur la société.
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'QuestionnaireSection',
}
</script>

<style scoped>
.questionnaire-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 25px 16px;
  text-align: center;
}

.title-container {
  margin-bottom: 32px;
}

.title {
  font-family: 'Lalezar', sans-serif;
  font-weight: 400;
  font-size: 48px;
  line-height: 75px;
  color: #004C53;
}

.section-title {
  color: #004D56;
  font-size: 2.5rem;
  font-weight: 700;
  margin-bottom: 3rem;
  position: relative;
  display: inline-block;
}

.questionnaire-container {
  display: flex;
  justify-content: center;
  gap: 32px;
  max-width: 1200px;
  width: 100%;
}

.questionnaire-card {
  background-color: #FFFFFF;
  border-radius: 21px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  width: 506px;
  height: 202px;
  display: flex;
  align-items: center;
  padding-bottom: 3%;
}

.card-content {
  padding: 32px;
}

.icon-title {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 16px;
  padding-top: 10%;
}

.icon {
  font-size: 36px;
  margin-right: 12px;
}

.headline {
  font-family: 'Inter', sans-serif;
  font-weight: 600;
  font-size: 30px;
  line-height: 44px;
  color: #004D56;
  
}

.subheading {
  font-family: 'Inter', sans-serif;
  font-weight: 200;
  font-size: 20px;
  line-height: 31px;
  color: #555555;
  padding-bottom: 15px;
  
}

@media (max-width: 1200px) {
  .questionnaire-container {
    flex-direction: column;
    align-items: center;
  }

  .questionnaire-card {
    width: 100%;
    max-width: 586px;
    height: auto;
  }
}
</style>

