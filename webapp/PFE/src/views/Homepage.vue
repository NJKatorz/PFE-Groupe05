<template>
  <v-app>
    <v-container>
      <HeroSection />
      <QuestionnaireSection />
      <WhyChooseSection />
    </v-container>
  </v-app>
</template>

<script>
import HeroSection from '../components/HeroSection.vue';
import QuestionnaireSection from '../components/QuestionnaireSection.vue';
import WhyChooseSection from '../components/WhyChooseSection.vue';

export default {
  name: 'App',
  components: {
    HeroSection,
    QuestionnaireSection,
    WhyChooseSection
  }
};
</script>

<style>

</style>
