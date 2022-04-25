package com.example.springboottest.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository){
        return args-> {
            Student test = new Student(1L,"test","test@test.com", LocalDate.now());
            Student test2 = new Student(2L,"test2","test2@test2.com", LocalDate.now());
            studentRepository.saveAll(List.of(test,test2));
        };
    }
}
