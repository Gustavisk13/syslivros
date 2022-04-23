package controller;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.MaskFormatter;

import dao.LivroDao;
import domain.Livro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class CadastroLivroController {

    private final int MAXISBN = 13;

    @FXML
    private Button btnAdicionarLivro;

    @FXML
    private Button btnBuscarLivro;

    @FXML
    private Button btnDeletarLivro;

    @FXML
    private Label lblIdHide;

    @FXML
    private Label lblPreviewTitulo;

    @FXML
    private TableView<Livro> tbvTabela;

    @FXML
    private TableColumn<Livro, String> tcAutor;

    @FXML
    private TableColumn<Livro, Integer> tcEdicao;

    @FXML
    private TableColumn<Livro, String> tcIsbn;

    @FXML
    private TableColumn<Livro, String> tcTitulo;

    @FXML
    private TextField tfAutorLivro;

    @FXML
    private TextArea tfDescricaoLivro;

    @FXML
    private TextField tfEdicaoLivro;

    @FXML
    private TextField tfIsbnLivro;

    @FXML
    private Button tfLimparLivro;

    @FXML
    private Button btnEditar;

    @FXML
    private TextField tfTituloLivro;

    @FXML
    private TextField tfPesquisarLivro;

    ObservableList<Livro> livrosObservableList;

    private List<Livro> livros;

    @FXML
    void handlerBuscarLivro(ActionEvent event) {
        String titulo;
        LivroDao ldao = new LivroDao();
        tfPesquisarLivro.requestFocus();
        Boolean campoVazio = verificaCampoVazio();
        if (campoVazio == true) {

            titulo = tfPesquisarLivro.getText();
            livros = ldao.findByTitulo(titulo);
            livrosObservableList = FXCollections.observableArrayList(livros);
            tbvTabela.setItems(livrosObservableList);
            tfPesquisarLivro.clear();
        } else {
            livros = ldao.findAll();
            livrosObservableList = FXCollections.observableArrayList(livros);
            tbvTabela.setItems(livrosObservableList);
            tfPesquisarLivro.clear();
        }

    }

    @FXML
    void handlerDeletarLivro(ActionEvent event) {

        LivroDao lDao = new LivroDao();
        Boolean campoVazio = verificaCampoVazio();
        if (campoVazio == true) {
            Long id = Long.parseLong(lblIdHide.getText());
            System.out.println(id);
            lDao.delete(id);
            refreshTable();
            limparCampos();

        }

    }

    private void limparCampos() {
        tfTituloLivro.clear();
        tfIsbnLivro.clear();
        tfEdicaoLivro.clear();
        tfAutorLivro.clear();
        tfDescricaoLivro.clear();
    }

    @FXML
    void handlerEditarLivro(ActionEvent event) throws Exception {
        Livro livro = new Livro();
        LivroDao lDao = new LivroDao();
        
        final Integer ISBN_TAMANHO = tfIsbnLivro.getText().replace("-", "").length();
        Alert errorAlert = new Alert(AlertType.ERROR);
        Boolean campoVazio = verificaCampoVazio();
        

        if (campoVazio == true && ISBN_TAMANHO == MAXISBN) {
            livro.setTitulo(tfTituloLivro.getText());
            livro.setIsbn(formatIsbn(tfIsbnLivro.getText()));
            livro.setEdicao(Integer.parseInt(tfEdicaoLivro.getText()));
            livro.setAutor(tfAutorLivro.getText());
            livro.setDescricao(tfDescricaoLivro.getText());

            lDao.update(livro);
            limparCampos();
            refreshTable();
            
        }else if(campoVazio == false){
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Existem Campos vazios !");
            errorAlert.showAndWait();
            
        }else if(ISBN_TAMANHO != MAXISBN){
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("O tamanho do campo ISBN deve ser 13 caracteres");
            errorAlert.showAndWait();
        } 

    }

    public void initialize() {
        tcTitulo.setCellValueFactory(new PropertyValueFactory<Livro, String>("titulo"));
        tcIsbn.setCellValueFactory(new PropertyValueFactory<Livro, String>("isbn"));
        tcEdicao.setCellValueFactory(new PropertyValueFactory<Livro, Integer>("edicao"));
        tcAutor.setCellValueFactory(new PropertyValueFactory<Livro, String>("autor"));
    }

    @FXML
    void handlerLimparLivro(ActionEvent event) {
        limparCampos();
    }

    @FXML
    void handlerInserirLivro(ActionEvent event) throws Exception {

        final Integer ISBN_TAMANHO = tfIsbnLivro.getText().replace("-", "").length();
        Alert errorAlert = new Alert(AlertType.ERROR);
        Boolean campoVazio = verificaCampoVazio();
        
        if(campoVazio == true && ISBN_TAMANHO == MAXISBN){
            Livro livro = new Livro();
            LivroDao ldao = new LivroDao();
            livro.setTitulo(tfTituloLivro.getText());
            livro.setIsbn(formatIsbn(tfIsbnLivro.getText()));
            livro.setEdicao(Integer.parseInt(tfEdicaoLivro.getText()));
            livro.setAutor(tfAutorLivro.getText());
            livro.setDescricao(tfDescricaoLivro.getText());

            ldao.insert(livro);
        }else if(campoVazio == false){
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Existem Campos vazios !");
            errorAlert.showAndWait();
        }
        else if(ISBN_TAMANHO != MAXISBN){
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("O tamanho do campo ISBN deve ser 13 caracteres");
            errorAlert.showAndWait();
        } 
        

    }

    @FXML
    void handlerTabela(MouseEvent event) {
        livrosPorClick(event);
        

    }

        private  String formatIsbn(String isbn) throws Exception{
            
            Pattern pattern = Pattern.compile("-");
            Matcher matcher = pattern.matcher(isbn);
            boolean matcherFound = matcher.find();

            if(matcherFound){
                return isbn;
            }else{
                String mask= "###-#-####-####-#";
                MaskFormatter maskFormatter= new MaskFormatter(mask);
                maskFormatter.setValueContainsLiteralCharacters(false);
                String isbnFormat = maskFormatter.valueToString(isbn) ;
                System.out.println(isbnFormat);

                return isbnFormat;
            }
}

        private Boolean verificaCampoVazio() {
            
            String titulo = tfTituloLivro.getText();
            String isbn = tfIsbnLivro.getText();
            String edicao = tfEdicaoLivro.getText();
            String autor = tfAutorLivro.getText();
            String descricao = tfDescricaoLivro.getText();

            if((titulo != "") && (isbn != "") && (edicao != "") && (autor != "")
            && (descricao != "")){
                return true;
            }else{
                return false;
            }
        }

        private Livro livrosPorClick (MouseEvent event) {
            Livro livro = tbvTabela.getSelectionModel().getSelectedItem();
        if (event.getClickCount() == 2 && livro != null) {

            System.out.println(livro);
            tfTituloLivro.setText(livro.getTitulo());
            tfIsbnLivro.setText(livro.getIsbn());
            tfEdicaoLivro.setText(String.valueOf(livro.getEdicao()));
            tfAutorLivro.setText(livro.getAutor());
            tfDescricaoLivro.setText(livro.getDescricao());
            lblIdHide.setText(Long.toString(livro.getId()));
            lblPreviewTitulo.setText(livro.getTitulo());

        }
        return livro;
        }
    
        private void refreshTable (){
            LivroDao ldao = new LivroDao();
            livros = ldao.findAll();
            livrosObservableList = FXCollections.observableArrayList(livros);
            tbvTabela.setItems(livrosObservableList);
            tbvTabela.refresh();
        }
    }
