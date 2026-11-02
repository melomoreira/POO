import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Interface gráfica (JavaFX) do Campus Hub para gerenciar a entidade Usuario.
 * Usa Database e UsuarioRepository do projeto, com as mesmas operações do
 * MainTesteCRUD: criar, consultarPorId, listarTodos, atualizar e excluirPorId.
 */
public class CampusHubInterface extends Application {

    private static final String NOME_BANCO = "campushub.db";

    private Database database;
    private UsuarioRepository repo;

    private final ObservableList<Usuario> dados = FXCollections.observableArrayList();
    private TableView<Usuario> tabela;
    private TextField campoNome;
    private TextField campoCurso;
    private TextField campoUniversidade;
    private TextField campoBuscaId;
    private Label status;

    @Override
    public void start(Stage stage) {
        try {
            database = new Database(NOME_BANCO);
            repo = new UsuarioRepository(database);
        } catch (Exception e) {
            erro("Não foi possível abrir o banco de dados", e);
            Platform.exit();
            return;
        }

        BorderPane raiz = new BorderPane();
        raiz.setPadding(new Insets(16));
        raiz.setTop(criarTopo());
        raiz.setCenter(criarCentro());
        raiz.setBottom(criarRodape());

        Scene cena = new Scene(raiz, 860, 560);
        stage.setTitle("Campus Hub - Gerenciamento de Usuários");
        stage.setScene(cena);
        stage.show();

        carregarTodos();
    }

    @Override
    public void stop() {
        if (database != null) {
            database.close();
        }
    }

    // ------------------------------------------------------------------
    // Montagem da tela
    // ------------------------------------------------------------------

    private VBox criarTopo() {
        Label titulo = new Label("Campus Hub");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label subtitulo = new Label("Cadastro de usuários (estudantes da plataforma)");
        subtitulo.setStyle("-fx-text-fill: #555555;");

        VBox topo = new VBox(2, titulo, subtitulo);
        topo.setPadding(new Insets(0, 0, 14, 0));
        return topo;
    }

    private VBox criarCentro() {
        VBox formulario = criarFormulario();
        HBox busca = criarBarraBusca();
        criarTabela();

        VBox centro = new VBox(12, formulario, busca, tabela);
        VBox.setVgrow(tabela, Priority.ALWAYS);
        return centro;
    }

    private VBox criarFormulario() {
        campoNome = new TextField();
        campoNome.setPromptText("Ex.: José Fulano");
        campoCurso = new TextField();
        campoCurso.setPromptText("Ex.: Engenharia de Computação");
        campoUniversidade = new TextField();
        campoUniversidade.setPromptText("Ex.: UFG");

        GridPane grade = new GridPane();
        grade.setHgap(10);
        grade.setVgap(8);
        grade.add(new Label("Nome:"), 0, 0);
        grade.add(campoNome, 1, 0);
        grade.add(new Label("Curso:"), 0, 1);
        grade.add(campoCurso, 1, 1);
        grade.add(new Label("Universidade:"), 0, 2);
        grade.add(campoUniversidade, 1, 2);
        GridPane.setHgrow(campoNome, Priority.ALWAYS);
        GridPane.setHgrow(campoCurso, Priority.ALWAYS);
        GridPane.setHgrow(campoUniversidade, Priority.ALWAYS);

        Button btnCadastrar = new Button("Cadastrar");
        btnCadastrar.setOnAction(e -> cadastrar());

        Button btnAtualizar = new Button("Atualizar selecionado");
        btnAtualizar.setOnAction(e -> atualizar());

        Button btnExcluir = new Button("Excluir selecionado");
        btnExcluir.setOnAction(e -> excluir());

        Button btnLimpar = new Button("Limpar campos");
        btnLimpar.setOnAction(e -> {
            tabela.getSelectionModel().clearSelection();
            limparFormulario();
        });

        HBox botoes = new HBox(8, btnCadastrar, btnAtualizar, btnExcluir, btnLimpar);
        botoes.setAlignment(Pos.CENTER_LEFT);

        VBox caixa = new VBox(10, grade, botoes);
        caixa.setPadding(new Insets(12));
        caixa.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 6; -fx-background-radius: 6;");
        return caixa;
    }

    private HBox criarBarraBusca() {
        campoBuscaId = new TextField();
        campoBuscaId.setPromptText("ID do usuário");
        campoBuscaId.setPrefWidth(140);
        campoBuscaId.setOnAction(e -> buscarPorId());

        Button btnBuscar = new Button("Buscar por ID");
        btnBuscar.setOnAction(e -> buscarPorId());

        Button btnListar = new Button("Listar todos");
        btnListar.setOnAction(e -> carregarTodos());

        HBox barra = new HBox(8, new Label("Consulta:"), campoBuscaId, btnBuscar, btnListar);
        barra.setAlignment(Pos.CENTER_LEFT);
        return barra;
    }

