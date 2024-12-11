<template>
    <div class="dashboard p-8">
      <h1 class="dashboard-title">Tableau de bord administrateur</h1>
      <div class="stats-grid">
        <div v-for="(stat, index) in stats" :key="index" class="card">
          <div class="card-header">
            <h3 class="card-title">{{ stat.title }}</h3>
            <component :is="stat.icon" class="card-icon" />
          </div>
          <div class="card-content">
            <div class="stat-value">{{ stat.value }}</div>
            
          </div>
        </div>
      </div>
      <div class="dashboard-grid">
        <div class="card chart-card">
          <div class="card-header">
            <h3 class="card-title">Aperçu</h3>
          </div>
          <div class="card-content">
            <table class="chart-table">
              <thead>
                <tr>
                  <th>Mois</th>
                  <th>Total</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="item in chartData" :key="item.name">
                  <td>{{ item.name }}</td>
                  <td>{{ item.total }}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import { Users, Activity, BarChart2, TrendingUp } from 'lucide-vue-next';
  import FormsService from '@/services/FormsService';
  import CompaniesService from '@/services/CompaniesService';
  
  const stats = ref([]);
  
  const initializeStats = async () => {
    try {
      const submittedForms = (await FormsService.getNumberOfFormsSubmitted()).data;
      const formsInProgress = (await FormsService.getNumberOfFormsInProgress()).data;
      const averageESG = (await FormsService.getAverageScoreESG()).data;
      const numberOfCompanies = (await CompaniesService.getAll()).data.length;
  
      stats.value = [
        {
          title: "Entreprises inscrites",
          value: numberOfCompanies,
          change: "+180 depuis le mois dernier",
          icon: Users,
        },
        {
          title: "Questionnaires soumis",
          value: submittedForms,
          change: "+201 depuis le mois dernier",
          icon: Activity,
        },
        {
          title: "Questionnaires en cours de complétetion",
          // title: "Taux de complétion",
          value: formsInProgress, // Valeur statique ou calculée séparément
          change: "+5% depuis le mois dernier",
          icon: BarChart2,
        },
        {
          title: "Score moyen ESG",
          value: Number(averageESG).toFixed(2) + "%",
          change: "+0.3 depuis le mois dernier",
          icon: TrendingUp,
        },
      ];
    } catch (error) {
      console.error("Erreur lors de l'initialisation des statistiques :", error);
    }
  };
  
  onMounted(() => {
    initializeStats();
  });
  </script>
  
  
  <style scoped>
  .dashboard {
    background-color: #f0f0f0;
  }
  
  .dashboard-title {
    font-size: 2rem;
    font-weight: bold;
    text-align: center;
    margin-bottom: 1.5rem;
    color: #004851;
  }
  
  .stats-grid {
    display: grid;
    gap: 1rem;
    grid-template-columns: repeat(1, 1fr);
  }
  
  @media (min-width: 768px) {
    .stats-grid {
      grid-template-columns: repeat(2, 1fr);
    }
  }
  
  @media (min-width: 1024px) {
    .stats-grid {
      grid-template-columns: repeat(4, 1fr);
    }
  }
  
  .dashboard-grid {
    display: grid;
    gap: 1rem;
    margin-top: 1rem;
    grid-template-columns: 1fr;
  }
  
  @media (min-width: 768px) {
    .dashboard-grid {
      grid-template-columns: 4fr 3fr;
    }
  }
  
  .card {
    background-color: white;
    border-radius: 0.5rem;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    padding: 1rem;
  }
  
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 0.5rem;
  }
  
  .card-title {
    font-size: 0.875rem;
    font-weight: 500;
    color: #333;
  }
  
  .card-icon {
    width: 1rem;
    height: 1rem;
    color: #666;
  }
  
  .card-content {
    padding-top: 0.5rem;
  }
  
  .stat-value {
    font-size: 1.5rem;
    font-weight: bold;
    color: #004851;
  }
  
  .stat-change {
    font-size: 0.75rem;
    color: #666;
  }
  
  .chart-card {
    grid-column: span 4;
  }
  
  .submissions-card {
    grid-column: span 3;
  }
  
  .submission-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0.5rem 0;
    border-bottom: 1px solid #eee;
  }
  
  .submission-item:last-child {
    border-bottom: none;
  }
  
  .submission-name {
    font-size: 0.875rem;
    font-weight: 500;
    color: #333;
  }
  
  .submission-details {
    font-size: 0.75rem;
    color: #666;
  }
  
  .submission-score {
    font-size: 0.875rem;
    font-weight: 500;
    color: #004851;
  }
  
  .chart-table {
    width: 100%;
    border-collapse: collapse;
  }
  
  .chart-table th,
  .chart-table td {
    padding: 0.5rem;
    text-align: left;
    border-bottom: 1px solid #eee;
  }
  
  .chart-table th {
    font-weight: bold;
    background-color: #f8f8f8;
  }
  </style>