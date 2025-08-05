package org.miller.literalura.Ui;

public class Menu {

    private String menu = "\n" +
            "1-Buscar libro por título\n" +
            "2-Listar libros registrados\n" +
            "3-Listar autores registrados\n" +
            "4-Listar autores vivos en un determinado año\n" +
            "5-Listar libros por idioma\n" +
            "0-Salir\n" +
            "\n" +
            "Elija una opción: ";
    
    private String bienvenida = "Bienvenidos a literAlura";

    public String getMenu() {
        return menu;
    }

    public String getBienvenida() {
        return bienvenida;
    }
}