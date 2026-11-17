package controller;

import model.Usuario;
import repository.UsuarioRepository;
import database.Database;

import java.sql.SQLException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class UsuarioController {
    @FXML private TableView<Usuario> tabelaUsuarios;
    @FXML private TableColumn<Usuario, Integer> colunaId;
    @FXML private TableColumn<Usuario, String> colunaNome;
    @FXML private TableColumn<Usuario, String> colunaCurso;
    @FXML private TableColumn<Usuario, String> colunaUniversidade;

    @FXML private TextField campoNome;
    @FXML private TextField campoCurso;
    @FXML private TextField campoUniversidade;
    @FXML private Label mensagem;
    @FXML private Button botaoCadastrar;

    private Database database;
    private UsuarioRepository usuarioRepository;
    private final ObservableList<Usuario> usuarios =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarColunas();

        try {
            // Usa o mesmo banco SQLite do projeto original.
            database = new Database("campushub.db");
            usuarioRepository = new UsuarioRepository(database);
            carregarUsuarios();
            mensagem.setText("Banco de dados conectado.");
        } catch (SQLException e) {
            mensagem.setText("Não foi possível conectar ao banco de dados.");
            botaoCadastrar.setDisable(true);
            mostrarAlerta("Erro de conexão", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void configurarColunas() {
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        colunaUniversidade.setCellValueFactory(
                new PropertyValueFactory<>("universidade"));
        tabelaUsuarios.setItems(usuarios);
    }

    @FXML
    private void cadastrarUsuario() {
        String nome = campoNome.getText().trim();
        String curso = campoCurso.getText().trim();
        String universidade = campoUniversidade.getText().trim();

        if (nome.isEmpty() || curso.isEmpty() || universidade.isEmpty()) {
            mensagem.setText("Preencha todos os campos antes de cadastrar.");
            return;
        }

        try {
            Usuario novoUsuario = new Usuario(nome, curso, universidade);
            usuarioRepository.criar(novoUsuario);

            carregarUsuarios();
            limparFormulario();
            mensagem.setText("Usuário cadastrado com sucesso! ID: "
                    + novoUsuario.getId());
        } catch (SQLException e) {
            mensagem.setText("Erro ao cadastrar usuário.");
            mostrarAlerta("Erro no cadastro", e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void atualizarTabela() {
        carregarUsuarios();
        mensagem.setText("Lista de usuários atualizada.");
    }

    private void carregarUsuarios() {
        if (usuarioRepository == null) {
            return;
        }

        try {
            List<Usuario> lista = usuarioRepository.listarTodos();
            usuarios.setAll(lista);
        } catch (SQLException e) {
            mensagem.setText("Erro ao consultar usuários.");
            mostrarAlerta("Erro na consulta", e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }

    private void limparFormulario() {
        campoNome.clear();
        campoCurso.clear();
        campoUniversidade.clear();
        campoNome.requestFocus();
    }

    private void mostrarAlerta(String titulo, String conteudo,
                               Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(conteudo == null ? "Erro não especificado." : conteudo);
        alerta.showAndWait();
    }

    public void fecharConexao() {
        if (database != null) {
            database.close();
        }
    }
}
