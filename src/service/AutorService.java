package service;

import java.util.List;

import dao.AutorDao;
import domain.Autor;

public class AutorService {
    
    private AutorDao aDao = new AutorDao();

    public void insert(Autor autor){
        aDao.insert(autor);
    }

    public void update (Autor autor){
        aDao.update(autor);
    }

    public void delete (Long id){
        aDao.delete(id);
    }

    public List<Autor> findAll (Integer busca){
        
    return aDao.findAll(busca);    
    }

    public List<Autor> findByNome (String nome){

    return aDao.findByNome(nome);    
    }


}
