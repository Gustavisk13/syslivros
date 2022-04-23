package controller;

import domain.Livro;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ConsultaLivroController {

    @FXML
    private Button btnFiltro;

    @FXML
    private Button btnMenu;

    @FXML
    private Button btnPesquisar;

    @FXML
    private Label lblAutor;

    @FXML
    private Label lblDescricao;

    @FXML
    private Label lblEdicao;

    @FXML
    private Label lblISBN;

    @FXML
    private Label lblTitulo;

    @FXML
    private TableView<Livro> tbvTabela;

    @FXML
    private TableColumn<Livro, String> tcTitulo;

    @FXML
    private TextField tfPesquisa;

    @FXML
    private ImageView ivImagem;

    @FXML
    void handlerFiltrar(ActionEvent event) {

    }

    @FXML
    void handlerPesquisar(ActionEvent event) {

    }

    @FXML
    void handlerTabelaLivro(MouseEvent event) {

    }

    @FXML
    void handlerVoltarMenu(ActionEvent event) throws Exception {
        Principal.mudarCenaPrincipal();
    }

}
