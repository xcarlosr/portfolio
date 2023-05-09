
-- Pessoas
INSERT INTO pessoa (id, nome, datanascimento, cpf, funcionario)
VALUES (nextval('seq_pessoa'), 'Maria Santos', '1985-02-15', '222.222.222-22', true);

INSERT INTO pessoa (id, nome, datanascimento, cpf, funcionario)
VALUES (nextval('seq_pessoa'), 'Pedro Souza', '1992-05-10', '333.333.333-33', true);

INSERT INTO pessoa (id, nome, datanascimento, cpf, funcionario)
VALUES (nextval('seq_pessoa'), 'Ana Oliveira', '1998-11-20', '444.444.444-44', true);

INSERT INTO pessoa (id, nome, datanascimento, cpf, funcionario)
VALUES (nextval('seq_pessoa'), 'Lucas Pereira', '1993-07-05', '555.555.555-55', false);

INSERT INTO pessoa (id, nome, datanascimento, cpf, funcionario)
VALUES (nextval('seq_pessoa'), 'Carolina Almeida', '1997-03-18', '666.666.666-66', true);

INSERT INTO pessoa (id, nome, datanascimento, cpf, funcionario)
VALUES (nextval('seq_pessoa'), 'Rafael Castro', '1994-09-22', '777.777.777-77', true);

-- Projetos
INSERT INTO projeto (id, nome, data_inicio, data_previsao_fim, data_fim, descricao, status, orcamento, risco, idgerente)
VALUES (nextval('seq_projeto'), 'Projeto A', '2023-01-01', '2023-03-31', null, 'Descrição do Projeto A', 'INICIADO', 10000.00, 'BAIXO_RISCO', 1);

INSERT INTO projeto (id, nome, data_inicio, data_previsao_fim, data_fim, descricao, status, orcamento, risco, idgerente)
VALUES (nextval('seq_projeto'), 'Projeto B', '2023-02-01', '2023-05-31', null, 'Descrição do Projeto B', 'PLANEJADO', 5000.00, 'MEDIO_RISCO', 1);

INSERT INTO projeto (id, nome, data_inicio, data_previsao_fim, data_fim, descricao, status, orcamento, risco, idgerente)
VALUES (nextval('seq_projeto'), 'Projeto C', '2023-03-01', '2023-07-31', null, 'Descrição do Projeto C', 'EM_ANDAMENTO', 8000.00, 'ALTO_RISCO', 1);

INSERT INTO projeto (id, nome, data_inicio, data_previsao_fim, data_fim, descricao, status, orcamento, risco, idgerente)
VALUES (nextval('seq_projeto'), 'Projeto D', '2023-04-01', '2023-06-30', null, 'Descrição do Projeto D', 'PLANEJADO', 3000.00, 'BAIXO_RISCO', 3);

INSERT INTO projeto (id, nome, data_inicio, data_previsao_fim, data_fim, descricao, status, orcamento, risco, idgerente)
VALUES (nextval('seq_projeto'), 'Projeto E', '2023-01-01', '2023-03-31', null, 'Descrição do Projeto E', 'ANALISE_APROVADA', 10000.00, 'BAIXO_RISCO', 1);

INSERT INTO projeto (id, nome, data_inicio, data_previsao_fim, data_fim, descricao, status, orcamento, risco, idgerente)
VALUES (nextval('seq_projeto'), 'Projeto F', '2023-02-01', '2023-05-31', null, 'Descrição do Projeto F', 'PLANEJADO', 5000.00, 'MEDIO_RISCO', 1);

INSERT INTO projeto (id, nome, data_inicio, data_previsao_fim, data_fim, descricao, status, orcamento, risco, idgerente)
VALUES (nextval('seq_projeto'), 'Projeto G', '2023-03-01', '2023-07-31', null, 'Descrição do Projeto G', 'EM_ANDAMENTO', 8000.00, 'ALTO_RISCO', 1);

INSERT INTO projeto (id, nome, data_inicio, data_previsao_fim, data_fim, descricao, status, orcamento, risco, idgerente)
VALUES (nextval('seq_projeto'), 'Projeto H', '2023-04-01', '2023-06-30', null, 'Descrição do Projeto H', 'PLANEJADO', 3000.00, 'BAIXO_RISCO', 2);

INSERT INTO projeto (id, nome, data_inicio, data_previsao_fim, data_fim, descricao, status, orcamento, risco, idgerente)
VALUES (nextval('seq_projeto'), 'Projeto I', '2023-01-01', '2023-03-31', null, 'Descrição do Projeto I', 'EM_ANDAMENTO', 10000.00, 'BAIXO_RISCO', 1);

INSERT INTO projeto (id, nome, data_inicio, data_previsao_fim, data_fim, descricao, status, orcamento, risco, idgerente)
VALUES (nextval('seq_projeto'), 'Projeto J', '2023-02-01', '2023-05-31', null, 'Descrição do Projeto J', 'PLANEJADO', 5000.00, 'MEDIO_RISCO', 1);

