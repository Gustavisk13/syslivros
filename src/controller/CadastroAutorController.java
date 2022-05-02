package controller;

import java.text.ParseException;
import java.util.List;

import dao.AutorDao;
import domain.Autor;
import domain.Livro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import service.AutorService;

public class CadastroAutorController {

    private Autor autor = new Autor();
    private Livro livro = new Livro();
    private AutorService autorService = new AutorService();
    private ObservableList<Autor> livrosObservableList;
    private ObservableList<Autor> autoresObservableList;
    private List<Autor> autores;
    private Integer somaBusca = 0;

    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnAtualizar;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnLimpar;

    @FXML
    private Button btnMaisDez;

    @FXML
    private Button btnPesquisa;

    @FXML
    private Label lblHideId;

    @FXML
    private Label lblShowAutor;

    @FXML
    private Label lblShowLivrosFeitos;

    @FXML
    private Label lblShowNacionalidade;

    @FXML
    private Label lblShowNascimento;

    @FXML
    private Label lblShowObra;

    @FXML
    private MenuItem miMenuVoltar;

    @FXML
    private TableView<Autor> tbvAutor;

    @FXML
    private TableView<Livro> tbvLivro;

    @FXML
    private TableView<Livro> tbvLivrosDisp;

    @FXML
    private TableColumn<Autor, String> tcAutor;

    @FXML
    private TableColumn<Livro, String> tcLivro;

    @FXML
    private TableColumn<Livro, String> tcLivroDisponivel;

    @FXML
    private TextField tfNacionalidade;

    @FXML
    private TextField tfNascimento;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfPesquisa;

    @FXML
    void handlerInsert(ActionEvent event){
            
            autor.setNome(tfNome.getText());
            autor.setNacionalidade(tfNacionalidade.getText());
            autor.setAno_nascimento(Integer.parseInt(tfNascimento.getText()));

            autorService.insert(autor);
            
    }

    @FXML
    void handlerUpdate(ActionEvent event) {
        lblHideId.setText(Long.toString(autor.getIdAutor()));

        autor.setNome(tfNome.getText());
        autor.setNacionalidade(tfNacionalidade.getText());;
        autor.setAno_nascimento(Integer.parseInt(tfNascimento.getText()));;
        autor.setIdAutor(Long.parseLong(lblHideId.getText()));

        autorService.update(autor);


    }

    @FXML
    void handlerDelete(ActionEvent event) {

        autorService.delete(Long.parseLong(lblHideId.getText()));

    }

    @FXML
    void handlerLimpar(ActionEvent event) {
         limparCampos();
    }

    @FXML
    void handlerMaisDez(ActionEvent event) {
         somaBusca = somaBusca+10;
         autores = autorService.findAll(20+somaBusca);
        autoresObservableList = FXCollections.observableArrayList(autores);
        tbvAutor.setItems(autoresObservableList);
        tfPesquisa.clear();
        System.out.println(somaBusca);
    }

    @FXML
    void handlerPesquisa(ActionEvent event) {
        String titulo;
        tfPesquisa.requestFocus();
        btnMaisDez.setVisible(true);

        if (tfPesquisa.getText() != ""){
            titulo = tfPesquisa.getText();
            autores = autorService.findByNome(titulo);
            autoresObservableList = FXCollections.observableArrayList(autores);
            tbvAutor.setItems(autoresObservableList);
            tfPesquisa.clear();
        }else{
            autores = autorService.findAll(20);
            autoresObservableList = FXCollections.observableArrayList(autores);
            tbvAutor.setItems(autoresObservableList);
            tfPesquisa.clear();
        }


    }

    @FXML
    void handlerTabelaAutor(MouseEvent event) {
        autoresPorClick(event);
    }

    @FXML
    void handlerTabelaLivro(ActionEvent event) {

    }

    @FXML
    void handlerTabelaLivroDisponivel(ActionEvent event) {

    }

    @FXML
    void handlerVoltarMenu(ActionEvent event) throws Exception{
        Principal.mudarCenaPrincipal();
    }

    private void limparCampos(){
        tfNome.clear();
        tfNacionalidade.clear();
        tfNascimento.clear();
        lblShowAutor.setText("");
        lblShowNacionalidade.setText("");
        lblShowNascimento.setText("");
        lblShowObra.setText("");
        lblShowLivrosFeitos.setText("");
    }

    public void initialize() {
        tcAutor.setCellValueFactory(new PropertyValueFactory<Autor, String>("nome"));
        tcLivro.setCellValueFactory(new PropertyValueFactory<Livro, String>("titulo"));
    }

    private Autor autoresPorClick(MouseEvent event){
        autor = tbvAutor.getSelectionModel().getSelectedItem();

        if(event.getClickCount() == 2 && autor != null){

            lblHideId.setText(String.valueOf(autor.getIdAutor()));
            tfNome.setText(autor.getNome());
            tfNacionalidade.setText(autor.getNacionalidade());
            tfNascimento.setText(String.valueOf(autor.getAno_nascimento()));
    
        }
        return autor;
    }


}