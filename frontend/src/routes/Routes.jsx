import {Route, Routes} from 'react-router-dom';
import {AuthProvider} from "../contexts/AuthContext.jsx";
import PrivateRoute from "./PrivateRoute.jsx";

// public imports
import PublicLayout from "../layouts/publico/PublicLayout.jsx";
import PublicHome from "../pages/Publico/Home/Home.jsx";
import PublicDepoimentos from "../pages/Publico/Depoimentos/Depoimentos.jsx";
import PublicListagemEgressos from "../pages/Publico/Egressos/Listagem/Listagem.jsx";
import PublicPerfilEgresso from "../pages/Publico/Egressos/Perfil/Perfil.jsx";
import PublicOportunidades from "../pages/Publico/Oportunidades/Oportunidades.jsx";
import PublicRelatorios from "../pages/Publico/Relatorios/Relatorios.jsx";
import EgressoLogin from "../pages/Egresso/Login/Login.jsx";
import CoordenadorLogin from "../pages/Coordenador/Login/Login.jsx";
import AcessoNegado from "../pages/Publico/AcessoNegado/AcessoNegado.jsx";

//egresso imports
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
import CoordenadorEdicao from "../pages/Coordenador/Edicao/Edicao.jsx";
import CoordenadorDashboard from "../pages/Coordenador/Dashboard/Dashboard.jsx";
import CoordenadorEgressoGerenciamento from "../pages/Coordenador/Egresso/Gerenciamento/Gerenciamento.jsx";
import CoordenadorEgressoCadastro from "../pages/Coordenador/Egresso/Cadastro/Cadastro.jsx";
import CoordenadorEgressoAtualizacao from "../pages/Coordenador/Egresso/Atualizacao/Atualizacao.jsx";
import CoordenadorCursoGerenciamento from "../pages/Coordenador/Curso/Gerenciamento/Gerenciamento.jsx";
import CoordenadorCursoCadastro from "../pages/Coordenador/Curso/Cadastro/Cadastro.jsx";
import CoordenadorCursoAtualizacao from "../pages/Coordenador/Curso/Atualizacao/Atualizacao.jsx";
import CoordenadorDiscussaoGerenciamento from "../pages/Coordenador/Discussoes/Discussoes.jsx";
import CoordenadorDepoimentoHomologacao from "../pages/Coordenador/Depoimentos/Depoimentos.jsx";
import CoordenadorOportunidadeHomologacao from "../pages/Coordenador/Oportunidades/Oportunidades.jsx";
import LoginLayout from "../layouts/publico/LoginLayout.jsx";
import CoordenadorLayout from "../layouts/coordenador/CoordenadorLayout.jsx";
import EgressoLayout from "../layouts/egresso/EgressoLayout.jsx";


const AppRoutes = () => {
    return (
        <AuthProvider>
        <Routes>

            {/*Rotas Públicas*/}
            <Route element={<PublicLayout usuario={'PUBLICO'}/>}>
                <Route path="/" element={<PublicHome />} />
                <Route path="/depoimentos" element={<PublicDepoimentos />} />
                <Route path="/egressos" element={<PublicListagemEgressos />} />
                <Route path="/egressos/perfil/:id" element={<PublicPerfilEgresso />} />
                <Route path="/oportunidades" element={<PublicOportunidades />} />
                <Route path="/relatorios" element={<PublicRelatorios />} />
                <Route path="/acesso-negado" element={<AcessoNegado />} />
            </Route>

            {/*Rotas de login*/}
            <Route element={<LoginLayout usuario={'COORDENADOR'}/>}>
                <Route path="/coordenador/login" element={<CoordenadorLogin />} />
            </Route>
            <Route element={<LoginLayout usuario={'EGRESSO'}/>}>
                <Route path="/egresso/login" element={<EgressoLogin />} />
            </Route>

            {/*Rotas do Egresso*/}
            <Route element={<PrivateRoute allowedRoles={"ROLE_EGRESSO"}/>}>
                <Route element={<EgressoLayout />} >
                    <Route path="/egresso/dashboard" element={<EgressoDashboard />} />
                    <Route path="/egresso/discussao/gerenciamento" element={<EgressoDiscussaoGerenciamento />} />
                    <Route path="/egresso/discussao/criacao" element={<EgressoDiscussaoCriacao />} />
                    <Route path="/egresso/discussao/listagem" element={<EgressoDiscussaoListagem />} />
                    <Route path="/egresso/depoimento/gerenciamento" element={<EgressoDepoimentoGerenciamento />} />
                    <Route path="/egresso/depoimento/envio" element={<EgressoDepoimentoEnvio />} />
                    <Route path="/egresso/oportunidade/gerenciamento" element={<EgressoOportunidadeGerenciamento />} />
                    <Route path="/egresso/oportunidade/envio" element={<EgressoOportunidadeEnvio />} />
                    <Route path="/egresso/edicao" element={<EgressoEdicao />} />
                    <Route path="/egresso/discussao" element={<EgressoDiscussao />} />
                    {/*Rotas Públicas para Egresso*/}
                    <Route path="/egresso/home" element={<PublicHome />} />
                    <Route path="/egresso/depoimentos" element={<PublicDepoimentos />} />
                    <Route path="/egresso/egressos" element={<PublicListagemEgressos />} />
                    <Route path="/egresso/egressos/perfil/:id" element={<PublicPerfilEgresso />} />
                    <Route path="/egresso/oportunidades" element={<PublicOportunidades />} />
                    <Route path="/egresso/relatorios" element={<PublicRelatorios />} />
                </Route>
            </Route>

            {/*Rotas do Coordenador*/}
            <Route element={<PrivateRoute allowedRoles={"ROLE_COORDENADOR"}/>}>
                <Route element={<CoordenadorLayout />}>
                    <Route path="/coordenador/edicao" element={<CoordenadorEdicao />} />
                    <Route path="/coordenador/dashboard" element={<CoordenadorDashboard />} />
                    <Route path="/coordenador/egresso/gerenciamento" element={<CoordenadorEgressoGerenciamento />} />
                    <Route path="/coordenador/egresso/cadastro" element={<CoordenadorEgressoCadastro />} />
                    <Route path="/coordenador/egresso/atualizacao/:id" element={<CoordenadorEgressoAtualizacao />} />
                    <Route path="/coordenador/curso/gerenciamento" element={<CoordenadorCursoGerenciamento />} />
                    <Route path="/coordenador/curso/cadastro" element={<CoordenadorCursoCadastro />} />
                    <Route path="/coordenador/curso/atualizacao/:id" element={<CoordenadorCursoAtualizacao />} />
                    <Route path="/coordenador/discussao/gerenciamento" element={<CoordenadorDiscussaoGerenciamento />} />
                    <Route path="/coordenador/depoimento/homologacao" element={<CoordenadorDepoimentoHomologacao />} />
                    <Route path="/coordenador/oportunidade/homologacao" element={<CoordenadorOportunidadeHomologacao />} />
                    {/*Rotas Públicas para Coordenador*/}
                    <Route path="/coordenador/home" element={<PublicHome />} />
                    <Route path="/coordenador/depoimentos" element={<PublicDepoimentos />} />
                    <Route path="/coordenador/egressos" element={<PublicListagemEgressos />} />
                    <Route path="/coordenador/egressos/perfil/:id" element={<PublicPerfilEgresso />} />
                    <Route path="/coordenador/oportunidades" element={<PublicOportunidades />} />
                    <Route path="/coordenador/relatorios" element={<PublicRelatorios />} />
                </Route>
            </Route>

        </Routes>
        </AuthProvider>
    );
};

export  default AppRoutes;