# AgnelloAdmin

Uma aplicação web Java para gerenciamento de vinhos e administração de usuários.

## Visão Geral do Projeto

AgnelloAdmin é uma aplicação web construída usando tecnologias Java EE, projetada para gerenciar inventário de vinhos e administração de usuários. A aplicação utiliza JSP (JavaServer Pages) para o frontend e segue uma arquitetura MVC tradicional.

## Stack Tecnológico

- Java SE 21
- Apache Tomcat 11.0.4
- Jakarta EE 10 (Servlet API, JSP, JSTL)
- MySQL 8.0 ou superior

## Dependências do Projeto

O projeto utiliza as seguintes dependências principais:
 - jakarta.servlet.jsp.jstl-3.0.1
 - jakarta.servlet.jsp.jstl-api-3.0.2
 - mysql-connector-j-9.2.0
 - Bootstrap 5.3.3

## Estrutura do Projeto

```
AgnelloAdmin/
├── src/main/java/br/com/fiap/
│   ├── controller/    # Classes controladoras (Servlets)
│   ├── dao/          # Objetos de Acesso a Dados
│   ├── bean/         # Java Beans
│   ├── exception/    # Exceções personalizadas
│   └── factory/      # Classes fábrica
├── WebContent/
│   ├── *.jsp         # Páginas JSP
│   │   ├── header.jsp        # Cabeçalho comum
│   │   ├── menu.jsp          # Menu de navegação
│   │   ├── footer.jsp        # Rodapé comum
│   │   ├── index.jsp         # Página de login
│   │   ├── home.jsp          # Dashboard principal
│   │   ├── criarUsuario.jsp  # Formulário de cadastro de usuário
│   │   ├── editarUsuario.jsp # Formulário de edição de usuário
│   │   ├── listaUsuarios.jsp # Listagem de usuários
│   │   ├── criarVinho.jsp    # Formulário de cadastro de vinho
│   │   ├── editarVinho.jsp   # Formulário de edição de vinho
│   │   └── listaVinhos.jsp   # Listagem de vinhos
│   ├── resources/    # Recursos estáticos (CSS, JS, imagens)
│   ├── WEB-INF/      # Arquivos de configuração
│   └── META-INF/     # Metadados da aplicação
```

## Pré-requisitos

- Java SE 21 ou superior
- Apache Tomcat 11.0.4
- MySQL 8.0 ou superior
- Eclipse IDE (recomendado)

## Instruções de Configuração

1. **Configuração do Ambiente**
   - Instale o Java SE 21
   - Instale o Apache Tomcat 11.0.4
   - Instale o MySQL 8.0 ou superior
   - Instale o Eclipse IDE

2. **Configuração do Banco de Dados**
   - Crie um banco de dados MySQL chamado `meu_banco`
   - Configure as credenciais no arquivo `ConnectionFactory.java`:
     ```java
     private static final String URL = "jdbc:mysql://localhost:3306/meu_banco";
     private static final String USER = "root";
     private static final String PWD = "root";
     ```
   - Execute os scripts SQL para criar as tabelas:
     ```sql
     CREATE TABLE usuarios (
         id_usuario INT AUTO_INCREMENT PRIMARY KEY,
         nome VARCHAR(100) NOT NULL,
         email VARCHAR(100) NOT NULL UNIQUE,
         celular VARCHAR(20) NOT NULL UNIQUE,
         senha VARCHAR(100) NOT NULL,
         role INT NOT NULL,
         data_aniversario DATE NOT NULL
     );

     CREATE TABLE vinhos (
         idVinho INT AUTO_INCREMENT PRIMARY KEY,
         nomeVinho VARCHAR(100) NOT NULL,
         fotoVinho VARCHAR(255),
         preco DOUBLE NOT NULL,
         nomeVinicola VARCHAR(100) NOT NULL,
         cidade VARCHAR(100) NOT NULL,
         teorAlcoolico VARCHAR(20) NOT NULL,
         docura VARCHAR(20) NOT NULL,
         fotoBandeira VARCHAR(255),
         blend VARCHAR(100),
         quantidadeDisponivel INT NOT NULL
     );
     ```

3. **Importação do Projeto**
   - Clone o repositório
   - Importe o projeto no Eclipse 
   - Configure os facets do projeto para Dynamic Web Module

4. **Configuração do Servidor**
   - Adicione o Apache Tomcat 11.0.4 como servidor no Eclipse
   - Configure o projeto para usar o servidor Tomcat
   - Defina o ambiente de execução do servidor

5. **Executando a Aplicação**
   - Inicie o servidor Tomcat
   - Faça o deploy da aplicação
   - Acesse a aplicação através de: `http://localhost:8080/AgnelloAdmin`

## Funcionalidades

### Gerenciamento de Usuários
- **Criação de Usuários**
  - Cadastro de novos usuários com informações pessoais
  - Definição de níveis de acesso e permissões
  - Validação de dados de entrada

- **Edição de Usuários**
  - Atualização de informações do usuário
  - Alteração de senha
  
- **Listagem de Usuários**
  - Visualização de todos os usuários cadastrados

### Gerenciamento de Vinhos
- **Cadastro de Vinhos**
  - Inclusão de novos vinhos com detalhes completos
  - Upload de imagens do produto
  - Categorização por tipo, região e safra
  - Gestão de estoque e preços

- **Edição de Vinhos**
  - Atualização de informações do vinho
  - Modificação de preços e estoque
  - Alteração de imagens e descrições

- **Listagem de Vinhos**
  - Visualização de todos os vinhos cadastrados

## Arquitetura do Projeto

O projeto segue uma arquitetura MVC (Model-View-Controller) tradicional:
- **Model**: Java Beans no pacote `bean` (Usuario.java, Vinho.java)
- **View**: Arquivos JSP no diretório `WebContent` organizados em:
  - Templates comuns (header, menu, footer)
  - Páginas de dashboard (home.jsp)
  - Páginas de CRUD para usuários
  - Páginas de CRUD para vinhos
- **Controller**: Classes Servlet no pacote `controller` (LoginServlet, UsuarioServlet, VinhoServlet)
- **DAO**: Objetos de Acesso a Dados no pacote `dao` para operações de banco de dados

## Tratamento de Erros

O projeto implementa um sistema robusto de tratamento de erros:
- Classe `NotFoundException` para erros de recursos não encontrados
- Tratamento de exceções SQL
- Validação de dados de entrada
- Mensagens de erro amigáveis para o usuário

## Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo LICENSE para detalhes. 
