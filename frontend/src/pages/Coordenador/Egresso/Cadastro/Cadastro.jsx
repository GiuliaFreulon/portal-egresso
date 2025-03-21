import React, {useEffect, useState} from 'react';
import './Cadastro.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faSquarePlus} from "@fortawesome/free-solid-svg-icons";
import api from "../../../../services/api.jsx";
import {useAuth} from "../../../../contexts/AuthContext.jsx";

const Cadastro = () => {
    const { user } = useAuth()

    const [nome, setNome] = useState('');
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const [cursos, setCursos] = useState([]);
    const [currentCurso, setCurrentCurso] = useState('');
    const [currentAnoInicio, setCurrentAnoInicio] = useState('');
    const [currentAnoFim, setCurrentAnoFim] = useState('');
    const [cursosAdicionados, setCursosAdicionados] = useState([]);

    useEffect(() => {
        const fetchCursos = async () => {
            try {
                const response = await api.get(`/api/curso/listarTodos`);
                setCursos(response.data.filter(
                    (curso) => curso.coordenador.id_coordenador === user.id
                ));
            } catch (error) {
                console.error("Erro ao buscar cursos:", error);
            } finally {
            }
        };

        fetchCursos();
    }, [user]);

    const handleAddCurso = (e) => {
        e.preventDefault();

        const anoAtual = new Date().getFullYear();

        if (!currentCurso || !currentAnoInicio) {
            alert('Por favor, selecione um curso e informe o ano de início');
            return;
        }

        if(currentAnoInicio < 1900 || currentAnoInicio > anoAtual) {
            alert('Ano de início inválido');
            return;
        }

        if(currentAnoFim && (currentAnoFim < currentAnoInicio || currentAnoFim > anoAtual)) {
            alert('Ano de término inválido');
            return;
        }

        if (!currentCurso || !currentAnoInicio) {
            alert('Por favor, selecione um curso e informe o ano de início');
            return;
        }

        const novoCurso = {
            cursoId: currentCurso,
            anoInicio: currentAnoInicio,
            anoFim: currentAnoFim
        };

        setCursosAdicionados([...cursosAdicionados, novoCurso]);

        // Limpa os campos
        setCurrentCurso('');
        setCurrentAnoInicio('');
        setCurrentAnoFim('');
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        if (cursosAdicionados.length === 0) {
            alert('Adicione pelo menos um curso');
            return;
        }

        const formData = {
            nome,
            email,
            senha
        };

        console.log('Dados para cadastro:', formData);

        try {
            //cadastra egresso
            const response = await api.post("/api/coordenador/cadastrarEgresso", formData);
            const egressoID = response.data.id_egresso;

            //associa cursos
            for (const curso of cursosAdicionados) {
                const responseCurso = await api.post(`/api/coordenador/associarCursoAEgresso/${egressoID}/${curso.cursoId}`, {
                    anoInicio: curso.anoInicio,
                    anoFim: curso.anoFim,
                });
            }

            setNome('');
            setEmail('');
            setSenha('');
            setCursosAdicionados([]);
            alert("Egresso cadastrado com sucesso");

        }catch(error) {
            console.log('Falha no cadastro', error.response?.data || error.message);
        }
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
                            <label htmlFor="curso" className="coordenador-cadastro-egresso-label">Curso*</label>
                            <select
                                id="curso"
                                value={currentCurso}
                                onChange={(e) => setCurrentCurso(e.target.value)}
                                className="drop-down-input"
                            >
                                <option value="">Selecione um curso</option>
                                {cursos.map((curso) => (
                                    <option key={curso.id} value={curso?.id_curso}>{curso?.nome}</option>
                                ))}
                            </select>
                        </div>

                        <div className="ano-input-container">
                            <div className="ano-input">
                                <label htmlFor="anoInicio" className="coordenador-cadastro-egresso-label">Ano de Início*</label>
                                <input
                                    type="number"
                                    id="anoInicio"
                                    value={currentAnoInicio}
                                    onChange={(e) => setCurrentAnoInicio(e.target.value)}
                                />
                            </div>

                            <div className="ano-input">
                                <label htmlFor="anoFim" className="coordenador-cadastro-egresso-label">Ano de Término</label>
                                <input
                                    type="number"
                                    id="anoFim"
                                    value={currentAnoFim}
                                    onChange={(e) => setCurrentAnoFim(e.target.value)}
                                />
                            </div>
                        </div>

                        <div className="cadastro-egresso-add-curso" onClick={handleAddCurso}>
                            <FontAwesomeIcon icon={faSquarePlus} className="add-icon-curso" />
                            <p>Adicionar curso</p>
                        </div>

                        {/* Lista de cursos adicionados */}
                        {cursosAdicionados.length > 0 && (
                            <div className="cursos-adicionados">
                                <h3>Cursos Adicionados:</h3>
                                <ul>
                                    {cursosAdicionados.map((curso, index) => (
                                        <li key={index}>
                                            {cursos.find(c => c.id_curso === curso.cursoId)?.nome} -
                                            Início: {curso.anoInicio} -
                                            Término: {curso.anoFim || 'Não informado'}
                                        </li>
                                    ))}
                                </ul>
                            </div>
                        )}

                        <div>
                            <button
                                className="formulario-button"
                                type="submit">
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