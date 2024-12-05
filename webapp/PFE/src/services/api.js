import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 5000,
});

// const getAllClientForms = axios.get('http://localhost:8080/api');

export default api;
