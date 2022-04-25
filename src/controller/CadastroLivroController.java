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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class CadastroLivroController {

    private final int MAXISBN = 13;
    private List<Livro> livros;
    ObservableList<Livro> livrosObservableList;
    Integer somaBusca = 0;
    Alert errorAlert = new Alert(AlertType.ERROR);
    Alert infoAlert = new Alert(AlertType.INFORMATION);
    
    @FXML
    private Button btnAdicionarLivro;

    @FXML
    private Menu mMenu;

    @FXML
    private MenuItem miVoltar;

    @FXML
    private Button btnBuscarLivro;

    @FXML
    private Button btnDeletarLivro;

    @FXML
    private Label lblIdHide;

    @FXML
    private Label lblPreviewTitulo;

    @FXML
    private Label lblTempo;

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
    private Button btnMaisDez;

    @FXML
    private Label lblCharFalta;

    @FXML
    private Label lblCharSobra;

    @FXML
    private TextField tfTituloLivro;

    @FXML
    private TextField tfPesquisarLivro;

    @FXML
    void handlerBuscarLivro(ActionEvent event) {
        String titulo;
        LivroDao ldao = new LivroDao();
        tfPesquisarLivro.requestFocus();
        btnMaisDez.setVisible(true);
        if (tfPesquisarLivro.getText() != "") {

            titulo = tfPesquisarLivro.getText();
            System.out.println(titulo   );
            livros = ldao.findByTitulo(titulo);
            livrosObservableList = FXCollections.observableArrayList(livros);
            tbvTabela.setItems(livrosObservableList);
            tfPesquisarLivro.clear();
        } else {
            livros = ldao.findAll(20);
            livrosObservableList = FXCollections.observableArrayList(livros);
            tbvTabela.setItems(livrosObservableList);
            tfPesquisarLivro.clear();
        }

    }
    
    @FXML
    void handlerDeletarLivro(ActionEvent event) {

        LivroDao lDao = new LivroDao();
        Boolean campoVazio = verificaCampoVazio();
        System.out.println(campoVazio);
        if (campoVazio == true) {
            Long id = Long.parseLong(lblIdHide.getText());
            System.out.println(id);
            lDao.delete(id);
            refreshTable();
            limparCampos();

        }else{
            errorAlert.setHeaderText("EXCLUSÃO DE LIVROS");
            errorAlert.setContentText("Existem Campos Vazios!");
            errorAlert.showAndWait();
        }

    }

    @FXML
    void handlerEditarLivro(ActionEvent event) throws Exception {
        Livro livro = new Livro();
        LivroDao lDao = new LivroDao();
        final Integer ISBN_TAMANHO = tfIsbnLivro.getText().replace("-", "").length();
        Boolean campoVazio = verificaCampoVazio();
        Boolean tipoEdicao = verificaEdicao(tfEdicaoLivro.getText());
    
        if (campoVazio == true && ISBN_TAMANHO == MAXISBN && tipoEdicao == true) {
            livro.setTitulo(tfTituloLivro.getText());
            livro.setIsbn(formatIsbn(tfIsbnLivro.getText()));
            livro.setEdicao(Integer.parseInt(tfEdicaoLivro.getText()));
            livro.setAutor(tfAutorLivro.getText());
            livro.setDescricao(tfDescricaoLivro.getText());
            livro.setId(Long.parseLong(lblIdHide.getText()));

            lDao.update(livro);
            limparCampos();
            lblPreviewTitulo.setText("");
            refreshTable();

            infoAlert.setHeaderText("ATUALIZAÇÃO DE LIVROS");
            infoAlert.setContentText("Livro Atualizado Com Sucesso!!!");
            infoAlert.showAndWait();
            
        }else if(campoVazio == false){
            errorAlert.setHeaderText("ATUALIZAÇÃO DE LIVROS");
            errorAlert.setContentText("Existem Campos vazios !");
            errorAlert.showAndWait();
            
        }else if(tipoEdicao == false){
            errorAlert.setHeaderText("INSERIR LIVROS");
            errorAlert.setContentText("Campo edição deve ser somente números!");
            errorAlert.showAndWait();
        }else if(ISBN_TAMANHO > MAXISBN){
            errorAlert.setHeaderText("ATUALIZAÇÃO DE LIVROS");
            errorAlert.setContentText("O tamanho do campo ISBN deve ser 13 caracteres");
            errorAlert.showAndWait();
            lblCharFalta.setText("");
            livro.setIsbn((tfIsbnLivro.getText()));
            Integer valorSobra = (livro.getIsbn().trim().length()) - MAXISBN;
            lblCharSobra.setText(valorSobra + " caracteres passando!");
        }else if(ISBN_TAMANHO < MAXISBN){
            errorAlert.setHeaderText("ATUALIZAÇÃO DE LIVROS");
            errorAlert.setContentText("O tamanho do campo ISBN deve ser 13 caracteres");
            errorAlert.showAndWait();
            lblCharSobra.setText("");
            livro.setIsbn((tfIsbnLivro.getText()));
            Integer valorSobra = MAXISBN - (livro.getIsbn().trim().length());
            lblCharFalta.setText(valorSobra + " caracteres faltando!"); 
        }

    }

    @FXML
    void handlerLimparLivro(ActionEvent event) {
        limparCampos();
    }

    @FXML
    void handlerInserirLivro(ActionEvent event) throws Exception {

        final Integer ISBN_TAMANHO = tfIsbnLivro.getText().replace("-", "").length();
        Alert errorAlert = new Alert(AlertType.ERROR);
        Livro livro = new Livro();
        LivroDao ldao = new LivroDao();
        Boolean campoVazio = verificaCampoVazio();
        Boolean tipoEdicao = verificaEdicao(tfEdicaoLivro.getText());
        
        if(campoVazio == true && ISBN_TAMANHO == MAXISBN && tipoEdicao == true){
            livro.setTitulo(tfTituloLivro.getText());
            livro.setIsbn(formatIsbn(tfIsbnLivro.getText()));
            livro.setEdicao(Integer.parseInt(tfEdicaoLivro.getText()));
            livro.setAutor(tfAutorLivro.getText());
            livro.setDescricao(tfDescricaoLivro.getText());

            infoAlert.setHeaderText("INSERIR LIVROS");
            infoAlert.setContentText("Livro Inserido com Sucesso !");

            ldao.insert(livro);
        }
        else if(campoVazio == false){
            errorAlert.setHeaderText("INSERIR LIVROS");
            errorAlert.setContentText("Existem Campos vazios !");
            errorAlert.showAndWait();
        }
        else if(tipoEdicao == false){
            errorAlert.setHeaderText("INSERIR LIVROS");
            errorAlert.setContentText("Campo edição deve ser somente números!");
            errorAlert.showAndWait();
        }
        else if(ISBN_TAMANHO > MAXISBN){
            errorAlert.setHeaderText("INSERIR LIVROS");
            errorAlert.setContentText("O tamanho do campo ISBN deve ser 13 caracteres !");
            errorAlert.showAndWait();
            lblCharFalta.setText("");
            livro.setIsbn((tfIsbnLivro.getText()));
            Integer valorSobra = (livro.getIsbn().trim().length()) - MAXISBN;
            lblCharSobra.setText(valorSobra + " caracteres passando!");
        }
        else if(ISBN_TAMANHO < MAXISBN){
            errorAlert.setHeaderText("INSERIR LIVROS");
            errorAlert.setContentText("O tamanho do campo ISBN deve ser 13 caracteres !");
            errorAlert.showAndWait();
            lblCharSobra.setText("");
            livro.setIsbn((tfIsbnLivro.getText()));
            Integer valorSobra = MAXISBN - (livro.getIsbn().trim().length());
            lblCharFalta.setText(valorSobra + " caracteres faltando!");
        } 
        

    }

    @FXML
    void handlerMaisDez(ActionEvent event) {
            somaBusca = somaBusca + 10;
            LivroDao ldao = new LivroDao();
            livros = ldao.findAll(20 + somaBusca);
            livrosObservableList = FXCollections.observableArrayList(livros);
            tbvTabela.setItems(livrosObservableList);
            tfPesquisarLivro.clear();


    }

    @FXML
    void handlerTabela(MouseEvent event) {
        livrosPorClick(event);
    }

    @FXML
    void handlerVoltarMenu(ActionEvent event) throws Exception {
        Principal.mudarCenaPrincipal();
    }

        public void initialize() {
            tcTitulo.setCellValueFactory(new PropertyValueFactory<Livro, String>("titulo"));
            tcIsbn.setCellValueFactory(new PropertyValueFactory<Livro, String>("isbn"));
            tcEdicao.setCellValueFactory(new PropertyValueFactory<Livro, Integer>("edicao"));
            tcAutor.setCellValueFactory(new PropertyValueFactory<Livro, String>("autor"));
        }
        
        private boolean verificaEdicao(String f) 
{ 
        try 
        { 
            Integer.parseInt(f);
            System.out.println("É inteiro"); 
            return true; 
        } 
        catch (NumberFormatException e) 
        { 
            System.out.println("Não é inteiro"); 
            return false; 
        } 
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
            livros = ldao.findAll(20);
            livrosObservableList = FXCollections.observableArrayList(livros);
            tbvTabela.setItems(livrosObservableList);
            tbvTabela.refresh();
        }
    
        private void limparCampos() {
            tfTituloLivro.clear();
            tfIsbnLivro.clear();
            tfEdicaoLivro.clear();
            tfAutorLivro.clear();
            tfDescricaoLivro.clear();
            lblPreviewTitulo.setText("");
            lblCharSobra.setText("");
            lblCharFalta.setText("");
        }
    
    
    
    
    }
