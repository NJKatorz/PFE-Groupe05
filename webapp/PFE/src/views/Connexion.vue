<template>
  <div class="min-h-screen bg-[#f0f4f5] p-6 flex flex-col justify-center items-center">
    <div class="w-full max-w-[560px] space-y-8">
      <!-- Header -->
      <div class="text-center">
        <h1 class="flex items-center justify-center gap-2 text-[32px] font-bold">
          Formulaires ESG & ODD
          <span class="text-base font-normal">par</span>
          <img src="../assets/img.png" alt="Better Business" class="h-10" />
        </h1>
        <p class="mt-4 text-lg text-gray-600">
          Découvrez votre impact et contribuez à un avenir durable grâce à notre plateforme d'évaluation innovante.
        </p>
      </div>

      <!-- Login Card -->
      <OurCard
        title="Se connecter"
        subtitle="Connectez vous pour accéder aux questionnaires"
      >
        <div class="px-8 py-6 space-y-6">
          <div class="space-y-2">
            <label class="block text-[#004851] text-base font-medium">Email</label>
            <div class="relative">
              <div class="absolute left-4 top-1/2 -translate-y-1/2">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" class="text-[#40867A]">
                  <path d="M22 8.608V16.75C22 17.4793 21.7103 18.1788 21.1945 18.6945C20.6788 19.2103 19.9793 19.5 19.25 19.5H4.75C4.02065 19.5 3.32118 19.2103 2.80546 18.6945C2.28973 18.1788 2 17.4793 2 16.75V8.608L11.526 13.926C11.6761 14.0207 11.8466 14.0705 12.021 14.0705C12.1954 14.0705 12.3659 14.0207 12.516 13.926L22 8.608ZM19.25 4.5C19.9584 4.50033 20.6393 4.77195 21.1552 5.26493C21.6712 5.7579 21.9734 6.42664 22.006 7.134L12 12.752L1.994 7.134C2.02657 6.42664 2.32884 5.7579 2.84478 5.26493C3.36072 4.77195 4.04163 4.50033 4.75 4.5H19.25Z" fill="currentColor"/>
                </svg>
              </div>
              <input
                type="email"
                placeholder="votreemail@exemple.com"
                class="w-full h-[52px] px-12 rounded-lg border-2 border-[#E2E8F0] text-[#4A5568] placeholder-[#A0AEC0] focus:border-[#40867A] focus:outline-none transition-colors"
              />
            </div>
          </div>

          <div class="space-y-2">
            <label class="block text-[#004851] text-base font-medium">Mot de passe</label>
            <div class="relative">
              <div class="absolute left-4 top-1/2 -translate-y-1/2">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" class="text-[#40867A]">
                  <path d="M12 14.5C11.602 14.5 11.2208 14.342 10.9395 14.0607C10.658 13.7794 10.5 13.3981 10.5 13C10.5 12.6019 10.658 12.2206 10.9395 11.9393C11.2208 11.658 11.602 11.5 12 11.5C12.3978 11.5 12.7794 11.658 13.0607 11.9393C13.342 12.2206 13.5 12.6019 13.5 13C13.5 13.3981 13.342 13.7794 13.0607 14.0607C12.7794 14.342 12.3978 14.5 12 14.5ZM18.5 19.5H5.5C5.10218 19.5 4.72064 19.342 4.43934 19.0607C4.15804 18.7794 4 18.3978 4 18V8C4 7.60218 4.15804 7.22064 4.43934 6.93934C4.72064 6.65804 5.10218 6.5 5.5 6.5H7V5C7 4.07174 7.36875 3.1815 8.02513 2.52513C8.6815 1.86875 9.57174 1.5 10.5 1.5H13.5C14.4283 1.5 15.3185 1.86875 15.9749 2.52513C16.6313 3.1815 17 4.07174 17 5V6.5H18.5C18.8978 6.5 19.2794 6.65804 19.5607 6.93934C19.842 7.22064 20 7.60218 20 8V18C20 18.3978 19.842 18.7794 19.5607 19.0607C19.2794 19.342 18.8978 19.5 18.5 19.5ZM8.5 6.5H15.5V5C15.5 4.40326 15.2629 3.83097 14.841 3.40901C14.419 2.98705 13.8467 2.75 13.25 2.75H10.75C10.1533 2.75 9.58097 2.98705 9.15901 3.40901C8.73705 3.83097 8.5 4.40326 8.5 5V6.5Z" fill="currentColor"/>
                </svg>
              </div>
              <input
                type="password"
                placeholder="Mot de passe"
                class="w-full h-[52px] px-12 rounded-lg border-2 border-[#E2E8F0] text-[#4A5568] placeholder-[#A0AEC0] focus:border-[#40867A] focus:outline-none transition-colors"
              />
            </div>
          </div>

          <button
            class="w-full h-[52px] flex items-center justify-center gap-2 rounded-lg bg-[#004851] text-white hover:bg-[#40867A] transition-colors"
          >
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M12 12C13.1935 12 14.3381 11.5259 15.182 10.682C16.0259 9.83807 16.5 8.69347 16.5 7.5C16.5 6.30653 16.0259 5.16193 15.182 4.31802C14.3381 3.47411 13.1935 3 12 3C10.8065 3 9.66193 3.47411 8.81802 4.31802C7.97411 5.16193 7.5 6.30653 7.5 7.5C7.5 8.69347 7.97411 9.83807 8.81802 10.682C9.66193 11.5259 10.8065 12 12 12ZM20.5 21C20.5 21 22 21 22 19.5C22 18 20.5 13.5 12 13.5C3.5 13.5 2 18 2 19.5C2 21 3.5 21 3.5 21H20.5Z" fill="white"/>
            </svg>
            Se connecter
          </button>

          <div class="text-center text-[15px]">
            <span class="text-[#1A202C]">Pas encore de compte? </span>
            <a href="#" class="text-[#40867A] hover:underline">
              Inscrivez vous <span class="text-[#40867A]">ici</span>
            </a>
          </div>
        </div>
      </OurCard>
    </div>
  </div>
</template>

<script setup>
import OurCard from '../components/OurCard.vue';
</script>

<style scoped>
/* Override OurCard styles to match the design */
:deep(.card) {
  border-radius: 24px !important;
  box-shadow: 0px 4px 25px rgba(0, 0, 0, 0.1) !important;
}

:deep(.card-header) {
  background: linear-gradient(90deg, #004851 0%, #40867A 100%) !important;
  padding: 2rem !important;
}

:deep(.card-title) {
  font-size: 2rem !important;
  font-weight: 600 !important;
}

:deep(.card-subtitle) {
  font-size: 1rem !important;
  opacity: 1 !important;
  margin-top: 0.5rem !important;
}
</style>
