import React, {useEffect, useState} from 'react';
import './Atualizacao.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faSquarePlus} from "@fortawesome/free-solid-svg-icons";
import {useParams} from "react-router-dom";
import api from "../../../../services/api.jsx";
import {useAuth} from "../../../../contexts/AuthContext.jsx";

const Atualizacao = () => {
    const { id } = useParams();
    const { user } = useAuth()

    const [nome, setNome] = useState('');
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const [cursos, setCursos] = useState([]);
    const [currentCurso, setCurrentCurso] = useState('');
    const [currentAnoInicio, setCurrentAnoInicio] = useState('');
    const [currentAnoFim, setCurrentAnoFim] = useState('');
    const [cursosAdicionados, setCursosAdicionados] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const fetchEgresso = async () => {
            try {
                const response = await api.get(`/api/egresso/buscarPorId/${id}`);

                setNome(response.data.nome);
                setEmail(response.data.email);

            } catch (error) {
                console.log("Erro ao buscar egresso:", error);
            }
        }

        const fetchCursos = async () => {
            try {
                const response = await api.get(`/api/curso/listarTodos`);
                setCursos(response.data.filter(
                    (curso) => curso.coordenador.id_coordenador === user.id
                ));
            } catch (error) {
                console.error("Erro ao buscar cursos:", error);
            }
        };

        fetchEgresso();
        fetchCursos();
    },[id])

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

    const handleSubmit = async () => {
        const formData = {
            nome,
            email,
            senha
        };

        console.log('Dados para atualização:', formData);

        try {
            //atualiza egresso
            setLoading(true);
            const response = await api.put(`/api/egresso/${id}`, formData);
            console.log(response.data);
            const egressoID = response.data?.id_egresso;

            //associa cursos
            for (const curso of cursosAdicionados) {
                const responseCurso = await api.post(`/api/coordenador/associarCursoAEgresso/${egressoID}/${curso.cursoId}`, {
                    anoInicio: curso.anoInicio,
                    anoFim: curso.anoFim,
                });
            }

            //reseta campos após cadastro
            setNome('');
            setEmail('');
            setSenha('');
            setCursosAdicionados([]);
            alert("Egresso atualizado com sucesso");

        }catch(error) {
            alert("falha no cadastro: " + error.response?.data.senha);
            console.log('Falha no cadastro: ', error.response?.data || error.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="main__container">
            <section className="coordenador-atualizacao-egresso">
                <h1 className="line-text">Atualizar Egresso</h1>
                <div className="coordenador-atualizacao-egresso-frame">
                    <form onSubmit={handleSubmit} className="coordenador-atualizacao-egresso-form">

                        <div className="">
                            <label htmlFor="nome" className="coordenador-atualizacao-egresso-label">Nome Completo*</label>
                            <input
                                type="text"
                                id="nome"
                                name="nome"
                                value={nome}
                                onChange={(e) => setNome(e.target.value)}
                            />
                        </div>

                        <div className="">
                            <label htmlFor="email" className="coordenador-atualizacao-egresso-label">E-mail*</label>
                            <input
                                type="email"
                                id="email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                            />
                        </div>

                        <div className="">
                            <label htmlFor="senha" className="coordenador-atualizacao-egresso-label">Senha*</label>
                            <input
                                type="password"
                                id="senha"
                                value={senha}
                                onChange={(e) => setSenha(e.target.value)}
                            />
                        </div>

                        <div className="">
                            <label htmlFor="curso" className="coordenador-atualizacao-egresso-label">Curso*</label>

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
                                <label htmlFor="anoInicio" className="coordenador-atualizacao-egresso-label">Ano de Início*</label>
                                <input
                                    type="number"
                                    id="anoInicio"
                                    value={currentAnoInicio}
                                    onChange={(e) => setCurrentAnoInicio(e.target.value)}
                                />
                            </div>

                            <div className="ano-input">
                                <label htmlFor="anoFim" className="coordenador-atualizacao-egresso-label">Ano de Término</label>
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
                            {loading ? (
                                <div className="chart-skeleton" style={{marginTop: '1rem', height: '1.5rem', width: '80%'}}>
                                    <div className="skeleton-loader"></div>
                                </div>
                            ) : (
                                <button
                                    className="formulario-button"
                                    type="button"
                                    onClick={() => handleSubmit()}>
                                    Confirmar
                                </button>
                            )}
                        </div>
                    </form>
                </div>
            </section>
        </div>
    );
};

export default Atualizacao;