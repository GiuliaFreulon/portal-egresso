import './App.css'

import {BrowserRouter as Router} from 'react-router-dom';
import AppRoutes from "./routes/Routes.jsx";

function App() {

  return (
      <Router>
          <AppRoutes />
      </Router>
  )
}

export default App
