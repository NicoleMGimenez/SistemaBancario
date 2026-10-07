package ar.edu.unju.fi.arquitectura.tp2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class Tp2Application {

    static void main(String[] args) {
        SpringApplication.run(Tp2Application.class, args);
    }

}
