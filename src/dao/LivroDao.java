package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import config.ConfigDB;
import domain.Autor;
import domain.Livro;
import domain.LivroAutor;

public class LivroDao {
    

    public void insert(Livro livro) {

        String sql = "INSERT INTO livro(titulo, isbn, descricao, edicao)" +
                "VALUES ( ? , ? , ? , ? )";

        try (
                Connection connection = ConfigDB.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, new int[]{1});) {
                connection.setAutoCommit(false);

            
            statement.setString(1, livro.getTitulo());
            statement.setString(2, livro.getIsbn());
            statement.setString(3, livro.getDescricao());
            statement.setInt(4, livro.getEdicao());
          

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            livro.setId(Integer.toUnsignedLong(resultSet.getInt(1)));

            LivroAutorDao livroAutorDao = new LivroAutorDao(connection);
           
            livroAutorDao.insert(livro);
            connection.commit();
            connection.close();

        } catch (SQLException e )  {
            e.printStackTrace();
        }
    
    }

    public List<Livro> findAll(Integer busca) {

        String sql =    """
            SELECT		 	l.id, 
                            l.titulo,
                            LISTAGG(a.NOME, ', ') WITHIN GROUP (ORDER BY a.NOME) AS nome, 
                            l.isbn, 
                            l.edicao, 
                            l.descricao 
                            FROM livro l
                            INNER JOIN livro_autor la
                            ON l.id = la.id_livro
                            INNER JOIN autor a
                            ON la.id_autor = a.id_autor
                            GROUP BY l.id, l.titulo,l.isbn, l.edicao, l.descricao 
                            
                            FETCH NEXT ? ROWS ONLY
                        
                        """;
        List<Livro> livros = null;

        try (
                Connection connection = ConfigDB.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, busca);
            statement.executeQuery();

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

    public List<Livro> findById(Long id) {
        String sql = """
                        SELECT  l.id, 
                                l.titulo,
                                a.NOME, 
                                l.isbn, 
                                l.edicao, 
                                l.descricao 
                        FROM livro l 
                        INNER JOIN livro_autor la
                        ON l.id = la.id_livro
                        INNER JOIN autor a
                        ON la.id_autor = a.id_autor
                        WHERE l.ID = ?
                        FETCH FIRST 1 ROWS ONLY  
                     """;
        List<Livro> livros = null;
        Livro livro;

        try (
                Connection connection = ConfigDB.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

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
        String sql = "UPDATE livro SET titulo=?, isbn=?, edicao=?, descricao=? WHERE id = ?";
        try (
                Connection connection = ConfigDB.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, livro.getTitulo());
            statement.setString(2, livro.getIsbn());
            statement.setInt(3, livro.getEdicao());
            statement.setString(4, livro.getDescricao());
            statement.setLong(5, livro.getId());

            statement.executeUpdate();

            LivroAutorDao livroAutorDao = new LivroAutorDao(connection);
           
            livroAutorDao.delete(livro.getId());
            livroAutorDao.insert(livro);
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Livro> findByTitulo(String titulo) {
        String sql = """
                        SELECT  l.id, 
                                l.titulo,
                                a.NOME, 
                                l.isbn, 
                                l.edicao, 
                                l.descricao 
                        FROM livro l 
                        INNER JOIN livro_autor la
                        ON l.id = la.id_livro
                        INNER JOIN autor a
                        ON la.id_autor = a.id_autor
                        WHERE UPPER(l.titulo) like UPPER(?) 
                        FETCH FIRST 1 ROWS ONLY
                """;
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
        livro.setAutor(resultSet.getString("nome"));
        livro.setDescricao(resultSet.getString("descricao"));
        livro.setIsbn(resultSet.getString("isbn"));
        

        return livro;
    }

}
