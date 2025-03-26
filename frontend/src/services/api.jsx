import axios from "axios";

const api = axios.create({
    baseURL: import.meta.env.VITE_API_URL || "http://portal-egresso.railway.internal", // URL do backend
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