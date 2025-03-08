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


// Egresso pages


function App() {

  return (
      <div className="App">
          <Header
          cor="#22415A"
          />
          <CoordenadorEgressoGerenciamento />

      </div>
  )
}

export default App
