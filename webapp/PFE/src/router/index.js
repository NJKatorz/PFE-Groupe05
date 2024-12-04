import { createRouter, createWebHistory } from 'vue-router'
import NewQuestionnaire from '../views/NewQuestionnaire.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: NewQuestionnaire,
    },
   
    {
      path: "/new-questionnaire",
      name: "NewQuestionnaire",
      component: () => import('../views/NewQuestionnaire.vue'),
    },
    {
      path: '/questionnaire/:type',
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
  ],
})

export default router
