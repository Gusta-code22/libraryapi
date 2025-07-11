# 📚 LibraryAPI — Biblioteca Digital

Uma API RESTful desenvolvida em **Spring Boot** para gerenciar uma biblioteca digital, permitindo o cadastro, busca e gerenciamento de **livros**, **autores** e **usuários**, com autenticação segura via OAuth2 (Google e GitHub) e armazenamento em banco **PostgreSQL**.

---

## 🌟 Funcionalidades principais

- Cadastro, atualização, exclusão e busca de **Livros**  
- Cadastro, atualização, exclusão e busca de **Autores**  
- Cadastro e gerenciamento de **Usuários**  
- Autenticação via **OAuth2** com Google e GitHub e JWT  
- Persistência de dados em **PostgreSQL**  
- Configuração flexível para diferentes ambientes (local, produção)  
- Endpoints REST organizados e seguros

---

## 🧰 Tecnologias usadas

- Java 17 + Spring Boot  
- Spring Data JPA com Hibernate  
- Spring Security com OAuth2 e JWT  
- PostgreSQL  
- Maven  
- Deploy configurado para Render (ou outro ambiente em nuvem)

---

## 🚀 Como rodar localmente

### Pré-requisitos

- Java 17 instalado  
- PostgreSQL rodando (local ou remoto)  
- Maven instalado  

### Passos

1. Clone o repositório  
   ```bash
   git clone https://github.com/Gusta-code22/libraryapi.git
   cd libraryapi
Configure as variáveis de ambiente criando um arquivo .env na raiz:

DATASOURCE_URL=jdbc:postgresql://<host>:5432/<database>
DATASOURCE_USERNAME=<usuario>
DATASOURCE_PASSWORD=<senha>

GOOGLE_CLIENTID=<seu-client-id-google>
GOOGLE_CLIENT_SECRET=<seu-secret-google>
GITHUB_CLIENTID=<seu-client-id-github>
GITHUB_CLIENT_SECRET=<seu-secret-github>

SPRING_PROFILES_ACTIVE=default
Execute a aplicação:

bash
Copiar
Editar
mvn spring-boot:run
Teste endpoints:

Livros: GET http://localhost:8080/livros

Autores: GET http://localhost:8080/autores

Usuários: GET http://localhost:8080/usuarios

Login Google: http://localhost:8080/oauth2/authorization/google

Login GitHub: http://localhost:8080/oauth2/authorization/github

☁️ Deploy na Render
Faça push no GitHub do seu código

No Render crie um Web Service apontando para a branch main

Configure as variáveis de ambiente iguais às do .env

Configure os comandos de build/start:

Build: ./mvnw clean package

Start: java -jar target/libraryapi-0.0.1-SNAPSHOT.jar

Configure o health check no caminho /healthz

Sua API estará disponível publicamente em um domínio do Render

📁 Estrutura do projeto (exemplo simplificado)
bash
Copiar
Editar
src/
 ├─ main/
 │   ├─ java/io/github/Gusta_code22/libraryapi/
 │   │   ├─ Application.java           # Inicializador da aplicação
 │   │   ├─ config/                   # Configurações (security, db)
 │   │   ├─ controller/               # Controladores REST
 │   │   │   ├─ LivroController.java
 │   │   │   ├─ AutorController.java
 │   │   │   └─ UsuarioController.java
 │   │   ├─ model/                    # Entidades JPA
 │   │   │   ├─ Livro.java
 │   │   │   ├─ Autor.java
 │   │   │   └─ Usuario.java
 │   │   ├─ repository/               # Interfaces JPA Repository
 │   │   ├─ service/                  # Regras de negócio
 │   │   └─ security/                 # Configuração OAuth2
 │   └─ resources/
 │       └─ application.yml           # Configurações via variáveis
🔍 Como funciona o fluxo básico
Usuários autenticam via Google/GitHub usando OAuth2

Usuários podem criar, atualizar, buscar e deletar livros e autores

A persistência é feita no banco PostgreSQL configurado

A aplicação pode rodar localmente ou ser deployada na nuvem

💡 Dicas para evoluir
Adicionar busca avançada por título, autor, categoria, etc

Implementar paginação e ordenação nos endpoints REST

Criar um frontend para facilitar uso

Adicionar testes automatizados e integração contínua

Implementar roles (admin, usuário comum) para controle de acesso


⭐ Agradecimentos
Obrigado por visitar este projeto!
Se curtir, deixa uma estrela ⭐ no GitHub e compartilhe com a comunidade!

