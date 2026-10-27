# POO
Implementação da persistência de dados da entidade Usuario do projeto Campus Hub, utilizando Java, ORMLite e SQLite, com padrão Repository e operações CRUD (Create, Read, Update e Delete).
# Campus Hub — Persistência de Dados

## Sobre o projeto

O **Campus Hub** é uma plataforma digital voltada à integração da comunidade universitária, com o objetivo de centralizar informações acadêmicas, serviços, atividades e oportunidades, além de incentivar a interação entre estudantes de diferentes universidades.

A proposta inclui funcionalidades como cadastro de universidades e estudantes, criação de grupos e comunidades, divulgação de eventos, reserva de espaços e compartilhamento de informações acadêmicas.

## Sobre esta atividade

Esta atividade prática faz parte do desenvolvimento do Campus Hub e tem como objetivo implementar a persistência de dados de uma entidade do projeto utilizando um ORM (*Object-Relational Mapping*) e o padrão Repository.

Nesta etapa, foi implementada a entidade `Usuario`, responsável por representar um estudante cadastrado na plataforma.

## Tecnologias utilizadas

- **Java** — linguagem de programação.
- **ORMLite** — mapeamento entre objetos Java e tabelas do banco de dados.
- **SQLite** — banco de dados relacional utilizado para armazenar os dados.
- **JDBC** — conexão entre a aplicação Java e o banco de dados.

## Entidade implementada

A entidade `Usuario` possui os seguintes atributos:

| Atributo | Descrição |
|---|---|
| `id` | Identificador único do usuário, gerado automaticamente. |
| `nome` | Nome do usuário. |
| `curso` | Curso do usuário. |
| `universidade` | Universidade à qual o usuário está vinculado. |

Os atributos são mapeados para a tabela `usuarios` no banco de dados SQLite por meio das anotações do ORMLite.

## Operações CRUD

O projeto implementa as quatro operações fundamentais de persistência de dados:

- **Create:** cadastra um novo usuário no banco de dados.
- **Read:** consulta um usuário pelo identificador e lista os usuários cadastrados.
- **Update:** atualiza os dados de um usuário existente.
- **Delete:** exclui um usuário pelo identificador.

As operações são organizadas na classe `UsuarioRepository`, responsável por intermediar o acesso aos dados.

## Estrutura do código

- `Database.java` — gerencia a conexão com o banco de dados SQLite.
- `Usuario.java` — representa a entidade Usuario e seu mapeamento para a tabela.
- `UsuarioRepository.java` — implementa as operações de acesso e manipulação dos dados.
- `MainTesteCRUD.java` — executa os testes das operações CRUD e da persistência dos dados.

## Testes realizados

Os testes executados verificaram:

1. Cadastro de um usuário.
2. Consulta do usuário pelo identificador.
3. Listagem dos usuários cadastrados.
4. Fechamento e reabertura da conexão para verificar a persistência dos dados.
5. Atualização dos dados do usuário.
6. Exclusão do usuário.
7. Confirmação de que o usuário excluído não permanece no banco de dados.

Os resultados obtidos confirmaram o funcionamento das operações CRUD e a recuperação dos dados após a reabertura da conexão.

## Banco de dados

O banco de dados utilizado nesta etapa é o `campushub.db`, criado e acessado pela aplicação por meio do SQLite e do ORMLite.

## Próximas etapas

A persistência poderá ser expandida futuramente para outras entidades previstas no Campus Hub, como universidades, grupos, eventos, reservas, disciplinas, avaliações e documentos acadêmicos.

## Observação

Esta entrega contempla a persistência da entidade `Usuario` e seus testes CRUD. Ela representa uma etapa do desenvolvimento do Campus Hub, não a implementação completa de todas as funcionalidades da plataforma.
