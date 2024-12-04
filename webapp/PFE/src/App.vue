<template>
  <div class="app">
    <!-- Header -->
    <Header />

    <!-- Hero Section -->
    <HeroSection />

    <!-- Questionnaire Section -->
    <QuestionnaireSection />

    <!-- Why Choose Section -->
    <WhyChooseSection />

    <!-- Footer -->
    <Footer />
  </div>
</template>

<script>
import Header from './components/Header.vue';
import HeroSection from './components/HeroSection.vue';
import QuestionnaireSection from './components/QuestionnaireSection.vue';
import WhyChooseSection from './components/WhyChooseSection.vue';
import Footer from './components/Footer.vue';

export default {
  name: 'App',
  components: {
    Header,
    HeroSection,
    QuestionnaireSection,
    WhyChooseSection,
    Footer
  }
};
</script>

<style scoped>
/* Global Styles */
</style>
