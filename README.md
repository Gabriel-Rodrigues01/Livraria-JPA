📚 LivrariaJPA: Gerenciamento de Livros (CRUD)
Este projeto implementa um sistema simples de Gerenciamento de Livros (CRUD - Create, Read, Update, Delete) utilizando JavaFX para a interface gráfica e a especificação JPA com Hibernate para a persistência de dados. O objetivo principal foi consolidar a arquitetura em camadas e o uso transacional do JPA.

✨ Destaques do Projeto
Tecnologia: JavaFX para a interface e JPA (Hibernate) para o backend.

Padrão: Implementação completa das quatro operações CRUD.

Banco de Dados: Conexão nativa com PostgreSQL.

Arquitetura: Projeto modularizado (Java Modules/Jigsaw) e organizado em pacotes.

🚀 Como Executar o Projeto
Pré-requisitos
JDK 21 ou superior (configurado com suporte a JavaFX).

PostgreSQL Server instalado e rodando.

Maven (usado para gerenciar dependências).

Passos de Configuração
Clone o Repositório:
"git clone https://docs.github.com/pt/migrations/importing-source-code/using-the-command-line-to-import-source-code/adding-locally-hosted-code-to-github"

Configurar o Banco de Dados:

Crie um banco de dados chamado livraria_db no PostgreSQL.

Verifique e ajuste as credenciais de acesso no arquivo: src/main/resources/META-INF/persistence.xml.

Atenção: O projeto está configurado para o usuário postgres e senha Admin (se essa for a sua senha).

Execução:

Abra o projeto no IntelliJ IDEA (ou IDE de sua preferência).

O Maven fará o download automático das dependências (Hibernate, JavaFX, PostgreSQL Driver).

Execute a classe principal: org.view.LivrariaApp.

O Hibernate irá automaticamente criar a tabela livros no seu banco de dados na primeira execução (hibernate.hbm2ddl.auto=update).

🧱 Arquitetura e Estrutura de Pacotes
O projeto segue o padrão MVC (Model-View-Controller) simplificado e utiliza pacotes para separar as responsabilidades:

| Pacote | Função | Descrição |
| :--- | :--- | :--- |
| `org.model` | **Entidade/Modelo** | Contém a classe **`Livro`** (a entidade JPA mapeada para o banco). |
| `org.dao` | **Acesso a Dados (DAO)** | Contém a classe **`LivroDAO`**, responsável pela comunicação transacional com o `EntityManager` (CRUD). |
| `org.view` | **Interface/View** | Contém a classe **`LivrariaApp`**, que constrói a interface gráfica (JavaFX) e atua como o Controlador da aplicação. |
| `org.util` | **Utilitários** | Contém a classe **`JPAUtil`** para inicialização centralizada do `EntityManagerFactory`. |

⚙️ Tecnologias Principais
Interface: JavaFX 21

Persistência: Jakarta Persistence API (JPA)

Provider JPA: Hibernate ORM 6

Banco de Dados: PostgreSQL

Gerenciador de Build: Apache Maven

Módulos: Java Platform Module System (JPMS)

🤝 Contribuição
Sinta-se à vontade para sugerir melhorias, como validação de formulário mais robusta ou o uso de FXML!

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Desenvolvido por Gabriel Rodrigues como Exercício de Laboratório


