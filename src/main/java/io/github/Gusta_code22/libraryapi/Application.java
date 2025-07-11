package io.github.Gusta_code22.libraryapi;

import io.github.Gusta_code22.libraryapi.model.Autor;
import io.github.Gusta_code22.libraryapi.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;

@SpringBootApplication
@EnableJpaAuditing
@ComponentScan(basePackages = "io.github.Gusta_code22.libraryapi")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}




}
