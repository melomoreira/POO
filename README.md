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

- **Java** — linguagem de programação utilizada na implementação.
- **ORMLite** — mapeamento entre objetos Java e tabelas do banco de dados.
- **SQLite** — banco de dados relacional utilizado para armazenar os dados.
- **JDBC** — conexão entre a aplicação Java e o banco de dados.

### Bibliotecas utilizadas

As bibliotecas necessárias para executar o projeto estão disponíveis na pasta [`bibliotecas/`](bibliotecas/).

- **ORMLite JDBC** — biblioteca de ORM e integração com JDBC.
- **SQLite JDBC** — driver de conexão entre Java e SQLite.

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

## Evidências dos testes

Esta seção reúne os arquivos utilizados para demonstrar a execução da aplicação no BlueJ e o funcionamento das operações CRUD.

### Etapas para executar os testes no BlueJ

As imagens a seguir apresentam o caminho percorrido no ambiente BlueJ para acessar e executar os testes da aplicação.

1. **Etapa 1 — Primeiro passo da execução**

   [Visualizar Etapa 1](evidencias/Etapa%201.png)

2. **Etapa 2 — Continuação da execução**

   [Visualizar Etapa 2](evidencias/Etapa%202.png)

3. **Etapa 3 — Acesso ao resultado dos testes**

   [Visualizar Etapa 3](evidencias/Etapa%203.png)

### Relatório com as evidências da execução

O arquivo abaixo contém o registro da execução dos testes no BlueJ, com as saídas que demonstram o funcionamento das operações implementadas.

[**Visualizar Relatório do BlueJ (PDF)**](evidencias/Relat%C3%B3rio%20do%20BlueJ.pdf)

### Explicações dos testes CRUD

O documento abaixo apresenta as explicações dos resultados registrados no relatório do BlueJ, descrevendo o que foi verificado em cada operação.

[**Visualizar Explicações dos Testes CRUD (PDF)**](evidencias/Explica%C3%A7%C3%B5es%20dos%20Testes%20CRUD.pdf)

### Resultados verificados

- **Create:** cadastro de um usuário no banco de dados.
- **Read:** consulta do usuário pelo identificador e listagem dos registros.
- **Persistência:** recuperação dos dados após o encerramento e a reabertura da conexão com o banco de dados.
- **Update:** alteração do curso do usuário de Engenharia de Computação para Ciência da Computação.
- **Delete:** exclusão do usuário e confirmação de que o registro não existia mais no banco de dados.

O relatório do BlueJ apresenta os resultados da execução, enquanto o documento de explicações descreve o significado de cada teste. As três imagens anteriores mostram o caminho utilizado para chegar à execução desses testes.

## Próximas etapas

A persistência poderá ser expandida futuramente para outras entidades previstas no Campus Hub, como universidades, grupos, eventos, reservas, disciplinas, avaliações e documentos acadêmicos.

## Referências

- **ORMLite.** *ORMLite — Lightweight Object Relational Mapping for Java.* Disponível em: https://ormlite.com/.
- **SQLite.** *SQLite — SQL Database Engine.* Disponível em: https://www.sqlite.org/.
- **Oracle.** *Java Documentation.* Disponível em: https://docs.oracle.com/en/java/.
- **BlueJ.** *BlueJ — The Interactive Java Environment.* Disponível em: https://www.bluej.org/.

## Observação

Esta entrega contempla a persistência da entidade `Usuario` e seus testes CRUD. Ela representa uma etapa do desenvolvimento do Campus Hub, não a implementação completa de todas as funcionalidades da plataforma.
