import {Route, Routes} from 'react-router-dom';
import {PrivateRoute} from "../contexts/AuthContext.jsx";

// public imports
import PublicHome from "../pages/Publico/Home/Home.jsx";
import PublicDepoimentos from "../pages/Publico/Depoimentos/Depoimentos.jsx";
import PublicListagemEgressos from "../pages/Publico/Egressos/Listagem/Listagem.jsx";
import PublicPerfilEgresso from "../pages/Publico/Egressos/Perfil/Perfil.jsx";
import PublicOportunidades from "../pages/Publico/Oportunidades/Oportunidades.jsx";
import PublicRelatorios from "../pages/Publico/Relatorios/Relatorios.jsx";
import LoginLayout from "../layouts/coordenador/LoginLayout.jsx";

//egresso imports
import EgressoLogin from "../pages/Egresso/Login/Login.jsx";
import EgressoDashboard from "../pages/Egresso/Dashboard/Dashboard.jsx";
import EgressoDiscussaoGerenciamento from "../pages/Egresso/Discussoes/Gerenciamento/Gerenciamento.jsx";
import EgressoDiscussaoCriacao from "../pages/Egresso/Discussoes/Criacao/Criacao.jsx";
import EgressoDiscussaoListagem from "../pages/Egresso/Discussoes/Listagem/Listagem.jsx";
import EgressoDepoimentoGerenciamento from "../pages/Egresso/Depoimentos/Gerenciamento/Gerenciamento.jsx";
import EgressoDepoimentoEnvio from "../pages/Egresso/Depoimentos/Envio/Envio.jsx";
import EgressoOportunidadeGerenciamento from "../pages/Egresso/Oportunidades/Gerenciamento/Gerenciamento.jsx";
import EgressoOportunidadeEnvio from "../pages/Egresso/Oportunidades/Envio/Envio.jsx";
import EgressoEdicao from "../pages/Egresso/Edicao/Edicao.jsx";
import EgressoDiscussao from "../pages/Egresso/Discussoes/Discussao/Discussao.jsx";

//coordenador imports

const AppRoutes = () => {
    return (
        <Routes>
            <Route element={<LoginLayout/>}>
                <Route path="/" element={<PublicHome />} />
                <Route path="/depoimentos" element={<PublicDepoimentos />} />
                <Route path="/egressos" element={<PublicListagemEgressos />} />
                <Route path="/egressos/perfil/:id" element={<PublicPerfilEgresso />} />
                <Route path="/oportunidades" element={<PublicOportunidades />} />
                <Route path="/relatorios" element={<PublicRelatorios />} />
                <Route path="/egresso_login" element={<EgressoLogin />} />
            </Route>

            <Route element={<PrivateRoute />}>
                <Route path="/egresso_dashboard" element={<EgressoDashboard />} />
            </Route>

        </Routes>
    );
};

export  default AppRoutes;