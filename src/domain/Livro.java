package domain;

import java.util.List;

public class Livro {

    private Long id;
    private String titulo;
    private String isbn;
    private Integer edicao;
    private String autor;
    private String descricao;

    List<Autor> autores;

    public Livro() {
    }

    public Livro(String titulo, String isbn, Integer edicao) {

        this.titulo = titulo;
        this.isbn = isbn;
        this.edicao = edicao;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getEdicao() {
        return edicao;
    }

    public void setEdicao(Integer edicao) {
        this.edicao = edicao;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Livro [autor=" + autor + ", descricao=" + descricao + ", edicao=" + edicao + ", id=" + id + ", isbn="
                + isbn + ", titulo=" + titulo + "]";
    }

}
