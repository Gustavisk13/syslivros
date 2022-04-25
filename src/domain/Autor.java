package domain;

import java.util.List;

public class Autor {
    
    private Long idAutor;
    private String nome;
    private String nacionalidade;
    private Integer ano_nascimento;

    List<Livro> livros;

    public Autor() {
    }

    public Autor(Long idAutor, String nome, String nacionalidade, Integer ano_nascimento) {
        this.idAutor = idAutor;
        this.nome = nome;
        this.nacionalidade = nacionalidade;
        this.ano_nascimento = ano_nascimento;
    }

    public Long getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Long idAutor) {
        this.idAutor = idAutor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public Integer getAno_nascimento() {
        return ano_nascimento;
    }

    public void setAno_nascimento(Integer ano_nascimento) {
        this.ano_nascimento = ano_nascimento;
    }

    @Override
    public String toString() {
        return "Autor [ano_nascimento=" + ano_nascimento + ", idAutor=" + idAutor + ", livros=" + livros
                + ", nacionalidade=" + nacionalidade + ", nome=" + nome + "]";
    }
    
}
