package com.avaliacaopratica.api.config;

import com.avaliacaopratica.api.enums.CourseRegistrationStatus;
import com.avaliacaopratica.api.models.Course;
import com.avaliacaopratica.api.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseDataLoader implements CommandLineRunner {

    private final CourseRepository courseRepository;

    @Override
    public void run(String... args) {

        if (courseRepository.count() > 0) {
            return;
        }

        List<Course> cursos = List.of(
                new Course(null, "Java Básico", 3, CourseRegistrationStatus.EM_ANDAMENTO),
                new Course(null, "Java Avançado", 2, CourseRegistrationStatus.EM_ANDAMENTO),
                new Course(null, "Spring Boot Essencial", 3, CourseRegistrationStatus.EM_ANDAMENTO),
                new Course(null, "Banco de Dados SQL", 3, CourseRegistrationStatus.EM_ANDAMENTO),
                new Course(null, "Introdução ao Git", 2, CourseRegistrationStatus.EM_ANDAMENTO),
                new Course(null, "Docker para Iniciantes", 3, CourseRegistrationStatus.EM_ANDAMENTO),
                new Course(null, "APIs REST com Spring", 2, CourseRegistrationStatus.EM_ANDAMENTO),
                new Course(null, "Arquitetura Clean", 3, CourseRegistrationStatus.EM_ANDAMENTO),
                new Course(null, "Testes Automatizados", 2, CourseRegistrationStatus.EM_ANDAMENTO),
                new Course(null, "Boas Práticas em Java", 3, CourseRegistrationStatus.EM_ANDAMENTO)
        );

        courseRepository.saveAll(cursos);
    }
}
