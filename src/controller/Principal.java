package controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Principal extends Application {
    private static Stage stagePrincipal;

    @Override
    public void start(Stage stageP) throws Exception {

        stagePrincipal = stageP;        
        mudarCenaPrincipal();

    }

    public static void mudarCenaPrincipal() throws Exception {
		Parent root = FXMLLoader.load(Principal.class.getResource("../javafx/cenaprincipal.fxml"));
		Scene scene = new Scene(root);

		stagePrincipal.setTitle("Sistema de Biblioteca");
		stagePrincipal.setScene(scene);
		stagePrincipal.setResizable(false);
		stagePrincipal.show();
	}

    public static void mudarCenaCadastro() throws Exception {
		Parent root = FXMLLoader.load(Principal.class.getResource("../javafx/cenalivro.fxml"));
		Scene scene = new Scene(root);

		stagePrincipal.setTitle("Sistema de Biblioteca - Cadastrar Livro");
		stagePrincipal.setScene(scene);
		stagePrincipal.show();
	}

    public static void main(String[] args) {
        launch(args);
    }
}
