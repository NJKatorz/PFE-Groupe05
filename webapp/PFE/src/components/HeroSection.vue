<template>
  <section class="hero-section">
    <div class="content-container">
      <h1 class="headline">Évaluez votre impact ESG & ODD</h1>
      <p class="subheading">
        Découvrez comment votre entreprise contribue au développement durable et à la responsabilité sociale grâce à notre plateforme d'évaluation innovante.
      </p>
    </div>
  </section>
</template>

<script>
export default {
  name: 'HeroSection'
};
</script>

<style scoped>
/* Styles spécifiques pour la section Hero */
.hero-section {
  position: relative;
  height: 305px;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  background-image: url('../assets/Accueil.jpg');
  background-size: cover;
  background-position: center;
}

.hero-section::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(255, 255, 255, 0.5);
}

.content-container {
  position: relative;
  max-width: 1090px;
  padding: 0 20px;
  z-index: 1;
}

.headline {
  font-size: 40px;
  font-weight: 800;
  line-height: 1.2;
  margin-bottom: 20px;
}

.subheading {
  font-size: 24px;
  line-height: 1.4;
}
</style>
