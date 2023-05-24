-- Sequência pessoa
CREATE SEQUENCE IF NOT EXISTS seq_pessoa;
CREATE TABLE IF NOT EXISTS pessoa (
    id BIGINT NOT NULL DEFAULT nextval('seq_pessoa'),
    nome VARCHAR(100) NOT NULL,
    datanascimento TIMESTAMP,
    cpf VARCHAR(14),
    funcionario BOOLEAN,
    cargo VARCHAR(100)
);

---- Sequência projeto
CREATE SEQUENCE IF NOT EXISTS seq_projeto;
CREATE TABLE IF NOT EXISTS projeto (
    id BIGINT NOT NULL DEFAULT nextval('seq_projeto'),
    nome VARCHAR(100) NOT NULL,
    data_inicio TIMESTAMP,
    data_previsao_fim TIMESTAMP,
    data_fim TIMESTAMP,
    descricao VARCHAR(200) NOT NULL,
    status VARCHAR(45) NOT NULL,
    orcamento NUMERIC(15,2),
    risco VARCHAR(45),
    idgerente BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS membros (
    idprojeto bigint NOT NULL,
    idpessoa bigint NOT NULL
);



