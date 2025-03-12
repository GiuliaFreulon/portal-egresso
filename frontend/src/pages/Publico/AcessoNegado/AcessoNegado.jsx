import { Link } from "react-router-dom"
import './AcessoNegado.css'

const AcessoNegado = () => {
    return (
        <div className="main__container">
            <section className="acesso-negado">
                <div className="acesso-negado-frame">
                    <h1>Acesso Negado</h1>
                    <p>Você não tem permissão para acessar esta página.</p>
                    <Link to="/">
                        <button className="formulario-button">Voltar para a página inicial</button>
                    </Link>
                </div>
            </section>
        </div>
    )
}

export default AcessoNegado

