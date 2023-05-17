# Gestão de Portfólio

Este projeto é um sistema CRUD de gestão de portfólio, desenvolvido utilizando as seguintes tecnologias e práticas:

- **Arquitetura em camadas**: Organiza o código em camadas distintas (Controller, Service, Repository) para facilitar a manutenção e a escalabilidade.
- **Docker Compose**: Ferramenta para criação e gerenciamento de ambientes multi-container com Docker, facilitando a configuração do ambiente de desenvolvimento.
- **Domain-Driven Design (DDD)**: Metodologia de design de software que enfatiza a modelagem do domínio para um melhor entendimento e expressividade do negócio.
- **Flyway**: Ferramenta de controle de versão de banco de dados, que permite a criação e migração automática de esquemas.
- **Java 11**: Linguagem de programação e orientada a objetos.
- **JSP (JavaServer Pages)**: Tecnologia para criação de páginas web dinâmicas, permitindo a mistura de código Java com HTML.
- **OpenApi**: É uma especificação que promove a padronização, documentação e interoperabilidade de APIs RESTful.
- **Postgres**: Banco de dados relacional utilizado para armazenar os dados do sistema.
- **SonarQube**: Plataforma de análise estática de código que verifica a qualidade do código, identifica problemas e fornece métricas para melhorar a qualidade do software.
- **Solid**: Princípios de design de software que promovem código limpo, coesão, baixo acoplamento e facilidade de extensão.
- **Spring Boot**: Framework Java que simplifica o desenvolvimento de aplicações robustas.
- **Spring Data**: Biblioteca do Spring que facilita a interação com o banco de dados, oferecendo abstrações e recursos úteis.
- **Testes com JUnit**: Validar o comportamento do sofware e ajuda na qualidade do código em unidades individuais.

## Regras de negócio:
- O sistema deve permitir o cadastro (inserção, exclusão, alteração e consulta) de projetos. Para cada projeto devem ser informados: nome, data de início, gerente responsável, previsão de término, data real de término, orçamento total, descrição e status.
- Os projetos devem ser classificados em: baixo risco, médio risco e alto risco. A classificação de risco não é cadastrada no sistema.
- A cada instante, o projeto deve estar em um status específico e único. Os status possíveis não são cadastrados no sistema e são: em análise, análise realizada, análise aprovada, iniciado, planejado, em andamento, encerrado, cancelado.
- Se um projeto foi mudado o status para iniciado, em andamento ou encerrado não pode mais ser excluído.
- O sistema não deve permitir o cadastro de um novo membro diretamente. Deve ser provida funcionalidade via web service, contendo nome e atribuição (cargo).
- O sistema deve permitir associar membros aos projetos que têm atribuição funcionário.


## Pré-requisitos

- Java JDK 11.0.1
- Docker
- Docker Compose
- Apache Maven 3.8.3

## Executando o projeto

1. Clone este repositório para o seu ambiente local.
2. Na raiz do projeto, execute o comando `docker-compose up` para iniciar o ambiente com o banco de dados Postgres e o SonarQube.
3. Abra o projeto em sua IDE de preferência.
4. Execute a classe `PortfolioApplication.java` para iniciar a aplicação.

Depois é só acessar a url `http://localhost:8080`.

## Testes unitários

Foram desenvolvidos testes unitários utilizando o framework JUnit. Os testes garantem a qualidade e a integridade do código, verificando se cada unidade (métodos, classes) funciona corretamente. Para executar os testes, basta executar as classes testes.

## Banco de dados

O Flyway foi utilizado para o controle de versão do banco de dados. As migrações estão localizadas em `src/main/resources/db/migration`. Com o Flyway, é possível criar, modificar e versionar o esquema do banco de dados de forma automatizada.

## Análise de código - SonarQube

O SonarQube é uma plataforma de análise estática de código que verifica a qualidade do código-fonte, identifica problemas, vulnerabilidades.

1. Acesse o SonarQube no seu navegador em `http://localhost:9000`
2. Faça login utilizando o usuário "admin" e a senha "admin".
3. Para executar o projeto no sonar, execute o comando: mvn sonar:sonar

## Documentação do projeto - OpenApi

Especificação que descreve e padroniza a documentação e a integração de APIs REST. Ele permite garantir a qualidade do código e facilita o compartilhamento de contratos entre equipes. Para acessar a documentação da API do projeto, basta acessar a URL: `http://localhost:8080/swagger-ui/index.html` com a aplicação em execução.

### Tela OpenApi
![alt tag](https://github.com/xcarlosr/portfolio/blob/main/imagens/open-api-swagger.png?raw=true)



## Telas da Aplicação

### Tela Home
![alt tag](https://github.com/xcarlosr/portfolio/blob/main/imagens/home.png?raw=true)


### Tela Projeto
![alt tag](https://github.com/xcarlosr/portfolio/blob/main/imagens/projetos.png?raw=true)


### Tela Projeto - Pesquisa pelo id
![alt tag](https://github.com/xcarlosr/portfolio/blob/main/imagens/projeto_pesquisa_id.png?raw=true)


### Tela Projeto - Pesquisa por nome e descrição
![alt tag](https://github.com/xcarlosr/portfolio/blob/main/imagens/projeto_pesquisa_descricao.png?raw=true)


### Tela Projeto - Adicionar
![alt tag](https://github.com/xcarlosr/portfolio/blob/main/imagens/projeto_adicionar.png?raw=true)


### Tela Projeto - Editar
![alt tag](https://github.com/xcarlosr/portfolio/blob/main/imagens/projeto_editar.png?raw=true)


### Tela Projeto - Deletar
![alt tag](https://github.com/xcarlosr/portfolio/blob/main/imagens/projeto_deletar.png?raw=true)


### Tela Pessoa
![alt tag](https://github.com/xcarlosr/portfolio/blob/main/imagens/pessoas.png?raw=true)


### Tela Membro
![alt tag](https://github.com/xcarlosr/portfolio/blob/main/imagens/membros.png?raw=true)