package org.miller.literalura.Service;


import static java.lang.System.out;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.miller.literalura.Api.Api;
import org.miller.literalura.Model.Autor;
import org.miller.literalura.Model.Idioma;
import org.miller.literalura.Model.Libro;
import org.miller.literalura.Model.LibroRecord;
import org.miller.literalura.Utils.JsonParser;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

    private final Scanner scanner;
    private final LibroService libroService;
    private final AutorService autorService;
    private final JsonParser jsonParser;

    public MenuService(LibroService libroService, AutorService autorService, JsonParser jsonParser) {
        this.scanner = new Scanner(System.in);
        this.libroService = libroService;
        this.autorService = autorService;
        this.jsonParser = jsonParser;
    }

    public void guardarLibro() {
        List<LibroRecord> librosObtenidos = obtenerLibrosApi();

        if (librosObtenidos.isEmpty()) {
            out.println("No se encontró ningun libro");
            return;
        }

        out.println("Escoja un libro para guardar[0-Cancelar]");
        for (int i = 0; i < librosObtenidos.size(); i++) {
            LibroRecord libroRecord = librosObtenidos.get(i);
            String autorNombre = (libroRecord.autores() != null && !libroRecord.autores().isEmpty()) 
                ? libroRecord.autores().get(0).nombre() 
                : "Autor desconocido";
            String idioma = (libroRecord.idiomas() != null && !libroRecord.idiomas().isEmpty()) 
                ? libroRecord.idiomas().get(0) 
                : "Sin idioma";
            out.println((i + 1) + " - "
                    + libroRecord.titulo() + " - "
                    + idioma + " - "
                    + autorNombre);
        }

        int opcion = scanner.nextInt();
        scanner.nextLine();
        if (opcion == 0) {
            return;
        }
        if (opcion < 1 || opcion > librosObtenidos.size()) {
            out.println("Error: número erroneo");
            return;
        }

        LibroRecord libroRecord = librosObtenidos.get(opcion - 1);
        Optional<Libro> libroTraidoDelRepo = libroService.getLibroByNombre(libroRecord.titulo());
        
        // Verificar si el libro ya existe
        if (libroTraidoDelRepo.isPresent()) {
            out.println("Error: no se puede registrar dos veces el mismo libro");
            return;
        }

        Libro libro = new Libro(libroRecord);

        // Verificar si hay autor y si ya existe en la base de datos
        if (libroRecord.autores() != null && !libroRecord.autores().isEmpty()) {
            String nombreAutor = libroRecord.autores().get(0).nombre();
            Optional<Autor> autorTraidodelRepo = autorService.getAutorByName(nombreAutor);
            
            if (!autorTraidodelRepo.isPresent() && libro.getAutor() != null) {
                autorService.saveAutor(libro.getAutor());
            }
        }

        libroService.saveLibro(libro);
    }

    public List<LibroRecord> obtenerLibrosApi() {
        out.print("Ingrese el título del libro [0-Cancelar]: ");
        String titulo = scanner.nextLine();
        if (titulo.equals("0")) {
            return Collections.emptyList();
        }
        List<LibroRecord> librosObtenidos;
        librosObtenidos = jsonParser.parseLibros(Api.getDatos(titulo));
        return librosObtenidos;
    }


    public void listarLibrosRegistrados() {
        List<Libro> libros = libroService.getAllLibroRepository();
        libros.forEach(Libro::imprimirInformacion);
    }

    public void listarAutoresRegistrados() {
        List<Autor> autores = autorService.getAllAutores();
        autores.forEach(Autor::imprimirInformacion);
    }

    public void listarAutoresVivosEnAnio() {
        try {
            out.print("Ingrese año: ");
            int anio = scanner.nextInt();
            scanner.nextLine();
            List<Autor> autores = autorService.getAutorByAnio(anio);
            autores.forEach(Autor::imprimirInformacion);
        } catch (InputMismatchException e) {
            out.println("Error: debe ingresar un número");
        }

    }

    public void listarLibrosPorIdioma() {
        Idioma.listarIdiomas();
        out.print("Ingrese el codigo del idioma [0-Cancelar]: ");
        String idiomaBuscado = scanner.nextLine();
        if (idiomaBuscado.equals("0")) {
            return;
        }
        List<Libro> libros = libroService.getLibroByIdioma(idiomaBuscado);
        libros.forEach(Libro::imprimirInformacion);
    }

}
