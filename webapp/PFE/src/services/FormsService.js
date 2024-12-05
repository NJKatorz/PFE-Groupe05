import api from './api';

const FormsService = {
  // Récupérer tous les produits
  getAllFormClients() {
    return api.get('/forms');
  },
};

export default FormsService;