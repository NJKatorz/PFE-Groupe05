import api from './api';

const FormsService = {
  // Récupérer tous les produits
  getAllFormClients() {
    return api.get('/forms');
  },

    // Récupérer un produit par son id
    getFormClientById(companyId) {
      return api.get(`/forms/${companyId}`);
    },
};

export default FormsService;