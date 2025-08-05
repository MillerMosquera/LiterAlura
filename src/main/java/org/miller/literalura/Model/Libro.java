package org.miller.literalura.Model;

import static java.lang.System.out;
import java.util.Optional;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne
    private Autor autor;
    private Idioma idioma;
    private Integer numeroDescargas;

    public Libro(String titulo, Idioma idioma, Integer numeroDescargas, String enlace) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.numeroDescargas = numeroDescargas;
    }

    public Libro(LibroRecord libro) {
        this.titulo = libro.titulo();
        Optional<AutorRecord> autorRecord = libro.autores().stream().findFirst();
        autorRecord.ifPresent(ar -> this.autor = new Autor(ar));

        Optional<String> idiomaString = libro.idiomas().stream().findFirst();
        idiomaString.ifPresent(is -> this.idioma = Idioma.stringToEnum(is));
        this.numeroDescargas = libro.numeroDescargas();
    }

    public void imprimirInformacion() {
        out.println("-----Libro-----");
        out.println("Título: " + titulo);
        out.println("Autor: " + (autor != null ? autor.getNombre() : "Sin autor"));
        out.println("Idioma: " + (idioma != null ? idioma.getIdiomaCompleto() : "Sin idioma"));
        out.println("Número de Descargas: " + numeroDescargas);
    }

    // Métodos getter adicionales en caso de problemas con Lombok
    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

}
