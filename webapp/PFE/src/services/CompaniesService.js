import api from './api';

const CompaniesService = {
  getAll() {
    return api.get('/companies');
  },
  validateCompany(companyId) {
    return api.post(`/companies/${companyId}/validate`);
  },
};

export default CompaniesService;
