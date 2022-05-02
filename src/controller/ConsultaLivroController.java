package controller;

import java.util.List;
import dao.LivroDao;
import domain.Autor;
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


public class ConsultaLivroController {

    Integer somaBusca = 0;
    Integer contaFiltro = 0;
    Autor autor = new Autor();
    CadastroLivroController cAutor = new CadastroLivroController();

    @FXML
    private Button btnEditar;

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
    void handlerEditarLivro(ActionEvent event) throws Exception {
        
        Livro livro = new Livro();
        

        livro.setId(Long.parseLong(lblFiltroAutor.getText()));
        livro.setTitulo(lblTitulo.getText());
        livro.setAutor(lblAutor.getText());
        livro.setIsbn(lblISBN.getText());
        livro.setDescricao(lblDescricao.getText());

        cAutor.preencheLivro(livro);
        Principal.mudarCenaCadastro();
        
    }

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
            contaFiltro--;
            }
            else if(tfISBN.getText() != ""){
            livros = ldao.findByIsbn(tfISBN.getText());
            livrosObservableList = FXCollections.observableArrayList(livros);
            tbvTabela.setItems(livrosObservableList);
            tfISBN.clear();
            contaFiltro--;
            }else{
            livros = ldao.findByEdicao(tfEdicao.getText());
            livrosObservableList = FXCollections.observableArrayList(livros);
            tbvTabela.setItems(livrosObservableList);
            tfEdicao.clear();
            contaFiltro--;
            }
        }



    }

    @FXML
    void handlerPesquisar(ActionEvent event) {
        String titulo;
        LivroDao ldao = new LivroDao();
        tfPesquisa.requestFocus();
        btnMaisDez.setVisible(true);
        if(contaFiltro == 1){
            handlerFiltrar(event);
        }
        else if (tfPesquisa.getText() != "") {

            titulo = tfPesquisa.getText();
            Boolean tipo = cAutor.verificaEdicao(titulo);
            
            if(tipo == false ){
                System.out.println("TITULO");
                livros = ldao.findByTitulo(titulo);
            }else{
                System.out.println("ID");
                livros = ldao.findById(Long.parseLong(titulo));
            }
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
        lblAutor.setText(livro.getAutor());
        lblDescricao.setText(livro.getDescricao());
        lblFiltroAutor.setText(String.valueOf(livro.getId()));

        revelaConsulta();
        lblPlaceHolder.setVisible(false);
        btnEditar.setVisible(true);
        
    }
    return livro;
    }

}
