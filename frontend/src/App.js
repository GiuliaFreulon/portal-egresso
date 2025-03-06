// import logo from './logo.svg';
// import './App.css';
//
// function App() {
//   return (
//     <div className="App">
//       <header className="App-header">
//         <img src={logo} className="App-logo" alt="logo" />
//         <p>
//           Edit <code>src/App.js</code> and save to reload.
//         </p>
//         <a
//           className="App-link"
//           href="https://reactjs.org"
//           target="_blank"
//           rel="noopener noreferrer"
//         >
//           Learn React
//         </a>
//       </header>
//     </div>
//   );
// }
//
// export default App;

import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './contexts/AuthContext';

// Layouts
import PublicoLayout from './layouts/PublicoLayout';
import EgressoLayout from './layouts/EgressoLayout';
import CoordenadorLayout from './layouts/CoordenadorLayout';

// Páginas Públicas
import PublicoHome from './pages/Publico/Home/Home';
import PublicoDepoimentos from './pages/Publico/Depoimentos/Depoimentos';
import EgressoPerfil from './pages/Publico/Egressos/Perfil/Perfil';

// Páginas de Egresso
import EgressoDashboard from './pages/Egresso/Dashboard/Dashboard';
import DepoimentoEnvio from './pages/Egresso/Depoimentos/Envio/Envio';

// Páginas de Coordenador
import CoordenadorDashboard from './pages/Coordenador/Dashboard/Dashboard';
import CursoCadastro from './pages/Coordenador/Curso/Cadastro/Cadastro';

// Componentes de Controle
import PrivateRoute from './routes/PrivateRoute';

function App() {
    return (
        <AuthProvider>
            <BrowserRouter>
                <Routes>
                    {/* Rotas Públicas */}
                    <Route element={<PublicoLayout />}>
                        <Route path="/" element={<PublicoHome />} />
                        <Route path="/depoimentos" element={<PublicoDepoimentos />} />
                        <Route path="/egressos/:id" element={<EgressoPerfil />} />
                    </Route>

                    {/* Rotas de Egresso */}
                    <Route element={<PrivateRoute allowedRoles={['Egresso']} />}>
                        <Route element={<EgressoLayout />}>
                            <Route path="/egresso/dashboard" element={<EgressoDashboard />} />
                            <Route path="/egresso/depoimentos/novo" element={<DepoimentoEnvio />} />
                            {/* Adicione outras rotas do Egresso aqui */}
                        </Route>
                    </Route>

                    {/* Rotas de Coordenador */}
                    <Route element={<PrivateRoute allowedRoles={['Coordenador']} />}>
                        <Route element={<CoordenadorLayout />}>
                            <Route path="/coordenador/dashboard" element={<CoordenadorDashboard />} />
                            <Route path="/coordenador/cursos/novo" element={<CursoCadastro />} />
                            {/* Adicione outras rotas do Coordenador aqui */}
                        </Route>
                    </Route>

                    {/* Rota de fallback */}
                    <Route path="*" element={<Navigate to="/" />} />
                </Routes>
            </BrowserRouter>
        </AuthProvider>
    );
}

export default App;