package controller;

import java.util.List;

import dao.LivroDao;
import domain.Livro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class ConsultaLivroController {

    Integer somaBusca = 0;
    Integer contaFiltro = 0;

    @FXML
    private Button btnFiltro;

    @FXML
    private Button btnMenu;

    
    @FXML
    private Button btnMaisDez;

    @FXML
    private Button btnPesquisar;

    @FXML
    private Label lblAutor;

    @FXML
    private Label lblDescricao;

    @FXML
    private Label lblEdicao;

    @FXML
    private Label lblFiltroAutor;

    @FXML
    private Label lblFiltroEdicao;

    @FXML
    private Label lblFiltroISBN;

    @FXML
    private Label lblISBN;

    @FXML
    private Label lblTitulo;

    @FXML
    private TableView<Livro> tbvTabela;

    @FXML
    private TableColumn<Livro, String> tcTitulo;

    @FXML
    private TextField tfAutor;

    @FXML
    private TextField tfEdicao;

    @FXML
    private TextField tfISBN;

    @FXML
    private TextField tfPesquisa;

    @FXML
    private Label tfVisuAutor;

    @FXML
    private Label tfVisuEdicao;

    @FXML
    private Label tfVisuIsbn;

    @FXML
    private Label tfVisuTitulo;

    @FXML
    private Label lblPlaceHolder;

    @FXML
    private ImageView ivImagem;

    ObservableList<Livro> livrosObservableList;

    private List<Livro> livros;

    @FXML
    void handlerFiltrar(ActionEvent event) {
        LivroDao ldao = new LivroDao();
        revelaFiltro();
        contaFiltro++;

        if(contaFiltro > 1){
            if(tfAutor.getText() != ""){
            livros = ldao.findByAutor(tfAutor.getText());
            livrosObservableList = FXCollections.observableArrayList(livros);
            tbvTabela.setItems(livrosObservableList);
            tfAutor.clear();
            }
            else if(tfISBN.getText() != ""){
            livros = ldao.findByIsbn(tfISBN.getText());
            livrosObservableList = FXCollections.observableArrayList(livros);
            tbvTabela.setItems(livrosObservableList);
            tfISBN.clear();
            }else{
            livros = ldao.findByEdicao(tfEdicao.getText());
            livrosObservableList = FXCollections.observableArrayList(livros);
            tbvTabela.setItems(livrosObservableList);
            tfEdicao.clear();
            }
        }



    }

    @FXML
    void handlerPesquisar(ActionEvent event) {
        String titulo;
        LivroDao ldao = new LivroDao();
        tfPesquisa.requestFocus();
        btnMaisDez.setVisible(true);
        if (tfPesquisa.getText() != "") {

            titulo = tfPesquisa.getText();
            System.out.println(titulo);
            livros = ldao.findByTitulo(titulo);
            livrosObservableList = FXCollections.observableArrayList(livros);
            tbvTabela.setItems(livrosObservableList);
            tfPesquisa.clear();
        } else {
            livros = ldao.findAll(20);
            livrosObservableList = FXCollections.observableArrayList(livros);
            tbvTabela.setItems(livrosObservableList);
            tfPesquisa.clear();
        }
    }

    @FXML
    void handlerTabelaLivro(MouseEvent event) {
         livrosPorClick(event);
    }

    @FXML
    void handlerVoltarMenu(ActionEvent event) throws Exception {
        Principal.mudarCenaPrincipal();
    }

    @FXML
    void handlerMaisDez(ActionEvent event) {
            somaBusca = somaBusca + 10;
            LivroDao ldao = new LivroDao();
            livros = ldao.findAll(20 + somaBusca);
            livrosObservableList = FXCollections.observableArrayList(livros);
            tbvTabela.setItems(livrosObservableList);
            tfPesquisa.clear();
    }

    private void revelaFiltro(){
        lblFiltroAutor.setVisible(true);
        lblFiltroEdicao.setVisible(true);
        lblFiltroISBN.setVisible(true);
        tfAutor.setVisible(true);
        tfEdicao.setVisible(true);
        tfISBN.setVisible(true);
    }

    private void revelaConsulta(){
        tfVisuAutor.setVisible(true);
        tfVisuEdicao.setVisible(true);
        tfVisuIsbn.setVisible(true);
        tfVisuTitulo.setVisible(true);
    }

    public void initialize() {
        tcTitulo.setCellValueFactory(new PropertyValueFactory<Livro, String>("titulo"));
    }

    private Livro livrosPorClick (MouseEvent event) {
        Livro livro = tbvTabela.getSelectionModel().getSelectedItem();
    if (event.getClickCount() == 2 && livro != null) {

        System.out.println(livro);
        lblTitulo.setText(livro.getTitulo());
        lblISBN.setText(livro.getIsbn());
        lblEdicao.setText(String.valueOf(livro.getEdicao()));
        lblAutor.setText(livro.getAutor());
        lblDescricao.setText(livro.getDescricao());

        lblPlaceHolder.setVisible(false);
        revelaConsulta();

    }
    return livro;
    }

    private void refreshTable (){
        LivroDao ldao = new LivroDao();
        livros = ldao.findAll(20);
        livrosObservableList = FXCollections.observableArrayList(livros);
        tbvTabela.setItems(livrosObservableList);
        tbvTabela.refresh();
    }

}
