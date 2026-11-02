import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "usuarios")
public class Usuario {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String nome;

    @DatabaseField(canBeNull = false)
    private String curso;

    @DatabaseField
    private String universidade;

    // Construtor vazio exigido pelo ORMLite
    
    public Usuario() {
    }

    public Usuario(String nome, String curso, String universidade) {
        this.nome = nome;
        this.curso = curso;
        this.universidade = universidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getUniversidade() {
        return universidade;
    }

    public void setUniversidade(String universidade) {
        this.universidade = universidade;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nome=" + nome + ", curso=" + curso + ", universidade=" + universidade + "]";
    }
}