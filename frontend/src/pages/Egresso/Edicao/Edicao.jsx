import React, {useEffect, useState} from 'react';
import './Edicao.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCloudArrowUp, faSquarePlus} from "@fortawesome/free-solid-svg-icons";
import {useAuth} from "../../../contexts/AuthContext.jsx";
import api from "../../../services/api.jsx";

const Edicao = () => {
    const { user } = useAuth()

    const [nome, setNome] = useState('');
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const [descricao, setDescricao] = useState('');
    const [foto, setFoto] = useState(null);
    const [curriculo, setCurriculo] = useState(null);
    const [linkedin, setLinkedin] = useState('');
    const [github, setGithub] = useState('');

    const [currentCargo, setCurrentCargo] = useState('');
    const [currentCargoDescricao, setCurrentCargoDescricao] = useState('');
    const [currentAnoInicio, setCurrentAnoInicio] = useState('');
    const [currentAnoFim, setCurrentAnoFim] = useState('');
    const [cargosAdicionados, setCargosAdicionados] = useState([]);

    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await api.get(`api/egresso/buscarPorId/${user.id}`);
                console.log(response.data);
                setNome(response.data.nome);
                setEmail(response.data.email);
                if (response.data.descricao !== null) {
                    setDescricao(response.data.descricao);
                }
                if (response.data.linkedin !== null) {
                    setLinkedin(response.data.linkedin);
                }
                if (response.data.github !== null) {
                    setGithub(response.data.github);
                }
            } catch (error) {
                console.log(error);
            }
        }

        fetchData();
    }, [user]);

    const handleAddCargo = (e) => {
        e.preventDefault();

        const anoAtual = new Date().getFullYear();

        if (!currentCargo || !currentAnoInicio || !currentCargoDescricao) {
            alert('Por favor, informe o nome da empresa, a descrição e o ano de início');
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

        if (!currentCargo || !currentAnoInicio || !currentCargoDescricao) {
            alert('Por favor, informe o nome da empresa, a descrição e o ano de início');
            return;
        }

        const novoCargo = {
            local: currentCargo,
            anoInicio: currentAnoInicio,
            anoFim: currentAnoFim,
            descricao: currentCargoDescricao
        };

        setCargosAdicionados([...cargosAdicionados, novoCargo]);

        // Limpa os campos
        setCurrentCargo('');
        setCurrentCargoDescricao('');
        setCurrentAnoInicio('');
        setCurrentAnoFim('');
    };

    const handleSubmit = async () => {

        const formData = new FormData();
        formData.append("nome", nome);
        formData.append("email", email);
        formData.append("senha", senha);
        formData.append("descricao", descricao);
        if (foto) formData.append("foto", foto);  // Foto selecionada
        if (curriculo) formData.append("curriculo", curriculo);  // Currículo selecionado
        formData.append("linkedin", linkedin);
        formData.append("github", github);

        console.log('Dados para atualização', formData);

        try {
            setLoading(true);
            const response = await api.put(`/api/egresso/${user.id}`, formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                }
            });

            //associa cursos
            for (const cargo of cargosAdicionados) {
                const responseCargo = await api.post(`/api/cargo/${user.id}`, {
                    local: cargo.local,
                    anoInicio: cargo.anoInicio,
                    anoFim: cargo.anoFim,
                    descricao: cargo.descricao,
                });
            }

            // reseta campos após atualização
            setNome('');
            setEmail('');
            setSenha('');
            setDescricao('');
            setLinkedin('');
            setGithub('');
            alert("Egresso atualizado com sucesso!");
        } catch (error) {
            alert("Falha ao atualizar" + error.message);
            console.log('Falha ao atualizar', error.response?.data || error.message);
        } finally {
            setLoading(false);
        }

    };

    return (
        <div className="main__container">
            <section className="egresso-edicao">
                <h1 className="line-text">Atualizar Dados</h1>
                <div className="egresso-edicao-frame">
                    <form onSubmit={handleSubmit} className="egresso-edicao-form">

                        <div>
                            <label htmlFor="nome" className="egresso-edicao-label">Nome Completo</label>
                            <input
                                type="text"
                                id="nome"
                                name="nome"
                                value={nome}
                                onChange={(e) => setNome(e.target.value)}
                                required
                            />
                        </div>

                        <div>
                            <label htmlFor="email" className="egresso-edicao-label">E-mail</label>
                            <input
                                type="email"
                                id="email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                required
                            />
                        </div>

                        <div>
                            <label htmlFor="senha" className="egresso-edicao-label">Senha</label>
                            <input
                                type="password"
                                id="senha"
                                value={senha}
                                onChange={(e) => setSenha(e.target.value)}
                                required
                            />
                        </div>

                        <div>
                            <label htmlFor="descricao" className="egresso-edicao-label">Descrição</label>
                            <textarea
                                id="descricao"
                                name="descricao"
                                value={descricao}
                                onChange={(e) => setDescricao(e.target.value)}
                                required
                                className="texto-input"
                            />
                        </div>

                        <div>
                            <label htmlFor="foto" className="egresso-edicao-label">
                                Foto de Perfil
                            </label>

                            <div className="egresso-edicao-arquivo-input">
                                <label htmlFor="foto" className="arquivo-label">
                                    <div className="arquivo-label-content">
                                        <span className="arquivo-icone">
                                            <FontAwesomeIcon icon={faCloudArrowUp} className="add-icon-curso" />
                                        </span>
                                        <p>Clique aqui para selecionar o arquivo</p>
                                    </div>
                                </label>
                                <input
                                    type="file"
                                    id="foto"
                                    accept="image/*"
                                    onChange={(e) => setFoto(e.target.files[0])}
                                />
                                {foto && <p className="arquivo-nome">{foto.name}</p>}
                            </div>
                        </div>

                        <div>
                            <label htmlFor="curriculo" className="egresso-edicao-label">
                                Currículo (PDF)
                            </label>

                            <div className="egresso-edicao-arquivo-input">
                                <label htmlFor="curriculo" className="arquivo-label">
                                    <div className="arquivo-label-content">
                                        <span className="arquivo-icone">
                                            <FontAwesomeIcon icon={faCloudArrowUp} className="add-icon-curso" />
                                        </span>
                                        <p>Clique aqui para selecionar o arquivo</p>
                                    </div>
                                </label>
                                <input
                                    type="file"
                                    id="curriculo"
                                    accept="application/pdf"
                                    onChange={(e) => setCurriculo(e.target.files[0])}
                                />
                                {curriculo && <p className="arquivo-nome">{curriculo.name}</p>}
                            </div>
                        </div>

                        <div>
                            <label htmlFor="linkedin" className="egresso-edicao-label">LinkedIn</label>
                            <input
                                type="url"
                                id="linkedin"
                                name="linkedin"
                                value={linkedin}
                                pattern="https://.*"
                                onChange={(e) => setLinkedin(e.target.value)}
                                required
                            />
                        </div>

                        <div>
                            <label htmlFor="github" className="egresso-edicao-label">GitHub</label>
                            <input
                                type="url"
                                id="github"
                                name="github"
                                value={github}
                                pattern="https://.*"
                                onChange={(e) => setGithub(e.target.value)}
                                required
                            />
                        </div>

                        <div>
                            <h2 className="line-text cargos-titulo">Cargos</h2>
                            <label htmlFor="currentCargo" className="egresso-edicao-label">Nome da Empresa</label>
                            <input
                                type="text"
                                id="currentCargo"
                                name="currentCargo"
                                value={currentCargo}
                                onChange={(e) => setCurrentCargo(e.target.value)}
                            />
                        </div>

                        <div className="">
                            <label htmlFor="currentCargoDescricao" className="egresso-edicao-label">Descrição</label>

                            <textarea
                                id="currentCargoDescricao"
                                name="currentCargoDescricao"
                                value={currentCargoDescricao}
                                onChange={(e) => setCurrentCargoDescricao(e.target.value)}
                                className="texto-input"
                            />
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

                        <div className="cadastro-egresso-add-curso" onClick={handleAddCargo}>
                            <FontAwesomeIcon icon={faSquarePlus} className="add-icon-curso" />
                            <p>Adicionar cargo</p>
                        </div>

                        {/* Lista de cursos adicionados */}
                        {cargosAdicionados.length > 0 && (
                            <div className="cursos-adicionados">
                                <h3>Cargos Adicionados:</h3>
                                <ul>
                                    {cargosAdicionados.map((cargo, index) => (
                                        <li key={index}>
                                            {cargo.local} -
                                            Início: {cargo.anoInicio} -
                                            Término: {cargo.anoFim || 'Não informado'}
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

export default Edicao;