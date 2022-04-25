package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class CadastroAutorController {

    @FXML
    private MenuItem miMenuVoltar;

    @FXML
    void handlerVoltarMenu(ActionEvent event) throws Exception{
        Principal.mudarCenaPrincipal();
    }

}