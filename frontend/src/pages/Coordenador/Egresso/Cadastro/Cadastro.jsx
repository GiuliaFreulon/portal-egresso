import React, {useState} from 'react';
import './Cadastro.css'

const Cadastro = () => {

    const [nome, setNome] = useState('');
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const [curso, setCurso] = useState('');
    const [anoInicio, setAnoInicio] = useState('');
    const [anoFim, setAnoFim] = useState('');

    // Lista de cursos exemplo
    const cursos = [
        { id: 1, nome: 'Desenvolvimento Web'},
        { id: 2, nome: 'Ciência de Dados'},
        { id: 3, nome: 'Design UX/UI'},
        { id: 4, nome: 'Marketing Digital'},
    ];

    const handleSubmit = (userType) => {
        // Previne o comportamento padrão do formulário
        event.preventDefault();

        // Cria o objeto com os dados
        const formData = {
            nome,
            email,
            senha,
            curso,
            anoInicio,
            anoFim,
            userType // 'coordenador' ou 'egresso'
        };

        // Simula o envio do JSON (substitua por fetch/axios)
        console.log('Dados enviados:', JSON.stringify(formData, null, 2));

        // Aqui você faria a chamada à API:
        // fetch('/api/login', {
        //   method: 'POST',
        //   headers: { 'Content-Type': 'application/json' },
        //   body: JSON.stringify(formData)
        // })
    };

    return (
        <div className="main__container">
            <section className="coordenador-cadastro-egresso">
                <h1 className="line-text">Cadastrar Egresso</h1>
                <div className="coordenador-cadastro-egresso-frame">
                    <form onSubmit={handleSubmit} className="coordenador-cadastro-egresso-form">

                        <div className="">
                            <label htmlFor="nome" className="coordenador-cadastro-egresso-label">Nome Completo*</label>
                            <input
                                type="text"
                                id="nome"
                                name="nome"
                                value={nome}
                                onChange={(e) => setNome(e.target.value)}
                                required
                            />
                        </div>

                        <div className="">
                            <label htmlFor="email" className="coordenador-cadastro-egresso-label">E-mail*</label>
                            <input
                                type="email"
                                id="email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                required
                            />
                        </div>

                        <div className="">
                            <label htmlFor="senha" className="coordenador-cadastro-egresso-label">Senha*</label>
                            <input
                                type="password"
                                id="senha"
                                value={senha}
                                onChange={(e) => setSenha(e.target.value)}
                                required
                            />
                        </div>

                        <div className="">
                            <label htmlFor="curso" className="coordenador-cadastro-egresso-label">Curso</label>

                            <select
                                id="curso"
                                value={curso}
                                onChange={(e) => setCurso(e.target.value)}
                                required
                                className="drop-down-input"
                            >
                                <option value="">Selecione um curso</option>
                                {cursos.map((curso) => (
                                    <option key={curso.id} value={curso.id}>{curso.nome}</option>
                                    ))
                                }
                            </select>
                        </div>

                        <div className="ano-input-container">
                            <div className="ano-input">
                                <label htmlFor="anoInicio" className="coordenador-cadastro-egresso-label">Ano de Início*</label>
                                <input
                                    type="number"
                                    id="anoInicio"
                                    value={anoInicio}
                                    onChange={(e) => setAnoInicio(e.target.value)}
                                    required
                                />
                            </div>

                            <div className="ano-input">
                                <label htmlFor="anoFim" className="coordenador-cadastro-egresso-label">Ano de Início*</label>
                                <input
                                    type="number"
                                    id="anoFim"
                                    value={anoFim}
                                    onChange={(e) => setAnoFim(e.target.value)}
                                />
                            </div>
                        </div>

                        <div>
                            <button
                                className="formulario-button"
                                type="button"
                                onClick={() => handleSubmit('coordenador')}>
                                Cadastrar
                            </button>
                        </div>
                    </form>
                </div>
            </section>
        </div>
    );
};

export default Cadastro;