import api from './api'

const FormsService = {
  // Récupérer tous les formulaires des entreprises
  getAllFormClients() {
    return api.get('/forms')
  },

  // Récupérer le formulaire d'une entreprise par son id
  getFormClientByFormId(formId) {
    return api.get(`/forms/${formId}`)
  },

  // Récupérer une entreprise par son id
  getFormClientByCompanyId(companyId) {
    return api.get(`/companies/${companyId}`)
  },
  login(crendentials) {
    return api.post('/auth/login', crendentials)
  },
  getFormByCompanyId(companyId) {
    return api.get(`/forms/company/${companyId}`)
  },

  getNumberOfRegisteredUsers() {
    return api.get('/users/count')
  },

  getNumberOfFormsSubmitted() {
    return api.get('/forms/formsSubmitted')
  },

  getAverageScoreESG() {
    return api.get('/forms/averageScoreESG')
  },

  getNumberOfFormsInProgress() {
    return api.get('/forms/formsInProgress')
  },
}

export default FormsService
