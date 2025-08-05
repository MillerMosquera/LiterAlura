package org.miller.literalura.Service;

import java.util.List;
import java.util.Optional;

import org.miller.literalura.Model.Idioma;
import org.miller.literalura.Model.Libro;
import org.miller.literalura.Respository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroService {

    private LibroRepository libroRepository;

    @Autowired
    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public LibroService() {
    }

    public List<Libro> getAllLibroRepository() {
        return libroRepository.findAll();
    }

    public Optional<Libro> getLibroByNombre(String nombre) {
        return libroRepository.getLibroByNombre(nombre);
    }

    public List<Libro> getLibroByIdioma(String idioma) {
        Idioma idiomaEnum = Idioma.stringToEnum(idioma);
        return libroRepository.getLibroByIdioma(idiomaEnum);
    }

    public void saveLibro(Libro libro) {
        libroRepository.save(libro);
    }

}
