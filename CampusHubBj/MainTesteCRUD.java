import java.sql.SQLException;
import java.util.List;

public class MainTesteCRUD {
    public static void main(String[] args) {
        String nomeBanco = "campushub.db";
        Database database = new Database(nomeBanco);

        try {
            System.out.println("=== INICIANDO TESTES CRUD ===");

            UsuarioRepository repo = new UsuarioRepository(database);

            // 1. CREATE
            
            System.out.println("\n=== 1. CREATE ===");

            Usuario usuario = new Usuario("Gustavo Hilbert", "Engenharia de Computacao", "UFG");

            repo.criar(usuario);

            int idUsuario = usuario.getId();

            System.out.println("ID gerado: " + idUsuario);
            System.out.println("Usuario cadastrado: " + usuario);

            // 2. READ
            
            System.out.println("\n=== 2. READ POR ID ===");

            Usuario consultado = repo.consultarPorId(idUsuario);

            if (consultado != null) {
                System.out.println("Usuario encontrado:");
                System.out.println(consultado);
            } else {
                throw new SQLException("Falha: usuario nao encontrado apos o cadastro.");
            }

            System.out.println("\n=== READ: LISTAR TODOS ===");

            List<Usuario> lista = repo.listarTodos();

            for (Usuario u : lista) {
                System.out.println(u);
            }

            System.out.println("Total de usuarios: " + lista.size());

            // Verifica a persistência fechando e reabrindo a conexão.
            
            System.out.println("\n=== VERIFICANDO PERSISTENCIA ===");

            database.close();

            database = new Database(nomeBanco);
            repo = new UsuarioRepository(database);

            Usuario recuperado = repo.consultarPorId(idUsuario);

            if (recuperado == null) {
                throw new SQLException("Falha: usuario nao persistiu no banco.");
            }

            System.out.println("Usuario recuperado apos reabrir o banco:");
            System.out.println(recuperado);

            // 3. UPDATE
            
            System.out.println("\n=== 3. UPDATE ===");

            System.out.println("Antes: " + recuperado);

            recuperado.setCurso("Ciencia da Computacao");
            repo.atualizar(recuperado);

            Usuario atualizado = repo.consultarPorId(idUsuario);

            System.out.println("Depois: " + atualizado);

            if (!"Ciencia da Computacao".equals(atualizado.getCurso())) {
                throw new SQLException("Falha: atualizacao nao confirmada.");
            }

            System.out.println("Atualizacao confirmada.");

            // 4. DELETE
            
            System.out.println("\n=== 4. DELETE ===");

            System.out.println("Usuario que sera excluido: " + repo.consultarPorId(idUsuario));

            repo.excluirPorId(idUsuario);

            Usuario aposExclusao = repo.consultarPorId(idUsuario);

            if (aposExclusao == null) {
                System.out.println("Exclusao confirmada: usuario nao existe mais.");
            } else {
                throw new SQLException("Falha: usuario ainda existe no banco.");
            }

            // Verificação final
            
            System.out.println("\n=== VERIFICACAO FINAL ===");

            List<Usuario> listaFinal = repo.listarTodos();

            System.out.println("Total de usuarios restantes: " + listaFinal.size());

            System.out.println("\n=== TODOS OS TESTES CONCLUIDOS ===");

        } catch (Exception e) {
            System.err.println("Erro durante os testes CRUD:");
            e.printStackTrace();
        } finally {
            database.close();
        }
    }
}