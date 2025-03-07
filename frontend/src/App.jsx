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


// Egresso pages


function App() {

  return (
      <div className="App">
          <Header
          cor="#691F31"
          />
          <PublicOportunidades />
      </div>
  )
}

export default App
