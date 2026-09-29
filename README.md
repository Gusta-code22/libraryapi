# 📚 LibraryAPI — Biblioteca Digital

Uma API RESTful desenvolvida em **Spring Boot** para gerenciar uma biblioteca digital, permitindo o cadastro, busca e gerenciamento de **livros**, **autores** e **usuários**, com autenticação segura via OAuth2 (Google e GitHub), **JWT** e armazenamento em **PostgreSQL**.

---

## 🎬 Demonstração

![LibraryAPI em ação](docs/images/demo.gif)

---

## 🌟 Funcionalidades principais

- Cadastro, atualização, exclusão e busca de **Livros**
- Cadastro, atualização, exclusão e busca de **Autores**
- Cadastro e gerenciamento de **Usuários**
- Autenticação via **OAuth2** (Google e GitHub) e **JWT**
- Persistência de dados em **PostgreSQL**
- Documentação interativa com **Swagger UI**
- Configuração flexível para diferentes ambientes (local e produção)
- Endpoints REST organizados e seguros

---

## 🧰 Tecnologias usadas

- Java 21 + Spring Boot 3.3
- Spring Data JPA com Hibernate
- Spring Security com OAuth2 (Client e Authorization Server) e JWT
- PostgreSQL
- MapStruct e Lombok
- Thymeleaf
- Spring Boot Actuator
- Springdoc OpenAPI (Swagger UI)
- Maven
- Deploy na Render

---

## 🗂️ Estrutura do projeto

```
libraryapi/
├── docs/
│   └── images/           # GIF de demonstração usado no README
├── src/
│   ├── main/
│   │   ├── java/         # código-fonte (controllers, services, repositories, entities)
│   │   └── resources/    # application.yml e configurações
│   └── test/             # testes
├── pom.xml               # dependências e configuração do Maven
├── LICENSE               # licença MIT
└── README.md
```

---

## 🚀 Como rodar localmente

### Pré-requisitos

- Java 21 instalado
- PostgreSQL rodando (local ou remoto)
- Maven instalado

### Passos

1. Clone o repositório:

```bash
   git clone https://github.com/Gusta-code22/libraryapi.git
   cd libraryapi
```

2. Configure as variáveis de ambiente criando um arquivo `.env` na raiz:

```env
   DATASOURCE_URL=jdbc:postgresql://<host>:5432/<database>
   DATASOURCE_USERNAME=<usuario>
   DATASOURCE_PASSWORD=<senha>

   GOOGLE_CLIENTID=<seu-client-id-google>
   GOOGLE_CLIENT_SECRET=<seu-secret-google>
   GITHUB_CLIENTID=<seu-client-id-github>
   GITHUB_CLIENT_SECRET=<seu-secret-github>

   SPRING_PROFILES_ACTIVE=default
```

3. Execute a aplicação:

```bash
   mvn spring-boot:run
```

4. A API estará disponível em `http://localhost:8080`.

---

## 📖 Documentação interativa (Swagger)

Com a aplicação rodando, acesse:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🔐 Exemplo de uso autenticado

1. Faça login pelo navegador:
   - Google: `http://localhost:8080/oauth2/authorization/google`
   - GitHub: `http://localhost:8080/oauth2/authorization/github`

2. Após o login, obtenha o token JWT: `<explique aqui como o token é retornado no seu projeto>`.

3. Envie o token no header `Authorization` das requisições:

```bash
   # Listar livros
   curl -H "Authorization: Bearer <SEU_TOKEN>" http://localhost:8080/livros

   # Cadastrar um autor
   curl -X POST http://localhost:8080/autores \
     -H "Authorization: Bearer <SEU_TOKEN>" \
     -H "Content-Type: application/json" \
     -d '{"nome": "Machado de Assis", "dataNascimento": "1839-06-21", "nacionalidade": "Brasileira"}'
```

### Endpoints principais

| Método | Rota        | Descrição      |
|--------|-------------|----------------|
| GET    | `/livros`   | Lista livros   |
| GET    | `/autores`  | Lista autores  |
| GET    | `/usuarios` | Lista usuários |

---

## ☁️ Deploy na Render

1. Faça push do código para o GitHub.
2. Na Render, crie um **Web Service** apontando para a branch `main`.
3. Configure as mesmas variáveis de ambiente do `.env`.
4. Configure os comandos:
   - **Build:** `./mvnw clean package`
   - **Start:** `java -jar target/libraryapi-0.0.1-SNAPSHOT.jar`
5. Configure o health check no caminho `/healthz`.

Sua API ficará disponível em um domínio público da Render.

---

## 🔍 Como funciona o fluxo básico

1. Usuários autenticam via Google/GitHub usando OAuth2.
2. Usuários autenticados podem criar, atualizar, buscar e deletar livros e autores.
3. Os dados são persistidos no PostgreSQL configurado.
4. A aplicação roda localmente ou na nuvem.

---

## 💡 Dicas para evoluir

- Busca avançada por título, autor, categoria etc.
- Paginação e ordenação nos endpoints
- Frontend para facilitar o uso
- Testes automatizados e integração contínua
- Roles (admin e usuário comum) para controle de acesso

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## ⭐ Agradecimentos

Obrigado por visitar este projeto! Se curtir, deixe uma estrela ⭐ no GitHub e compartilhe com a comunidade.
