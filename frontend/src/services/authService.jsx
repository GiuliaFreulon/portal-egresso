import api from './api';

export const login = async (credentials) => {
    try {
        const response = await api.post('/login', credentials);

        // Verifique a estrutura da resposta
        console.log('Resposta do login:', response);

        // Armazene o token e dados do usuário
        localStorage.setItem('token', response.data.token);
        localStorage.setItem('user', JSON.stringify(response.data.user));


        return response.data;
    } catch (error) {

        if (error.response) {
            // Erro com resposta do servidor (4xx, 5xx)
            throw {
                message: error.response?.data?.message || 'Erro desconhecido',
                status: error.response.status
            };
        } else if (error.request) {
            // Erro sem resposta (falha de rede)
            throw { message: 'Sem resposta do servidor', status: 503 };
        } else {
            // Erro de configuração
            throw { message: error.message, status: 500 };
        }
    }
};

export const getProfile = async () => {
    const response = await api.get('/api/egresso/buscarPorId/', localStorage.getItem(user.id));
    return response.data;
};