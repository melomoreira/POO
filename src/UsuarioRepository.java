import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class UsuarioRepository {
    private final Dao<Usuario, Integer> usuarioDao;

    public UsuarioRepository(Database database) throws SQLException {
        TableUtils.createTableIfNotExists(database.getConnection(), Usuario.class);

        usuarioDao = DaoManager.createDao(database.getConnection(), Usuario.class);
    }

    // CREATE: cadastra um novo usuário
    
    public Usuario criar(Usuario usuario) throws SQLException {
        if (usuario == null) {
            throw new IllegalArgumentException("O usuario nao pode ser nulo.");
        }

        int resultado = usuarioDao.create(usuario);

        if (resultado != 1) {
            throw new SQLException("Nao foi possivel cadastrar o usuario.");
        }

        System.out.println("Usuario cadastrado com sucesso!");
        return usuario;
    }

    // READ: consulta pelo identificador
    
    public Usuario consultarPorId(int id) throws SQLException {
        return usuarioDao.queryForId(id);
    }

    // READ: lista todos os usuários
    
    public List<Usuario> listarTodos() throws SQLException {
        return usuarioDao.queryForAll();
    }

    // UPDATE: atualiza um usuário existente
    
    public void atualizar(Usuario usuario) throws SQLException {
        if (usuario == null || usuario.getId() <= 0) {
            throw new IllegalArgumentException("Usuario ou identificador invalido.");
        }

        int resultado = usuarioDao.update(usuario);

        if (resultado == 0) {
            throw new SQLException("Nenhum usuario encontrado para atualizar.");
        }

        System.out.println("Usuario atualizado com sucesso!");
    }

    // DELETE: exclui pelo identificador
    
    public void excluirPorId(int id) throws SQLException {

        int resultado = usuarioDao.deleteById(id);

        if (resultado == 0) {
            throw new SQLException("Nenhum usuario encontrado para excluir.");
        }

        System.out.println("Usuario excluido com sucesso!");
    }
}