INSERT INTO projeto (id, nome, data_inicio, data_previsao_fim, data_fim, descricao, status, orcamento, risco, idgerente)
VALUES (nextval('seq_projeto'), 'Projeto K', '2023-03-01', '2023-07-31', null, 'Descrição do Projeto K', 'EM_ANDAMENTO', 8000.00, 'ALTO_RISCO', 1);

INSERT INTO projeto (id, nome, data_inicio, data_previsao_fim, data_fim, descricao, status, orcamento, risco, idgerente)
VALUES (nextval('seq_projeto'), 'Projeto L', '2023-04-01', '2023-06-30', '2023-06-30', 'Descrição do Projeto L', 'ENCERRADO', 3000.00, 'BAIXO_RISCO', 1);

INSERT INTO projeto (id, nome, data_inicio, data_previsao_fim, data_fim, descricao, status, orcamento, risco, idgerente)
VALUES (nextval('seq_projeto'), 'Projeto M', '2023-02-01', '2023-05-31', null, 'Descrição do Projeto M', 'PLANEJADO', 5000.00, 'MEDIO_RISCO', 6);

INSERT INTO projeto (id, nome, data_inicio, data_previsao_fim, data_fim, descricao, status, orcamento, risco, idgerente)
VALUES (nextval('seq_projeto'), 'Projeto N', '2023-03-01', '2023-07-31', null, 'Descrição do Projeto N', 'EM_ANDAMENTO', 8000.00, 'ALTO_RISCO', 1);

INSERT INTO projeto (id, nome, data_inicio, data_previsao_fim, data_fim, descricao, status, orcamento, risco, idgerente)
VALUES (nextval('seq_projeto'), 'Projeto P', '2023-04-01', '2023-06-30', null, 'Descrição do Projeto P', 'CANCELADO', 3000.00, 'BAIXO_RISCO', 1);


-- Membros dos projetos
-- Projeto 1
INSERT INTO membros (idprojeto, idpessoa)
VALUES (1, 1);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (1, 2);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (1, 3);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (1, 4);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (1, 5);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (1, 6);

-- Projeto 2
INSERT INTO membros (idprojeto, idpessoa)
VALUES (2, 1);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (2, 2);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (2, 3);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (2, 4);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (2, 5);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (2, 6);

-- Projeto 3
INSERT INTO membros (idprojeto, idpessoa)
VALUES (3, 1);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (3, 2);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (3, 3);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (3, 4);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (3, 5);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (3, 6);

-- Projeto 4
INSERT INTO membros (idprojeto, idpessoa)
VALUES (4, 1);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (4, 2);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (4, 3);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (4, 4);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (4, 5);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (4, 6);

-- Projeto 5
INSERT INTO membros (idprojeto, idpessoa)
VALUES (5, 1);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (5, 2);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (5, 3);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (5, 4);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (5, 5);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (5, 6);

-- Projeto 6
INSERT INTO membros (idprojeto, idpessoa)
VALUES (6, 1);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (6, 2);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (6, 3);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (6, 4);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (6, 5);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (6, 6);

-- Projeto 7
INSERT INTO membros (idprojeto, idpessoa)
VALUES (7, 1);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (7, 2);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (7, 3);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (7, 4);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (7, 5);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (7, 6);

-- Projeto 8
INSERT INTO membros (idprojeto, idpessoa)
VALUES (8, 1);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (8, 2);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (8, 3);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (8, 4);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (8, 5);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (8, 6);

-- Projeto 9
INSERT INTO membros (idprojeto, idpessoa)
VALUES (9, 1);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (9, 2);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (9, 3);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (9, 4);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (9, 5);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (9, 6);

-- Projeto 10
INSERT INTO membros (idprojeto, idpessoa)
VALUES (10, 1);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (10, 2);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (10, 3);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (10, 4);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (10, 5);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (10, 6);

-- Projeto 11
INSERT INTO membros (idprojeto, idpessoa)
VALUES (11, 1);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (11, 2);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (11, 3);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (11, 4);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (11, 5);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (11, 6);

-- Projeto 12
INSERT INTO membros (idprojeto, idpessoa)
VALUES (12, 1);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (12, 2);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (12, 3);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (12, 4);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (12, 5);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (12, 6);

-- Projeto 13
INSERT INTO membros (idprojeto, idpessoa)
VALUES (13, 1);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (13, 2);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (13, 3);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (13, 4);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (13, 5);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (13, 6);

-- Projeto 14
INSERT INTO membros (idprojeto, idpessoa)
VALUES (14, 1);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (14, 2);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (14, 3);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (14, 4);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (14, 5);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (14, 6);

-- Projeto 15
INSERT INTO membros (idprojeto, idpessoa)
VALUES (15, 1);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (15, 2);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (15, 3);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (15, 4);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (15, 5);

INSERT INTO membros (idprojeto, idpessoa)
VALUES (15, 6);