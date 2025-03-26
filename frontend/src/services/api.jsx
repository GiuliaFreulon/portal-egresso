import axios from 'axios';

// Crie uma instância do Axios com configurações padrão
const API_BASE_URL = import.meta.env.VITE_API_URL || window.location.origin;

const api = axios.create({
    baseURL: API_BASE_URL, // URL do backend
    // http://localhost:8080
    headers: {
        'Content-Type': 'application/json',
    },
});

// Interceptor para adicionar o token JWT automaticamente
api.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

// Interceptor para tratar erros globais
api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response.status === 401) {
            // Token inválido/expirado - redirecione para login
            window.location = '/';
        }
        return Promise.reject(error);
    }
);

export default api;