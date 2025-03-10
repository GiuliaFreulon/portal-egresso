import './App.css'

// Public pages
import Header from "./components/common/Header/Header.jsx";
import PublicHome from "./pages/Publico/Home/Home.jsx"
import PublicListagem from "./pages/Publico/Egressos/Listagem/Listagem.jsx";
import PublicPagination from "./components/common/Pagination/Pagination.jsx";
import PublicPerfil from "./pages/Publico/Egressos/Perfil/Perfil.jsx";
import PublicDepoimentos from "./pages/Publico/Depoimentos/Depoimentos.jsx";
import PublicRelatorios from "./pages/Publico/Relatorios/Relatorios.jsx";
import PublicOportunidades from "./pages/Publico/Oportunidades/Oportunidades.jsx";

// Coordenador pages
import CoordenadorLogin from "./pages/Coordenador/Login/Login.jsx";
import CoordenadorEdicao from "./pages/Coordenador/Edicao/Edicao.jsx";
import CoordenadorDashboard from "./pages/Coordenador/Dashboard/Dashboard.jsx";
import CoordenadorEgressoGerenciamento from "./pages/Coordenador/Egresso/Gerenciamento/Gerenciamento.jsx";
import CoordenadorEgressoCadastro from "./pages/Coordenador/Egresso/Cadastro/Cadastro.jsx";
import CoordenadorEgressoAtualizacao from "./pages/Coordenador/Egresso/Atualizacao/Atualizacao.jsx";
import CoordenadorCursoGerenciamento from "./pages/Coordenador/Curso/Gerenciamento/Gerenciamento.jsx";
import CoordenadorCursoCadastro from "./pages/Coordenador/Curso/Cadastro/Cadastro.jsx";
import CoordenadorCursoAtualizacao from "./pages/Coordenador/Curso/Atualizacao/Atualizacao.jsx";
import CoordenadorDiscussaoGerenciamento from "./pages/Coordenador/Discussoes/Discussoes.jsx";
import CoordenadorDepoimentoHomologacao from "./pages/Coordenador/Depoimentos/Depoimentos.jsx";
import CoordenadorOportunidadeHomologacao from "./pages/Coordenador/Oportunidades/Oportunidades.jsx";

// Egresso pages
import EgressoLogin from "./pages/Egresso/Login/Login.jsx";
import EgressoDashboard from "./pages/Egresso/Dashboard/Dashboard.jsx";
import EgressoDiscussaoGerenciamento from "./pages/Egresso/Discussoes/Gerenciamento/Gerenciamento.jsx";
import EgressoDiscussaoCriacao from "./pages/Egresso/Discussoes/Criacao/Criacao.jsx";
import EgressoDepoimentoGerenciamento from "./pages/Egresso/Depoimentos/Gerenciamento/Gerenciamento.jsx";
import EgressoDepoimentoEnvio from "./pages/Egresso/Depoimentos/Envio/Envio.jsx";
import EgressoOportunidadeGerenciamento from "./pages/Egresso/Oportunidades/Gerenciamento/Gerenciamento.jsx";
import EgressoOportunidadeEnvio from "./pages/Egresso/Oportunidades/Envio/Envio.jsx";
import EgressoDiscussaoListagem from "./pages/Egresso/Discussoes/Listagem/Listagem.jsx";



function App() {

  return (
      <div className="App">
          <Header usuario='EGRESSO' />

          {/*<PublicHome />*/}
          {/*<PublicListagem />*/}
          {/*<PublicPagination />*/}
          {/*<PublicPerfil />*/}
          {/*<PublicDepoimentos />*/}
          {/*<PublicRelatorios />*/}
          {/*<PublicOportunidades />*/}


          {/*<CoordenadorLogin />*/}
          {/*<CoordenadorEdicao />*/}
          {/*<CoordenadorDashboard />*/}
          {/*<CoordenadorEgressoGerenciamento />*/}
          {/*<CoordenadorEgressoCadastro />*/}
          {/*<CoordenadorEgressoAtualizacao />*/}
          {/*<CoordenadorCursoGerenciamento />*/}
          {/*<CoordenadorCursoCadastro />*/}
          {/*<CoordenadorCursoAtualizacao />*/}
          {/*<CoordenadorDiscussaoGerenciamento />*/}
          {/*<CoordenadorDepoimentoHomologacao />*/}
          {/*<CoordenadorOportunidadeHomologacao />*/}


          {/*<EgressoLogin />*/}
          {/*<EgressoDashboard />*/}
          {/*<EgressoDiscussaoGerenciamento />*/}
          {/*<EgressoDiscussaoCriacao />*/}
          {/*<EgressoDepoimentoGerenciamento />*/}
          {/*<EgressoDepoimentoEnvio />*/}
          {/*<EgressoOportunidadeGerenciamento />*/}
          <EgressoDiscussaoListagem />
      </div>
  )
}

export default App
