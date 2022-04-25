package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import config.ConfigDB;
import domain.Livro;

public class LivroDao {
    

    public void insert(Livro livro) {

        String sql = "INSERT INTO livro(titulo, isbn, descricao, autor, edicao)" +
                "VALUES ( ? , ? , ? , ? , ? )";

        try (
                Connection connection = ConfigDB.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, new int[]{1});) {
              
            
            statement.setString(1, livro.getTitulo());
            statement.setString(2, livro.getIsbn());
            statement.setString(3, livro.getDescricao());
            statement.setString(4, livro.getAutor());
            statement.setInt(5, livro.getEdicao());
          

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            livro.setId(Integer.toUnsignedLong(resultSet.getInt(1)));

        } catch (SQLException e )  {
            e.printStackTrace();
        }
    
    }

    public List<Livro> findAll(Integer busca) {

        String sql = "SELECT id, titulo, isbn, edicao, autor, descricao FROM livro ";
        List<Livro> livros = null;

        try (
                Connection connection = ConfigDB.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();
            livros = new ArrayList<>();
            Livro livro;

            while (resultSet.next() && livros.size()<=busca) {
                livro = obterLivroPorResultSet(resultSet);
                livros.add(livro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livros;
    }

    public Livro findById(Long id) {
        String sql = "SELECT id, edicao, titulo, descricao, isbn FROM livro WHERE id = ? ";
        Livro livro = null;

        try (
                Connection connection = ConfigDB.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                livro = obterLivroPorResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livro;
    }

    public void delete(Long id) {
        String sql = "DELETE FROM livro WHERE id = ?";
        try (
                Connection connection = ConfigDB.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);) {
                
            statement.setLong(1, id);
           
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void update(Livro livro) {
        String sql = "UPDATE livro SET titulo=?, isbn=?, edicao=?, autor=?, descricao=? WHERE id = ?";
        try (
                Connection connection = ConfigDB.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, livro.getTitulo());
            statement.setString(2, livro.getIsbn());
            statement.setInt(3, livro.getEdicao());
            statement.setString(4, livro.getAutor());
            statement.setString(5, livro.getDescricao());
            statement.setLong(6, livro.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Livro> findByTitulo(String titulo) {
        String sql = "SELECT id, titulo, isbn, descricao, autor, edicao FROM livro WHERE UPPER(titulo) like UPPER(?)";
        Livro livro = null;
        List<Livro> livros = null;

        try (
                Connection connection = ConfigDB.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setString(1, ("%" + titulo + "%"));
            ResultSet resultSet = statement.executeQuery();
            System.out.println(statement);
            livros = new ArrayList<>();

            while (resultSet.next() && livros.size() <= 20) {
                livro = obterLivroPorResultSet(resultSet);
                livros.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livros;
    }

    public List<Livro> findByAutor(String autor){
        String sql = "SELECT id, titulo, isbn, descricao, autor, edicao FROM livro WHERE UPPER(autor) like UPPER(?)";
        Livro livro = null;
        List<Livro> livros = null;

        try (
                Connection connection = ConfigDB.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setString(1, ("%" + autor + "%"));
            ResultSet resultSet = statement.executeQuery();
            System.out.println(statement);
            livros = new ArrayList<>();

            while (resultSet.next() && livros.size() <= 20) {
                livro = obterLivroPorResultSet(resultSet);
                livros.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livros;

        
    }
    
    public List<Livro> findByIsbn (String isbn){
        String sql = "SELECT id, titulo, isbn, descricao, autor, edicao FROM livro WHERE UPPER(isbn) like UPPER(?)";
        Livro livro = null;
        List<Livro> livros = null;

        try (
                Connection connection = ConfigDB.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setString(1, ("%" + isbn + "%"));
            ResultSet resultSet = statement.executeQuery();
            System.out.println(statement);
            livros = new ArrayList<>();

            while (resultSet.next() && livros.size() <= 20) {
                livro = obterLivroPorResultSet(resultSet);
                livros.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livros;

    }
    
    public List<Livro> findByEdicao (String edicao){
        String sql = "SELECT id, titulo, isbn, descricao, autor, edicao FROM livro WHERE UPPER(edicao) like UPPER(?)";
        Livro livro = null;
        List<Livro> livros = null;

        try (
                Connection connection = ConfigDB.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setString(1, ("%" + edicao + "%"));
            ResultSet resultSet = statement.executeQuery();
            System.out.println(statement);
            livros = new ArrayList<>();

            while (resultSet.next() && livros.size() <= 20) {
                livro = obterLivroPorResultSet(resultSet);
                livros.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livros;

    }
    
    private Livro obterLivroPorResultSet(ResultSet resultSet) throws SQLException {
        Livro livro = new Livro();

        livro.setId(resultSet.getLong("id"));
        livro.setEdicao(resultSet.getInt("edicao"));
        livro.setTitulo(resultSet.getString("titulo"));
        livro.setDescricao(resultSet.getString("descricao"));
        livro.setIsbn(resultSet.getString("isbn"));
        livro.setAutor(resultSet.getString("autor"));

        return livro;
    }

}
