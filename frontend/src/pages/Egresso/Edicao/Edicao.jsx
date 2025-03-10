import React, {useState} from 'react';
import './Edicao.css'

const Edicao = () => {

    const [nome, setNome] = useState('');
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const [descricao, setDescricao] = useState('');
    const [foto, setFoto] = useState(null);
    const [curriculo, setCurriculo] = useState(null);
    const [linkedin, setLinkedin] = useState('');
    const [github, setGithub] = useState('');


    const handleSubmit = () => {
        // Previne o comportamento padrão do formulário
        event.preventDefault();

        // Cria o objeto com os dados
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
                            <label htmlFor="foto" className="egresso-edicao-label">Foto de Perfil</label>
                            <input
                                type="file"
                                id="foto"
                                accept="image/*"
                                onChange={(e) => setFoto(e.target.files[0])}
                                className="egresso-edicao-arquivo-input"
                            />
                        </div>

                        <div>
                            <label htmlFor="curriculo" className="egresso-edicao-label">Currículo (PDF)</label>
                            <input
                                type="file"
                                id="curriculo"
                                accept="application/pdf"
                                onChange={(e) => setCurriculo(e.target.files[0])}
                                className="egresso-edicao-arquivo-input"
                            />
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
                            <button
                                className="formulario-button"
                                type="button"
                                onClick={() => handleSubmit('coordenador')}>
                                Confirmar
                            </button>
                        </div>

                    </form>
                </div>
            </section>
        </div>
    );
};

export default Edicao;