package org.miller.literalura.Respository;

import java.util.List;
import java.util.Optional;

import org.miller.literalura.Model.Idioma;
import org.miller.literalura.Model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    @Query("SELECT l FROM Libro l WHERE LOWER(l.titulo) LIKE LOWER(:nombre)")
    Optional<Libro> getLibroByNombre(String nombre);

    @Query("SELECT l FROM Libro l WHERE l.idioma = :idioma" )
    List<Libro> getLibroByIdioma(Idioma idioma);
}
