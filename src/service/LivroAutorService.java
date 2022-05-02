package service;

import domain.Autor;
import domain.Livro;

public class LivroAutorService {
    
    AutorService autorService = new AutorService();
    LivroService livroService = new LivroService();
    
    public void insert(Autor autores, Livro livros){
        autorService.insert(autores);
        
    }

}
