package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import domain.Autor;
import domain.Livro;
import domain.LivroAutor;

public class LivroAutorDao {

    Connection connection;
    

    public LivroAutorDao(Connection connection) {
        this.connection = connection;
    }

    public void insert(Livro livro) throws SQLException{
        String sql = "INSERT INTO livro_autor (id_autor, id_livro) VALUES (?, ?)";

        try(PreparedStatement st = connection.prepareStatement(sql);) {

            for (Autor autor : livro.getAutores()) {
                st.setLong(1, autor.getIdAutor());
                st.setLong(2, livro.getId());

                st.addBatch();
                
            }
            st.executeBatch();
            
        } catch (SQLException e) {
            if(connection != null){
                connection.rollback();
                throw e;
            }
            
        }
    }

    public void delete(Long id){

        String sql = "DELETE FROM livro_autor WHERE id_livro = ?";

        try(PreparedStatement st = connection.prepareStatement(sql);) {

            st.setLong(1, id);

            st.executeUpdate();
            
        } catch (SQLException e) {

            
        }

    }

}

