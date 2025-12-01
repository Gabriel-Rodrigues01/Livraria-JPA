package org.model;
import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String autor;
    private Double preco;

    public Livro() {}
    public Livro(String titulo, String autor, Double preco) {
        this.titulo = titulo;
        this.autor = autor;
        this.preco = preco;
    }
    // Getters e Setters OBRIGATÓRIOS para o JavaFX TableView ler os dados
    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }
}