package service;

import java.util.List;

import dao.LivroDao;
import domain.Livro;

public class LivroService {

    private LivroDao lDao = new LivroDao();

    public void insert(Livro livro){
        lDao.insert(livro);
    }

    public List<Livro> findAll(Integer busca){

    return lDao.findAll(busca);
    }

    public Livro findById(Long id){

    return lDao.findById(id);
    }

    public void delete(Long id){
        lDao.delete(id);
    }

    public void update(Livro livro){
        lDao.update(livro);
    }

    public List<Livro> findByTitulo(String titulo){

    return lDao.findByTitulo(titulo);
    }

    public List<Livro> findByAutor(String autor){

    return lDao.findByAutor(autor);
    }

    public List<Livro> findByIsbn(String isbn){

    return lDao.findByIsbn(isbn);
    }

    public List<Livro> findByEdicao(String edicao){
        
    return lDao.findByEdicao(edicao);
    }

    
}
