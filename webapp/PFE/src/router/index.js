import { createRouter, createWebHistory } from 'vue-router'
import ClientFormDetails from "@/views/ClientFormDetails.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'HomePage',
      component: ()=>import ("@/views/Homepage.vue"),
    },

    {
      path: "/new-questionnaire",
      name: "NewQuestionnaire",
      component: () => import('../views/NewQuestionnaire.vue'),
    },
    {
      path: '/questionnaire/:id',
      name: 'QuestionnaireForm',
      component: () => import('@/views/QuestionsByModule.vue')
    },
    {
      path: '/validation',
      name: 'Validation',
      component: () => import('@/views/ValidationPage.vue')
    },
    {
      path: '/EndQuestionnaire',
      name: 'EndQuestionnaire',
      component: () => import('@/views/EndQuestionnaire.vue')
    },
    {
      path: '/allClientForms',
      name: 'AllClientForms',
      component: () => import('@/views/AllClientForms.vue')
    },
    {
      path: '/Forms/:formId',
      name: 'ClientFormDetails',
      component: ClientFormDetails,
    },
    {
      path: '/login',
      name: 'Connexion',
      component: () => import('@/views/Connexion.vue')
    },
    {
      path: '/boardPage',
      name: 'BoardPage',
      component: () => import('@/views/BoardPage.vue')
    },
    {
      path: '/allCompanies',
      name: 'AllCompanies',
      component: ()=>import ("@/views/AllCompanies.vue"),
    },
    {
      path: '/score',
      name: 'score',
      component: ()=>import ("@/views/EsgScore.vue"),
    },
  ],
})

export default router
