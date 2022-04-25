package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class PrincipalController {

    @FXML
    private Button btnCadastroLivro;

    @FXML
    private Button btnConsultaLivro;

    @FXML
    private Button btnCadastroAutor;

    @FXML
    void chamaCadastroLivro(ActionEvent event) throws Exception{
        Principal.mudarCenaCadastro();
    }

    @FXML
    void chamaConsultaLivro(ActionEvent event) throws Exception {
        Principal.mudarCenaConsulta();
    }

    @FXML
    void chamaCadastroAutor(ActionEvent event) throws Exception{
        Principal.mudarCenaCadastroAutor();
    }

}

