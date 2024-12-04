<template>
  <div class="hero-section">
    <div class="content-container">
      <h1 class="headline">Évaluez votre impact ESG & ODD</h1>
      <p class="subheading">
        Découvrez comment votre entreprise contribue au développement durable et à la responsabilité sociale grâce à notre plateforme d'évaluation innovante.
      </p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HeroSection'
};
</script>

<style scoped>
.hero-section {
  position: relative;
  height: 335px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: 'Inter', sans-serif;
  text-align: center;
  color: #002E37;
  overflow: hidden;
}

.hero-section::before {
  content: "";
  background-image: url('@/assets/Accueil.jpg');
  background-size: cover;
  background-position: center;
  opacity: 0.5; /* Set background image opacity */
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 0; /* Place the overlay behind the text */
}

.content-container {
  width: 1090px;
  height: 168px;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: relative; /* Ensure text is above the background */
  z-index: 2; /* Place text above the background image */
}

.headline {
  font-size: 40px;
  font-weight: 800;
  line-height: 48.41px;
}

.subheading {
  font-size: 24px;
  font-weight: 400;
  line-height: 29.05px;
}
</style>
