import React, {useEffect, useState} from 'react';
import './Edicao.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCloudArrowUp} from "@fortawesome/free-solid-svg-icons";
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


    const handleSubmit = async () => {

        const formData = {
            nome,
            email,
            senha,
            descricao,
            foto,
            curriculo,
            linkedin,
            github,
        };

        console.log('Dados para atualização', formData);

        try {
            setLoading(true);
            const reponse = await api.put(`/api/egresso/${user.id}`, formData);

            console.log(reponse.data);

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

                            {loading ? (
                                <div className="chart-skeleton" style={{marginTop: '1rem', height: '1.5rem', width: '80%'}}>
                                    <div className="skeleton-loader"></div>
                                </div>
                            ) : (
                                <button
                                    className="formulario-button"
                                    type="button"
                                    onClick={() => handleSubmit('coordenador')}>
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