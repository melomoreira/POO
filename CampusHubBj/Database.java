import java.sql.SQLException;
import com.j256.ormlite.jdbc.JdbcConnectionSource;

public class Database {
    private final String databaseName;
    private JdbcConnectionSource connection;

    public Database(String databaseName) {
        this.databaseName = databaseName;
    }

    public JdbcConnectionSource getConnection() throws SQLException {
        if (databaseName == null || databaseName.trim().isEmpty()) {
            throw new SQLException("Nome do banco de dados inválido.");
        }

        if (connection == null) {
            connection = new JdbcConnectionSource("jdbc:sqlite:" + databaseName);

            System.out.println("Banco de dados conectado: " + databaseName);
        }

        return connection;
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Conexão encerrada.");
            } catch (Exception e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}