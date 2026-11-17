import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    private UsuarioController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                MainApp.class.getResource("/view/UsuarioView.fxml"));

        Scene scene = new Scene(loader.load());
        controller = loader.getController();

        stage.setTitle("Campus Hub - Gerenciamento de Usuários");
        stage.setMinWidth(850);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        if (controller != null) {
            controller.fecharConexao();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
