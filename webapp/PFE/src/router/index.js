import { createRouter, createWebHistory } from 'vue-router'
import Homepage from "@/views/Homepage.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'HomePage',
      //component: NewQuestionnaire,
      component: ()=>import ("@/views/Homepage.vue"),
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
    {
      path: '/score',
      name: 'Score',
      component:() => import('@/views/EsgScore.vue')
    }
  ],
})

export default router
