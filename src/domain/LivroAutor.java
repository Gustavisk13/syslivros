package domain;

public class LivroAutor {
    private Long idLivro;
    private Long idAutor;

    public LivroAutor() {
    }

    public LivroAutor(Long idAutor, Long idLivro) {
        this.idLivro = idLivro;
        this.idAutor = idAutor;
    }

    public Long getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(Long idLivro) {
        this.idLivro = idLivro;
    }

    public Long getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Long idAutor) {
        this.idAutor = idAutor;
    }

    @Override
    public String toString() {
        return "LivroAutor [idAutor=" + idAutor + ", idLivro=" + idLivro + "]";
    }

    

    
}
