package org.miller.literalura;

import org.miller.literalura.Principal.Principal;
import org.miller.literalura.Service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(LiterAluraApplication.class, args);
    }

    @Autowired
    private MenuService menuService;

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal(menuService);
        principal.EjecutarAplicacion();
    }
}
