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

public class AutorDao {
    
    public void insert(Autor autor){

        String sql = "INSERT INTO autor(nome, nacionalidade, ano_nascimento)" +
                "VALUES ( ? , ? , ?)";

        try (
                Connection connection = ConfigDB.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, new int[]{1});) {
              
            
            statement.setString(1, autor.getNome());
            statement.setString(2, autor.getNacionalidade());
            statement.setInt(3, autor.getAno_nascimento());

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            autor.setIdAutor(Integer.toUnsignedLong(resultSet.getInt(1)));

        } catch (SQLException e )  {
            e.printStackTrace();
        }
    
    }

    public void update(Autor autor) {
        String sql = "UPDATE autor SET nome=?, nacionalidade=?, ano_nascimento=? WHERE id_autor = ?";
        try (
                Connection connection = ConfigDB.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, autor.getNome());
            statement.setString(2, autor.getNacionalidade());
            statement.setInt(3, autor.getAno_nascimento());
            statement.setLong(4, autor.getIdAutor());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM autor WHERE id_autor = ?";
        try (
                Connection connection = ConfigDB.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);) {
                
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Autor> findAll(Integer busca) {

        String sql = "SELECT id_autor, nome, nacionalidade, ano_nascimento FROM autor FETCH NEXT ? ROWS ONLY ";
        List<Autor> autores = null;

        try (
                Connection connection = ConfigDB.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, busca);
            statement.executeQuery();

            ResultSet resultSet = statement.executeQuery();
            autores = new ArrayList<>();
            Autor autor;

            while (resultSet.next() && autores.size()<=busca) {
                autor = obterAutorPorResultSet(resultSet);
                autores.add(autor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autores;
    }

    public List<Autor> findByNome (String nome){
        String sql = "SELECT id_autor, nome, nacionalidade, ano_nascimento FROM autor WHERE UPPER(nome) like UPPER(?)";
        Autor autor = null;
        List<Autor> autores = null;

        try (
                Connection connection = ConfigDB.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setString(1, ("%" + autor + "%"));
            ResultSet resultSet = statement.executeQuery();
            System.out.println(statement);
            autores = new ArrayList<>();

            while (resultSet.next() && autores.size() <= 20) {
                autor = obterAutorPorResultSet(resultSet);
                autores.add(autor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return autores;
    }



    private Autor obterAutorPorResultSet(ResultSet resultSet) throws SQLException{
        Autor autor = new Autor();

        autor.setIdAutor(resultSet.getLong("id_autor"));
        autor.setNome(resultSet.getString("nome"));
        autor.setNacionalidade(resultSet.getString("nacionalidade"));
        autor.setAno_nascimento(resultSet.getInt("ano_nascimento"));
        
    return autor;
    }
}
