package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PrincipalController  {

    

    @FXML
    private Button btnMostrarNome;

    @FXML
    private Label lblNome;

    @FXML
    private MenuItem miCadastroLivro;

    @FXML
    private TextField tfNome;
    
    @FXML
    void chamaCadastroLivro(ActionEvent event) throws IOException{
        
        

    }
    @FXML
    void handlerMostrarNome(ActionEvent event) {
        lblNome.setText(tfNome.getText());
        lblNome.setVisible(true);
    }

}