    private void criarTabela() {
        tabela = new TableView<>(dados);
        tabela.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tabela.setPlaceholder(new Label("Nenhum usuário cadastrado."));

        TableColumn<Usuario, Number> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getId()));
        colId.setPrefWidth(70);

        TableColumn<Usuario, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNome()));
        colNome.setPrefWidth(250);

        TableColumn<Usuario, String> colCurso = new TableColumn<>("Curso");
        colCurso.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCurso()));
        colCurso.setPrefWidth(260);

        TableColumn<Usuario, String> colUniversidade = new TableColumn<>("Universidade");
        colUniversidade.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getUniversidade()));
        colUniversidade.setPrefWidth(200);

        tabela.getColumns().add(colId);
        tabela.getColumns().add(colNome);
        tabela.getColumns().add(colCurso);
        tabela.getColumns().add(colUniversidade);

        // Ao selecionar uma linha, os dados vão para o formulário (para editar).
        tabela.getSelectionModel().selectedItemProperty().addListener((obs, antigo, atual) -> {
            if (atual != null) {
                campoNome.setText(atual.getNome());
                campoCurso.setText(atual.getCurso());
                campoUniversidade.setText(atual.getUniversidade());
            }
        });
    }

    private HBox criarRodape() {
        status = new Label("Pronto.");
        status.setStyle("-fx-text-fill: #333333;");
        HBox rodape = new HBox(status);
        rodape.setPadding(new Insets(10, 0, 0, 0));
        return rodape;
    }

    // ------------------------------------------------------------------
    // Operações (ligadas ao UsuarioRepository)
    // ------------------------------------------------------------------

    /** READ: lista todos. */
    private void carregarTodos() {
        try {
            dados.setAll(repo.listarTodos());
            status("Total de usuários: " + dados.size());
        } catch (Exception e) {
            erro("Erro ao listar usuários", e);
        }
    }

    /** READ: consulta por ID. */
    private void buscarPorId() {
        String texto = campoBuscaId.getText().trim();
        if (texto.isEmpty()) {
            aviso("Informe o ID para buscar.");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            aviso("O ID deve ser um número inteiro.");
            return;
        }

        try {
            Usuario encontrado = repo.consultarPorId(id);
            if (encontrado == null) {
                status("Nenhum usuário encontrado com ID " + id + ".");
                return;
            }
            dados.setAll(encontrado);
            tabela.getSelectionModel().selectFirst();
            status("Usuário com ID " + id + " encontrado. Use \"Listar todos\" para ver a lista completa.");
        } catch (Exception e) {
            erro("Erro ao consultar usuário", e);
        }
    }

    /** CREATE. */
    private void cadastrar() {
        String nome = campoNome.getText().trim();
        String curso = campoCurso.getText().trim();
        String universidade = campoUniversidade.getText().trim();

        if (nome.isEmpty() || curso.isEmpty() || universidade.isEmpty()) {
            aviso("Preencha nome, curso e universidade.");
            return;
        }

        try {
            Usuario novo = new Usuario(nome, curso, universidade);
            repo.criar(novo);
            tabela.getSelectionModel().clearSelection();
            limparFormulario();
            carregarTodos();
            status("Usuário cadastrado com ID " + novo.getId() + ".");
        } catch (Exception e) {
            erro("Erro ao cadastrar usuário", e);
        }
    }

    /** UPDATE. */
    private void atualizar() {
        Usuario selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            aviso("Selecione um usuário na tabela para atualizar.");
            return;
        }

        String nome = campoNome.getText().trim();
        String curso = campoCurso.getText().trim();
        String universidade = campoUniversidade.getText().trim();

        if (nome.isEmpty() || curso.isEmpty() || universidade.isEmpty()) {
            aviso("Preencha nome, curso e universidade.");
            return;
        }

        try {
            int id = selecionado.getId();
            selecionado.setNome(nome);
            selecionado.setCurso(curso);
            selecionado.setUniversidade(universidade);
            repo.atualizar(selecionado);
            carregarTodos();
            status("Usuário com ID " + id + " atualizado.");
        } catch (Exception e) {
            carregarTodos(); // descarta alterações locais que não foram salvas
            erro("Erro ao atualizar usuário", e);
        }
    }

    /** DELETE. */
    private void excluir() {
        Usuario selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            aviso("Selecione um usuário na tabela para excluir.");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar exclusão");
        confirmacao.setHeaderText("Excluir o usuário \"" + selecionado.getNome() + "\"?");
        confirmacao.setContentText("Essa ação não pode ser desfeita.");
        Optional<ButtonType> resposta = confirmacao.showAndWait();

        if (resposta.isEmpty() || resposta.get() != ButtonType.OK) {
            return;
        }

        try {
            int id = selecionado.getId();
            repo.excluirPorId(id);
            tabela.getSelectionModel().clearSelection();
            limparFormulario();
            carregarTodos();
            status("Usuário com ID " + id + " excluído.");
        } catch (Exception e) {
            erro("Erro ao excluir usuário", e);
        }
    }

    // ------------------------------------------------------------------
    // Utilitários
    // ------------------------------------------------------------------

    private void limparFormulario() {
        campoNome.clear();
        campoCurso.clear();
        campoUniversidade.clear();
    }

    private void status(String mensagem) {
        status.setText(mensagem);
    }

    private void aviso(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.WARNING, mensagem);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }

    private void erro(String titulo, Exception e) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Erro");
        alerta.setHeaderText(titulo);
        alerta.setContentText(e.getMessage() != null ? e.getMessage() : e.toString());
        alerta.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
